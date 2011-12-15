package it.matrix.alicehometv.captcha;

public interface CaptchaValidator
{
    public abstract boolean validate(String captchaId, String captchaResponse);
}