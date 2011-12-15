package it.matrix.alicehometv.service;

import static it.matrix.alicehometv.util.StringUtilies.*;
import static org.apache.commons.lang.StringUtils.*;
import it.matrix.alicehometv.*;
import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.services.userprivacydata.*;

import java.net.URL;
import java.sql.*;
import java.util.*;

@SuppressWarnings("serial")
public class AhtvUserServiceImpl implements AhtvUserService
{
    public static final String OTP_MOBILE_TYPE = "SMS";
    public static final String OTP_EMAIL_TYPE = "EMAIL";

    private static final Map<String, String> CONTROL_CODE_TYPE_MAPPING = new HashMap<String, String>()
    {{
        put(CONTROL_CODE_FOR_EMAIL, OTP_EMAIL_TYPE);
        put(CONTROL_CODE_FOR_MOBILE, OTP_MOBILE_TYPE);
    }};

    private UserDP itsUserPrivacyService;
    private PasswordGenerator itsPasswordGenerator;
    private DbConnectionProvider itsDbConnectionProvider;

    public AhtvUserServiceImpl(URL wsdlUserPrivacyServiceLocation, DbConnectionProvider dbConnectionProvider)
    {
        this(new UserDPService(wsdlUserPrivacyServiceLocation).getUserDPPort(), dbConnectionProvider, new PasswordGeneratorImpl());
    }

    public AhtvUserServiceImpl(UserDP userPrivacyService, DbConnectionProvider dbConnectionProvider, PasswordGenerator aPasswordGenerator)
    {
        itsUserPrivacyService = userPrivacyService;
        itsDbConnectionProvider = dbConnectionProvider;
        itsPasswordGenerator = aPasswordGenerator;
    }

    public int saveNewUser(UserProfile userProfile, String ahtvEmailAddress, String ahtvMobileNumber)
    {
        UserPDP newUser = UserPDPBuilder.newUserWith(userProfile.sunrisePassportCode());
        
        Collections.addAll(newUser.getEmail(), EmailBuilder.originalEmailWith(userProfile.email()), EmailBuilder.ahtvEmailWith(ahtvEmailAddress));
        Collections.addAll(newUser.getPhone(), PhoneBuilder.originalPhoneWith(userProfile.mobilePhoneNumber()), PhoneBuilder.ahtvPhoneWith(ahtvMobileNumber));
        
        ActivityLogger.debug("Saving new ahtv user:" + newUser);
        UserPDP savedUser = itsUserPrivacyService.insertUser(newUser, AHTV_CHANNEL_ID);
        
        int ahtvUserId = savedUser.getUserId().getIntUserId();
        ActivityLogger.info("Saved new ahtv user:" + savedUser);
        
        return ahtvUserId;
    }
    
    public boolean updateAhtvContactsFor(int ahtvUserId, String newAhtvEmailAddress, String newAhtvMobileNumber)
    {
        boolean emailUpdatedSuccessfully = updateAhtvEmailFor(ahtvUserId, newAhtvEmailAddress);
        boolean mobileUpdatedSuccessfully = updateAhtvMobileNumberFor(ahtvUserId, newAhtvMobileNumber);

        return emailUpdatedSuccessfully || mobileUpdatedSuccessfully;
    }

    public boolean updateOriginalContactsFor(AhtvUser ahtvUser, String newSunriseEmailAddress, String newSunriseMobileNumber)
    {
        boolean emailUpdatedSuccessfully = true;
        if (notEquals(ahtvUser.originalEmail(), newSunriseEmailAddress))
            emailUpdatedSuccessfully = updateOriginalEmailFor(ahtvUser.ahtvId(), newSunriseEmailAddress);
        
        boolean mobileUpdatedSuccessfully = true; 
        if (notEquals(ahtvUser.originalMobileNumber(), newSunriseMobileNumber))
            mobileUpdatedSuccessfully = updateOriginalMobileNumberFor(ahtvUser.ahtvId(), newSunriseMobileNumber);

        return emailUpdatedSuccessfully && mobileUpdatedSuccessfully;
    }

    public OTPVerificationResult verifyPendingOTPFor(int ahtvUserId, String passwordToVerify, String controlCodeType)
    {
        ActivityLogger.info("Verifying OTP:" + passwordToVerify + " for " + controlCodeType + " on behalf of user with id:" + ahtvUserId);

        Connection connection = itsDbConnectionProvider.connection();

        OTPVerificationResult result = OTPVerificationResult.NULL_RESULT;
        String procedureCallSql = "call pkg_fe_ahtv_otp.checkOtpUser (?,?,?,?,?,?,?)";
        try
        {
            CallableStatement callableStatement = connection.prepareCall(procedureCallSql);

            callableStatement.setInt(1, ahtvUserId);
            callableStatement.setString(2, passwordToVerify);
            callableStatement.setString(3, CONTROL_CODE_TYPE_MAPPING.get(controlCodeType));
            callableStatement.registerOutParameter(4, Types.NUMERIC);
            callableStatement.registerOutParameter(5, Types.NUMERIC);
            callableStatement.registerOutParameter(6, Types.VARCHAR);
            callableStatement.registerOutParameter(7, Types.NUMERIC);
            callableStatement.execute();

            boolean isVerified = callableStatement.getInt(4) == 1;
            boolean isPasswordMatching = callableStatement.getInt(5) == 1;
            String resultMessage = callableStatement.getString(6);
            int resultCode = callableStatement.getInt(7);

            result = new OTPVerificationResult(resultCode, resultMessage, isVerified, isPasswordMatching);

            ActivityLogger.info("Invoked procedure " + procedureCallSql + "; result:" + resultMessage + "; result code:" + resultCode);
        }
        catch (SQLException sqlException)
        {
            ActivityLogger.logException(sqlException);
        }
        finally
        {
            itsDbConnectionProvider.close(connection);
        }

        ActivityLogger.info("OTP verification result is:" + result + " for user with id:" + ahtvUserId);
        return result;
    }

