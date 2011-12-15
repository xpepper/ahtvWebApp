package it.matrix.alicehometv.captcha;

import it.matrix.alicehometv.logger.ActivityLogger;

import com.octo.captcha.service.CaptchaServiceException;

public class CaptchaValidatorImpl implements CaptchaValidator 
{
    public boolean validate(String captchaId, String captchaResponse)
    {
        boolean isCaptchaResponseCorrect = false;
        try
        {
            isCaptchaResponseCorrect = CaptchaServiceSigleton.instance().validateResponseForID(captchaId, captchaResponse).booleanValue();
        }
        catch (CaptchaServiceException e)
        {
            ActivityLogger.logException("Exception during captcha validation", e);
        }
        
        ActivityLogger.info("Captcha for id:" + captchaId + " and response:" + captchaResponse + " is valid:" + isCaptchaResponseCorrect);
        
        return isCaptchaResponseCorrect;
    }
}
