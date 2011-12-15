package it.matrix.alicehometv.search.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestSearchExecutor
{
    Mockery context = new JUnit4Mockery();
    private FASTInvoker mockFASTInvoker;
    
    @Before
    public void setUp()
    {
        mockFASTInvoker = context.mock(FASTInvoker.class);
    }
    
    @Test
    public void testRunOn() throws Exception
    {
        SearchExecutor searchExecutor = new SearchExecutorImpl(mockFASTInvoker);
        
        context.checking(new Expectations() 
        {{
            one (mockFASTInvoker).on(SearchRequest.forEPG()); will(returnValue("an EPG xml"));
        }});
        
        ArrayList<SearchRequest> searchRequests = new ArrayList<SearchRequest>();
        searchRequests.add(SearchRequest.forEPG());
        
        String response = searchExecutor.runOn(searchRequests);
        
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
        		"<COMPOSITE-RESULTPAGE>\n" +
        		"an EPG xml" +
        		"</COMPOSITE-RESULTPAGE>", 
        		response);
    }
    
    @Test
    public void shouldThrowAnIOExceptionWhenFASTInvokerFails() throws IOException
    {
        SearchExecutor failingSearchExecutor = new SearchExecutorImpl(mockFASTInvoker);
        
        context.checking(new Expectations() 
        {{
            one (mockFASTInvoker).on(SearchRequest.forEPG()); will(throwException(new IOException("FAST failure")));
        }});
        
        ActivityLogger.off();
        try
        {
            failingSearchExecutor.runOn(Arrays.asList(SearchRequest.forEPG()));
            fail();
        }
        catch (IOException expectedException) 
        {
        }
        finally
        {
            ActivityLogger.resume();
        }
    }

}
