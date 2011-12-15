package it.matrix.alicehometv.search;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.util.RemoveXMLDeclaration;

import java.io.IOException;
import java.util.List;

public class SearchExecutorImpl implements SearchExecutor
{
    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"" + SearchRequest.ENCODING_VALUE + "\"?>";
    private FASTInvoker itsFASTInvoker;

    public SearchExecutorImpl(FASTInvoker aFASTInvoker)
    {
        itsFASTInvoker = aFASTInvoker;
    }

    public String runOn(List<SearchRequest> searchRequests) throws IOException
    {
        StringBuffer response = new StringBuffer();
        response.append(XML_DECLARATION + "\n" + "<COMPOSITE-RESULTPAGE>\n");
        for (SearchRequest eachRequest : searchRequests)
        {
            try
            {
                String responseFromFAST = itsFASTInvoker.on(eachRequest);
                String responseWithoutXMLDeclaration = RemoveXMLDeclaration.on(responseFromFAST);
                response.append(responseWithoutXMLDeclaration);
            }
            catch (Exception exception)
            {
                ActivityLogger.logException(exception);
                throw new IOException(exception);
            }
        }
        response.append("</COMPOSITE-RESULTPAGE>");

        return response.toString();
    }
}
