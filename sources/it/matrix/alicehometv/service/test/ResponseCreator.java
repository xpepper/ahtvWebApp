package it.matrix.alicehometv.service.test;

import it.matrix.services.userprivacydata.*;

public class ResponseCreator
{
    public static EmailResponse okEmailResponse()
    {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setResponse(okResponse());
        return emailResponse;
    }

    public static PhoneResponse okPhoneResponse()
    {
        PhoneResponse phoneResponse = new PhoneResponse();
        phoneResponse.setResponse(okResponse());
        return phoneResponse;
    }
    
    public static EmailResponse failingEmailResponse()
    {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setResponse(failingResponse());
        return emailResponse;
    }

    private static ResultResponse failingResponse()
    {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(1);
        resultResponse.setMessage("FAILURE!");
        return resultResponse;
    }

    private static ResultResponse okResponse()
    {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(0);
        resultResponse.setMessage("OK");
        return resultResponse;
    }

}
