package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.search.SearchExecutor;
import it.matrix.alicehometv.search.SearchRequest;
import it.matrix.alicehometv.servlet.SearchServlet;

import java.io.IOException;
import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestSearchServlet extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();
    
    private SearchExecutor mockSearchExecutor;

    private ArrayList<SearchRequest> itsSearchRequests;

    @Before
    public void setUp()
    {
        mockSearchExecutor = context.mock(SearchExecutor.class);
        
        itsSearchRequests = new ArrayList<SearchRequest>();
    }

    @Test
    public void shouldInvokeTheSearchExecutor() throws Exception
    {
        fakeRequest().addParameter(SearchRequest.QUERY_STRING, "a query string");

        itsSearchRequests.add(SearchRequest.forEPG().addQueryString("a query string"));
        itsSearchRequests.add(SearchRequest.forVOD().addQueryString("a query string"));
        itsSearchRequests.add(SearchRequest.forCMS().addQueryString("a query string"));
        
        context.checking(new Expectations() 
        {{
            one (mockSearchExecutor).runOn(itsSearchRequests); will(returnValue("<RESULTSET TOTALHITS=\"1\">a resulting xml</RESULTSET>"));
        }});
        
        new SearchServletForTest(mockSearchExecutor).doGet(fakeRequest(), fakeResponse());
        
        assertEquals("<RESULTSET TOTALHITS=\"1\">a resulting xml</RESULTSET>\r\n", fakeResponse().getContentAsString());
    }

    @Test
    public void shouldPassPaginationParametersOnlyForListingSearchRequests() throws Exception
    {
        itsSearchRequests.add(SearchRequest.summaryForEPG().addQueryString("a query string"));
        itsSearchRequests.add(SearchRequest.summaryForVOD().addQueryString("a query string"));
        itsSearchRequests.add(SearchRequest.forCMS().addQueryString("a query string").addOffset("5").addHitsPerPage("10"));
        
        context.checking(new Expectations() 
        {{
            one (mockSearchExecutor).runOn(itsSearchRequests); will(returnValue("<RESULTSET TOTALHITS=\"1\">a resulting xml</RESULTSET>"));
        }});
        
        fakeRequest().addParameter(SearchRequest.SOURCE, SearchRequest.CMS_SOURCE);
        fakeRequest().addParameter(SearchRequest.QUERY_STRING, "a query string");
        fakeRequest().addParameter(SearchRequest.OFFSET, "5");
        fakeRequest().addParameter(SearchRequest.HITS_PER_PAGE, "10");
        
        new SearchServletForTest(mockSearchExecutor).doGet(fakeRequest(), fakeResponse());
    }
    
    @Test
    public void shouldThrowsAnIoExceptionWhenSearchFails() throws Exception
    {
        itsSearchRequests.add(SearchRequest.forEPG());
        itsSearchRequests.add(SearchRequest.forVOD());
        itsSearchRequests.add(SearchRequest.forCMS());
        
        context.checking(new Expectations() 
        {{
            one (mockSearchExecutor).runOn(itsSearchRequests); will(throwException(new IOException("test-generated exception")));
        }});

        try
        {
            new SearchServletForTest(mockSearchExecutor).doGet(fakeRequest(), fakeResponse());
            fail();
        }
        catch (IOException expectedException)
        {
        }
    }
    
    @SuppressWarnings("serial")
    private class SearchServletForTest extends SearchServlet
    {
        private SearchExecutor itsSearchExecutor;
        
        public SearchServletForTest(SearchExecutor searchExecutor)
        {
            itsSearchExecutor = searchExecutor;
        }
        @Override
        protected SearchExecutor searchExecutor()
        {
            return itsSearchExecutor;
        }
    }
    
}
