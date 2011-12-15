package it.matrix.alicehometv.servlet.test;

import it.matrix.alicehometv.service.OTPVerificationResult;

public class ServiceResultCreator
{

    public static OTPVerificationResult allOkResult()
    {
        return new OTPVerificationResult(0, "OK", true, true);
    }

}
