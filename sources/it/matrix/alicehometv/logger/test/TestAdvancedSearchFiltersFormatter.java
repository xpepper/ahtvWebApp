package it.matrix.alicehometv.logger.test;

import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.logger.AdvancedSearchFiltersFormatter;
import it.matrix.alicehometv.search.SearchRequest;
import it.matrix.alicehometv.search.SearchRequestAnalyzer;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class TestAdvancedSearchFiltersFormatter
{
    @Test
    public void testAllFilters() throws Exception
    {
        AdvancedSearchFiltersFormatter formatter = new AdvancedSearchFiltersFormatter(new SearchRequestAnalyzer(Collections.EMPTY_MAP));
        assertEmpty(formatter.allFilters());
        
        HashMap<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("AdvancedSearch", null);
        parameterMap.put(SearchRequest.DAY, null);
        parameterMap.put(SearchRequest.THEMATIC_AREA, null);
        parameterMap.put(SearchRequest.SOURCE, null);        

        formatter = new AdvancedSearchFiltersFormatter(new SearchRequestAnalyzer(parameterMap));        
        assertEquals(SearchRequest.THEMATIC_AREA + "+" + SearchRequest.DAY + "+" + SearchRequest.SOURCE, formatter.allFilters());
    }
    
    @Test
    public void firstLetterShouldBeConsideredAnAdvancedSearchFilter() throws Exception
    {
        HashMap<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put(SearchRequest.FIRST_LETTER, null);
        
        AdvancedSearchFiltersFormatter formatter = new AdvancedSearchFiltersFormatter(new SearchRequestAnalyzer(parameterMap));        
        assertEquals(SearchRequest.FIRST_LETTER, formatter.allFilters());
    }
    
    @Test
    public void searchTypeShouldBeSimpleOrAdvanced() throws Exception
    {
        AdvancedSearchFiltersFormatter formatter = new AdvancedSearchFiltersFormatter(new SearchRequestAnalyzer(Collections.EMPTY_MAP));        
        assertEquals("SIMPLE", formatter.searchType());
        
        HashMap<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("AdvancedSearch", null);
        
        formatter = new AdvancedSearchFiltersFormatter(new SearchRequestAnalyzer(parameterMap));        
        assertEquals("ADVANCED", formatter.searchType());
    }
    
    
    @Test
    public void whenOnlySourceIsDefinedShouldNotPutTheSeparatorChar() throws Exception
    {
        HashMap<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("AdvancedSearch", null);
        parameterMap.put(SearchRequest.SOURCE, null);        

        AdvancedSearchFiltersFormatter formatter = new AdvancedSearchFiltersFormatter(new SearchRequestAnalyzer(parameterMap));        
        assertEquals(SearchRequest.SOURCE, formatter.allFilters());
    }

    
}
