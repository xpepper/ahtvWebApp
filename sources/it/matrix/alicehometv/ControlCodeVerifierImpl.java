package it.matrix.alicehometv;

import static org.apache.commons.lang.StringUtils.*;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.service.AhtvUserService;
import it.matrix.alicehometv.service.OTPVerificationResult;

public class ControlCodeVerifierImpl implements ControlCodeVerifier
{
    private final AhtvUserService itsAhtvUserService;

    public ControlCodeVerifierImpl(AhtvUserService ahtvUserService)
    {
        itsAhtvUserService = ahtvUserService;
    }

    public ServiceResponse forUser(int ahtvUserId, String controlCode, String controlCodeType)
    {
        ServiceResponse serviceResponse;
        if (isValid(controlCodeType))
        {
            OTPVerificationResult result = itsAhtvUserService.verifyPendingOTPFor(ahtvUserId, controlCode, controlCodeType);

            if (result.isOk() && result.isControlCodeVerified())
                serviceResponse = ServiceResponse.okResponseWith("control code " + controlCode + " is correct");
            else
                serviceResponse = ServiceResponse.errorResponseWith("control code " + controlCode + " is not correct");
        }
        else
            serviceResponse = ServiceResponse.errorResponseWith("control code type is missing");
        
        ActivityLogger.info("Response for verification for user id" + ahtvUserId + " is:" + serviceResponse);
        return serviceResponse;
    }

    private boolean isValid(String controlCodeType)
    {
        return isNotEmpty(controlCodeType) && (controlCodeType.equals(AhtvUserService.CONTROL_CODE_FOR_EMAIL) || controlCodeType.equals(AhtvUserService.CONTROL_CODE_FOR_MOBILE));
    }
}
