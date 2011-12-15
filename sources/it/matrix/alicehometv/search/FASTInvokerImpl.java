package it.matrix.alicehometv.search;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.util.StreamReader;

import java.io.IOException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;

public class FASTInvokerImpl implements FASTInvoker
{
    private HttpClient itsHttpClient;
    private HttpMethodBase itsHttpMethod;

    public FASTInvokerImpl(String baseUrl, int port, String wrapperPath)
    {
        itsHttpClient = new HttpClient();
        HostConfiguration hostConfiguration = itsHttpClient.getHostConfiguration();
        hostConfiguration.setHost(baseUrl, port);

        itsHttpMethod = new GetMethod();
        itsHttpMethod.setPath(wrapperPath);
    }
    
    public String on(SearchRequest searchRequest) throws IOException
    {
        String response = null;

        String urlQueryString = EncodingUtil.formUrlEncode(searchRequest.toNameValuePairs(), SearchRequest.ENCODING_VALUE);
        itsHttpMethod.setQueryString(urlQueryString);
        try
        {
            ActivityLogger.info("calling FAST on " + itsHttpClient.getHostConfiguration().getHostURL() + itsHttpMethod.getURI());
            
            itsHttpClient.executeMethod(itsHttpMethod);
            response = new StreamReader().readFrom(itsHttpMethod.getResponseBodyAsStream(), itsHttpMethod.getResponseCharSet());
            ActivityLogger.debug("Response is " + response);
        }
        finally
        {
            itsHttpMethod.releaseConnection();
        }
        return response;
    }

    public String queryString()
    {
        return itsHttpMethod.getQueryString();
    }
}
