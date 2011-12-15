package it.matrix.alicehometv.search;

import static it.matrix.alicehometv.search.SearchRequest.*;
import static it.matrix.alicehometv.util.StringUtilies.*;
import static org.apache.commons.lang.StringUtils.*;

import java.util.Map;

public class SearchRequestAnalyzer
{
    private static final String ADVANCED_SEARCH_FLAG = "AdvancedSearch";
    private static final String ALERTING_SEARCH_FLAG = "isSearchingFromAlertingSection";
    
    private final Map<String, String[]> itsParameterMap;

    public SearchRequestAnalyzer(Map<String, String[]> aParameterMap)
    {
        itsParameterMap = aParameterMap;
    }

    public boolean isAdvancedSearch()
    {
        return containsParameter(ADVANCED_SEARCH_FLAG);
    }
    
    public boolean isSearchingFromAlertingSection()
    {
        return containsParameter(ALERTING_SEARCH_FLAG);
    }

    public boolean isANextPageOfTheSearch()
    {
        String offset = firstValueFrom(itsParameterMap.get(OFFSET));
        return isNotEmpty(offset) && notEquals("0", offset);
    }

    public boolean containsParameter(Object aKey)
    {
        return itsParameterMap.containsKey(aKey);
    }
   
    public String queryString()
    {
        return firstValueFrom(itsParameterMap.get(SearchRequest.QUERY_STRING));
    }
}
