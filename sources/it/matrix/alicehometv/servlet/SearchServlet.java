package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.logger.TraceLogger;
import it.matrix.alicehometv.search.*;
import it.matrix.alicehometv.util.CookieValueDetector;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SearchServlet extends AbstractAhtvServlet
{
    protected static String FAST_HOST;
    protected static int FAST_PORT;
    protected static String FAST_WRAPPER_PATH;

    @Override
    public void init() throws ServletException
    {
        super.init();
        
        configureFAST();
    }

    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        SearchRequestBuilder searchRequestBuilder = new SearchRequestBuilder(request.getParameterMap());
        List<SearchRequest> searchRequests = searchRequestBuilder.build();
        String searchResponse = searchExecutor().runOn(searchRequests);
        ActivityLogger.debug(searchResponse);

        SearchRequestAnalyzer searchRequestAnalyzer = new SearchRequestAnalyzer(request.getParameterMap());
        CookieValueDetector cookieValueDetector = new CookieValueDetector(request.getCookies());
        TraceLogger.traceUsing(cookieValueDetector, searchRequestAnalyzer, searchResponse);
        
        PrintWriter writer = response.getWriter();
        writer.println(searchResponse);
    }

    protected void configureFAST()
    {
        FAST_HOST = getInitParameter("FAST.host");
        FAST_PORT = Integer.parseInt(getInitParameter("FAST.port"));
        FAST_WRAPPER_PATH = getInitParameter("FAST.path");
    }
    
    protected SearchExecutor searchExecutor()
    {
        FASTInvoker fastInvoker = new FASTInvokerImpl(FAST_HOST, FAST_PORT, FAST_WRAPPER_PATH);
        return new SearchExecutorImpl(fastInvoker);
    }    
}
