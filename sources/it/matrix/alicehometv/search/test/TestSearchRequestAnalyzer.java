package it.matrix.alicehometv.search.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.search.SearchRequest;
import it.matrix.alicehometv.search.SearchRequestAnalyzer;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class TestSearchRequestAnalyzer
{
    private Map<String, String[]> incomingParametersMap;

    @Before
    public void setUp()
    {
        incomingParametersMap = new HashMap<String, String[]>();
    }

    @Test
    public void testQueryStringParameter()
    {
        SearchRequestAnalyzer requestAnalyzerWithNoParams = new SearchRequestAnalyzer(Collections.EMPTY_MAP);
        assertNull(requestAnalyzerWithNoParams.queryString());

        incomingParametersMap.put(SearchRequest.QUERY_STRING, new String[] { "a string to search" });
        assertEquals("a string to search", new SearchRequestAnalyzer(incomingParametersMap).queryString());
    }
    
    @Test
    public void testIsAdvancedSearch()
    {
        SearchRequestAnalyzer requestAnalyzerWithNoParams = new SearchRequestAnalyzer(Collections.EMPTY_MAP);
        assertFalse(requestAnalyzerWithNoParams.isAdvancedSearch());

        incomingParametersMap.put("AdvancedSearch", new String[] { "anyValue" });
        assertTrue(new SearchRequestAnalyzer(incomingParametersMap).isAdvancedSearch());
    }

    @Test
    public void testIsSearchingFromAlertingSection()
    {
        SearchRequestAnalyzer requestAnalyzerWithNoParams = new SearchRequestAnalyzer(Collections.EMPTY_MAP);
        assertFalse(requestAnalyzerWithNoParams.isSearchingFromAlertingSection());

        incomingParametersMap.put("isSearchingFromAlertingSection", new String[] { "anyValue" });
        assertTrue(new SearchRequestAnalyzer(incomingParametersMap).isSearchingFromAlertingSection());
    }

    @Test
    public void testIsANextPageOfTheSearch()
    {
        SearchRequestAnalyzer requestAnalyzerWithNoParams = new SearchRequestAnalyzer(Collections.EMPTY_MAP);
        assertFalse("When Offset is not present, it should be considered a first page search", requestAnalyzerWithNoParams.isANextPageOfTheSearch());

        SearchRequestAnalyzer requestAnalyzer = new SearchRequestAnalyzer(incomingParametersMap);

        incomingParametersMap.put(SearchRequest.OFFSET, new String[] { "0" });
        assertFalse("When Offset is 0, it should be considered a first page search", requestAnalyzer.isANextPageOfTheSearch());

        incomingParametersMap.put(SearchRequest.OFFSET, new String[] { "10" });
        assertTrue("When Offset is present and is not 0, it should be considered a 'next page' search", requestAnalyzer.isANextPageOfTheSearch());
    }

    @Test
    public void testContainsParameter() throws Exception
    {
        HashMap<String, String[]> parameters = new HashMap<String, String[]>();
        SearchRequestAnalyzer requestAnalyzer = new SearchRequestAnalyzer(parameters);

        assertFalse(requestAnalyzer.containsParameter("a parameter key"));

        parameters.put("a parameter key", new String[] { "a value" });

        assertTrue(requestAnalyzer.containsParameter("a parameter key"));
    }
}
