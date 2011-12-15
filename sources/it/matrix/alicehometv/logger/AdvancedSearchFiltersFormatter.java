package it.matrix.alicehometv.logger;

import static it.matrix.alicehometv.search.SearchRequest.*;
import it.matrix.alicehometv.search.SearchRequest;
import it.matrix.alicehometv.search.SearchRequestAnalyzer;

public class AdvancedSearchFiltersFormatter
{
    private SearchRequestAnalyzer itsSearchRequestAnalyzer;

    public AdvancedSearchFiltersFormatter(SearchRequestAnalyzer searchRequestAnalyzer)
    {
        itsSearchRequestAnalyzer = searchRequestAnalyzer;
    }

    public String allFilters()
    {
        StringBuffer collectedFilters = new StringBuffer();
        for (String eachAdvancedParameter : SearchRequest.ALL_ADVANCED_PARAMETERS)
            addParameterIfPresent(eachAdvancedParameter, collectedFilters);
        
        addParameterIfPresent(SearchRequest.FIRST_LETTER, collectedFilters);
        addParameterIfPresent(SearchRequest.SOURCE, collectedFilters);
        
        return collectedFilters.toString();
    }

    private void addParameterIfPresent(String parameter, StringBuffer collector)
    {
        if ((itsSearchRequestAnalyzer.isAdvancedSearch() ||  itsSearchRequestAnalyzer.containsParameter(FIRST_LETTER)) && itsSearchRequestAnalyzer.containsParameter(parameter))
        {
            addFilterSeparatorOn(collector);
            collector.append(parameter);
        }
    }

    private void addFilterSeparatorOn(StringBuffer collector)
    {
        if (collector.length() > 0)
            collector.append("+");
    }

    public String searchType()
    {
        return itsSearchRequestAnalyzer.isAdvancedSearch()? "ADVANCED" : "SIMPLE";
    }

}