    public boolean notifyPendingMobileForUser(int ahtvUserId)
    {
        return saveOTPFor(ahtvUserId, OTP_MOBILE_TYPE);
    }

    public boolean notifyPendingEmailForUser(int ahtvUserId)
    {
        return saveOTPFor(ahtvUserId, OTP_EMAIL_TYPE);
    }

    public AhtvUser findUserBySPC(String aSunrisePassportCode)
    {
        UserPDP userPDP = findUserPDPBy(aSunrisePassportCode);
        ActivityLogger.debug("Found UserPDP:" + userPDP);

        AhtvUser ahtvUser = AhtvUser.from(userPDP);
        if (ahtvUser.exists())
        {
            ActivityLogger.debug("Ahtv user exists with id:" + ahtvUser.ahtvId());
            
            ahtvUser.originalEmail(EmailUtils.detectOriginalEmailValueFrom(userPDP.getEmail()));
            ahtvUser.ahtvEmail(EmailUtils.detectAhtvEmailValueFrom(userPDP.getEmail()));
            ahtvUser.originalMobileNumber(PhoneUtils.detectOriginalPhoneValueFrom(userPDP.getPhone()));
            ahtvUser.ahtvMobileNumber(PhoneUtils.detectAhtvPhoneValueFrom(userPDP.getPhone()));
            computePendingDataOn(ahtvUser);
        }
        ActivityLogger.info(ahtvUser.isUnknown() ? "User not found in AHTV database with SPC:" + aSunrisePassportCode : "Found ahtv user:" + ahtvUser);

        return ahtvUser;
    }

