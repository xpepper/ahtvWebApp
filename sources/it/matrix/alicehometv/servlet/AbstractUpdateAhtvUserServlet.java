package it.matrix.alicehometv.servlet;

import static org.apache.commons.lang.StringUtils.*;
import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.captcha.CaptchaValidator;
import it.matrix.alicehometv.captcha.CaptchaValidatorImpl;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("serial")
public abstract class AbstractUpdateAhtvUserServlet extends AbstractMyAhtvJsonServlet
{
    private static final String CAPTCHA_RESPONSE_PARAMETER_NAME = "captchaResponse";
    private static final String MOBILE_NUMBER_PARAMETER_NAME = "mobileNumber";
    private static final String EMAIL_ADDRESS_PARAMETER_NAME = "emailAddress";

    protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        String emailAddress = request.getParameter(EMAIL_ADDRESS_PARAMETER_NAME);
        String mobileNumber = request.getParameter(MOBILE_NUMBER_PARAMETER_NAME);
        if (isNotEmpty(mobileNumber))
        {
            String captchaId = request.getSession().getId();
            String captchaResponse = request.getParameter(CAPTCHA_RESPONSE_PARAMETER_NAME);
            boolean passedCaptchaVerification = captchaValidator().validate(captchaId, captchaResponse);

            if (!passedCaptchaVerification)
                return converter.toXML(ServiceResponse.errorResponseWith("invalid captcha provided"));
        }

        boolean updateSuccessfully = saveOrUpdateWith(emailAddress, mobileNumber, request, response);

        ServiceResponse serviceResponse = null;
        if (updateSuccessfully)
            serviceResponse = ServiceResponse.okResponseWith("user saved successfully");
        else
            serviceResponse = ServiceResponse.errorResponseWith("user not saved");

        return converter.toXML(serviceResponse);
    }

    protected abstract boolean saveOrUpdateWith(String emailAddress, String mobileNumber, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException;

    protected CaptchaValidator captchaValidator()
    {
        return new CaptchaValidatorImpl();
    }
}
