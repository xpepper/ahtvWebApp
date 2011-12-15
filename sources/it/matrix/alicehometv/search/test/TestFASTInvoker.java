package it.matrix.alicehometv.search.test;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.search.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestFASTInvoker
{
    private FASTInvokerImpl itsFastInvoker;

    @Before
    public void setup()
    {
        itsFastInvoker = new FASTInvokerImpl("wrapper.alice.it.master", 10015, "/iptv/cgi/wrapper.cgi");
    }
    
    @Test public void responseShouldContainTheSearchedTerms() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forVOD().addQueryString("some words");
        String response = itsFastInvoker.on(searchRequest);
        
        assertNotNull("response should be not null", response);
        assertThat(response).contains("some words");
    }
    
    @Test public void urlQueryStringShouldCointainAllTheParameters() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forVOD().addQueryString("some words to search about").addOffset("5").addHitsPerPage("20");
        itsFastInvoker.on(searchRequest);
        
        assertThat(itsFastInvoker.queryString()).contains("source=vod");
        assertThat(itsFastInvoker.queryString()).contains("encoding=ISO-8859-1");
        assertThat(itsFastInvoker.queryString()).contains("qs=some+words+to+search+about");
        assertThat(itsFastInvoker.queryString()).contains("offset=5");
        assertThat(itsFastInvoker.queryString()).contains("hits=20");
    }
    
    @Test public void urlQueryStringMayCointainTheChannelParameter() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addChannel("Rete 4");
        itsFastInvoker.on(searchRequest);
        
        assertThat(itsFastInvoker.queryString()).contains("channel=%22Rete+4%22");
    }
    
    @Test public void nonASCIICharsInQueryStringShouldBeEncoded() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forVOD().addQueryString("perché no");
        itsFastInvoker.on(searchRequest);

        assertThat(itsFastInvoker.queryString()).contains("qs=perch%E9+no");
    }
    
    @Test public void shouldThrowIOExceptionIfFASTIsNotReachable() 
    {
        String response = null;
        FASTInvoker failingFastInvoker = new FASTInvokerImpl("notReachableHost", 10015, "/iptv/cgi/wrapper.cgi");

        ActivityLogger.off();
        try
        {
            response = failingFastInvoker.on(SearchRequest.summaryForCMS());
            fail();
        }
        catch (IOException expectedException)
        {
        }
        finally
        {
            ActivityLogger.resume();
        }
        
        assertNull(response);
    }

}
