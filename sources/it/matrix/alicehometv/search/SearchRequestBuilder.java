package it.matrix.alicehometv.search;

import static it.matrix.alicehometv.search.SearchRequest.*;
import static it.matrix.alicehometv.util.StringUtilies.*;
import static org.apache.commons.lang.ArrayUtils.*;

import java.util.*;

public class SearchRequestBuilder
{
    private Map<String, String[]> itsParameterMap;

    public static final String TIME_SLOT = "fascia_oraria";

    public SearchRequestBuilder(Map<String, String[]> aParameterMap)
    {
        itsParameterMap = aParameterMap;
    }

    public List<SearchRequest> build()
    {
        ArrayList<SearchRequest> searchRequests = new ArrayList<SearchRequest>();

        List<String> sources = hasValue(SOURCE)? Arrays.asList(itsParameterMap.get(SOURCE)) : ALL_SOURCES; 

        for (String eachSourceType : ALL_SOURCES)
        {
            SearchRequest searchRequest = null;
            if (sources.contains(eachSourceType))
            {
                searchRequest = new SearchRequest(eachSourceType, TYPE_LISTING_VALUE);
                searchRequest.addOffset(firstValueFrom(itsParameterMap.get(SearchRequest.OFFSET)));
                searchRequest.addHitsPerPage(firstValueFrom(itsParameterMap.get(SearchRequest.HITS_PER_PAGE)));
            }
            else
                searchRequest = new SearchRequest(eachSourceType, TYPE_SUMMARY_VALUE);

            searchRequest.addQueryString(firstValueFrom(itsParameterMap.get(SearchRequest.QUERY_STRING)));

            searchRequest.addParentalControlRating(firstValueFrom(itsParameterMap.get(SearchRequest.PARENTAL_CONTROL_RATING)));
            searchRequest.addGenre(firstValueFrom(itsParameterMap.get(SearchRequest.GENRE)));
            searchRequest.addArea(firstValueFrom(itsParameterMap.get(SearchRequest.THEMATIC_AREA)));
            searchRequest.addCategory(firstValueFrom(itsParameterMap.get(SearchRequest.CATEGORY)));
            searchRequest.addDay(firstValueFrom(itsParameterMap.get(SearchRequest.DAY)));
            searchRequest.addTitle(firstValueFrom(itsParameterMap.get(SearchRequest.TITLE)));
            searchRequest.addPeople(firstValueFrom(itsParameterMap.get(SearchRequest.PEOPLE)));
            searchRequest.addDuration(firstValueFrom(itsParameterMap.get(SearchRequest.DURATION)));
            searchRequest.addFilmNews(firstValueFrom(itsParameterMap.get(SearchRequest.FILMNEWS)));
            searchRequest.addFirstLetter(firstValueFrom(itsParameterMap.get(SearchRequest.FIRST_LETTER)));
            searchRequest.addChannel(firstValueFrom(itsParameterMap.get(SearchRequest.CHANNEL)));
            searchRequest.addTimeSlot(searchRequest.valueOf(SearchRequest.DAY), firstValueFrom(itsParameterMap.get(TIME_SLOT)));

            searchRequests.add(searchRequest);
        }
        
        return searchRequests;
    }

    private boolean hasValue(String parameterName)
    {
        String[] valuesForParameter = itsParameterMap.get(parameterName);
        return !isEmpty(valuesForParameter);
    }
}