    public boolean notifyNewLoginFrom(int ahtvUserId, String userProfileType)
    {
        ActivityLogger.info("Notifying new login from ahtv user with id:" + ahtvUserId + " and type:" + userProfileType);

        Connection connection = itsDbConnectionProvider.connection();
        int resultCode = -1;
        String procedureCallSql = "call pkg_fe_ahtv_login.SetUserProfile (?,?,?,?)";
        try
        {
            CallableStatement callableStatement = connection.prepareCall(procedureCallSql);

            callableStatement.setInt(1, ahtvUserId);
            callableStatement.setString(2, userProfileType);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.NUMERIC);
            callableStatement.execute();

            String resultMessage = callableStatement.getString(3);
            resultCode = callableStatement.getInt(4);

            ActivityLogger.info("Invoked procedure " + procedureCallSql + "; result:" + resultMessage + "; result code:" + resultCode);
        }
        catch (SQLException sqlException)
        {
            ActivityLogger.logException(sqlException);
        }
        finally
        {
            itsDbConnectionProvider.close(connection);
        }
        return isOk(resultCode);
    }

    private boolean saveOTPFor(int ahtvUserId, String otpType)
    {
        String generatedPassword = itsPasswordGenerator.generateNewPassword();
        ActivityLogger.info("Saving OTP:" + generatedPassword + " for user id:" + ahtvUserId + " for type:" + otpType);

        Connection connection = itsDbConnectionProvider.connection();
        int resultCode = -1;
        String procedureCallSql = "call pkg_fe_ahtv_otp.setOtpUser (?,?,?,?,?)";
        try
        {
            CallableStatement callableStatement = connection.prepareCall(procedureCallSql);

            callableStatement.setInt(1, ahtvUserId);
            callableStatement.setString(2, generatedPassword);
            callableStatement.setString(3, otpType);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.NUMERIC);
            callableStatement.execute();

            String resultMessage = callableStatement.getString(4);
            resultCode = callableStatement.getInt(5);

            ActivityLogger.info("Invoked procedure " + procedureCallSql + "; result:" + resultMessage + "; result code:" + resultCode);
        }
        catch (SQLException sqlException)
        {
            ActivityLogger.logException(sqlException);
        }
        finally
        {
            itsDbConnectionProvider.close(connection);
        }
        return isOk(resultCode);
    }

    private boolean isOk(int resultCode)
    {
        return resultCode == 0;
    }
    
    private void computePendingDataOn(AhtvUser ahtvUser)
    {
        ActivityLogger.debug("Computing pending data for user with id:" + ahtvUser.ahtvId());
        
        int resultCode = -1;
        boolean hasPendingMobile = false;
        boolean hasPendingEmail = false;
        String procedureCallSql = "call pkg_fe_ahtv_otp.getOTPUserStatus (?,?,?,?,?)";
        ActivityLogger.debug("Preparing call to procedure " + procedureCallSql);
        Connection connection = itsDbConnectionProvider.connection();
        try
        {
            CallableStatement callableStatement = connection.prepareCall(procedureCallSql);

            callableStatement.setInt(1, ahtvUser.ahtvId());
            callableStatement.registerOutParameter(2, Types.NUMERIC);
            callableStatement.registerOutParameter(3, Types.NUMERIC);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.NUMERIC);
            callableStatement.execute();

            hasPendingMobile = callableStatement.getInt(2) != 0;
            hasPendingEmail = callableStatement.getInt(3) != 0;
            String resultMessage = callableStatement.getString(4);
            resultCode = callableStatement.getInt(5);

            ActivityLogger.info("Invoked procedure " + procedureCallSql + "; result:" + resultMessage + "; result code:" + resultCode);
        }
        catch (SQLException sqlException)
        {
            ActivityLogger.logException(sqlException);
        }
        finally
        {
            itsDbConnectionProvider.close(connection);
        }

        if (isOk(resultCode))
        {
            ahtvUser.certifyAhtvMobile(!hasPendingMobile);
            ahtvUser.certifyAhtvEMail(!hasPendingEmail);
        }
        else
            ActivityLogger.warning("Cannot compute pending data (email and mobile) for user:" + ahtvUser);
    }

    private boolean updateAhtvMobileNumberFor(int ahtvUserId, String newAhtvMobileNumber)
    {
        boolean updatedSuccessfully = false;
        if (isNotEmpty(newAhtvMobileNumber))
        {
            ActivityLogger.info("Updating ahtv mobile:" + newAhtvMobileNumber + " for user id:" + ahtvUserId);

            PhoneResponse response = itsUserPrivacyService.modifyUserPhone(AHTV_CHANNEL_ID, ahtvUserId, null, PhoneUtils.MOBILE_AHTV_TYPE, newAhtvMobileNumber);
            updatedSuccessfully = response.isOk();
            if (updatedSuccessfully)
            {
                ActivityLogger.info("Notifying pending mobile:" + newAhtvMobileNumber + " for ahtv user id:" + ahtvUserId);
                notifyPendingMobileForUser(ahtvUserId);
            }
            else
                ActivityLogger.warning("Updating ahtv mobile fails: " + response.getResponse());
        }
        return updatedSuccessfully;
    }

    private boolean updateAhtvEmailFor(int ahtvUserId, String newAhtvEmailAddress)
    {
        boolean updatedSuccessfully = false;
        if (isNotEmpty(newAhtvEmailAddress))
        {
            ActivityLogger.info("Updating ahtv email:" + newAhtvEmailAddress + " for user id:" + ahtvUserId);

            EmailResponse response = itsUserPrivacyService.modifyUserEmail(AHTV_CHANNEL_ID, ahtvUserId, null, EmailUtils.EMAIL_AHTV_TYPE, newAhtvEmailAddress);
            updatedSuccessfully = response.isOk();
            if (updatedSuccessfully)
            {
                ActivityLogger.info("Notifying pending email:" + newAhtvEmailAddress + " for ahtv user id:" + ahtvUserId);
                notifyPendingEmailForUser(ahtvUserId);
            }
            else
                ActivityLogger.warning("Updating ahtv email fails: " + response.getResponse());
        }
        return updatedSuccessfully;
    }

    private boolean updateOriginalMobileNumberFor(int ahtvUserId, String aNewSunriseMobileNumber)
    {
        ActivityLogger.info("Updating sunrise mobile:" + aNewSunriseMobileNumber + " for user id:" + ahtvUserId);
        PhoneResponse response = itsUserPrivacyService.modifyUserPhone(AHTV_CHANNEL_ID, ahtvUserId, null, PhoneUtils.MOBILE_SUNRISE_TYPE, aNewSunriseMobileNumber);
        return response.isOk();
    }

    private boolean updateOriginalEmailFor(int ahtvUserId, String aNewSunriseEmailAddress)
    {
        ActivityLogger.info("Updating sunrise email:" + aNewSunriseEmailAddress + " for user id:" + ahtvUserId);
        EmailResponse response = itsUserPrivacyService.modifyUserEmail(AHTV_CHANNEL_ID, ahtvUserId, null, EmailUtils.EMAIL_SUNRISE_TYPE, aNewSunriseEmailAddress);
        return response.isOk();
    }

    private UserPDP findUserPDPBy(String aSunrisePassportCode)
    {
        return itsUserPrivacyService.loadUserByExtID(aSunrisePassportCode, AHTV_CHANNEL_ID, EmailUtils.ALL_TYPES, PhoneUtils.ALL_TYPES);
    }
}
