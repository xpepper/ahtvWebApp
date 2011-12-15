package it.matrix.alicehometv.captcha;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaServiceSigleton
{
    public static ImageCaptchaService instance()
    {
    	DefaultManageableImageCaptchaService serv = new DefaultManageableImageCaptchaService();
    //	serv.setCaptchaEngineClass(AHTVCaptchaEngine.class.getName());

        return serv;
    }
}
