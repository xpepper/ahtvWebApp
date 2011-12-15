package it.matrix.alicehometv.search.test;

import static org.fest.assertions.Assertions.*;
import it.matrix.alicehometv.search.SearchRequest;
import it.matrix.alicehometv.search.SearchRequestBuilder;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class TestSearchRequestBuilder
{
    private Map<String, String[]> incomingParametersMap;

    @Before
    public void setUp()
    {
        incomingParametersMap = new HashMap<String, String[]>();
    }

    @Test
    public void shouldBuildASearchOnAllTheThreeSourcesWhenSourceParameterIsNotDefined() throws Exception
    {
        List<SearchRequest> searchRequests = new SearchRequestBuilder(Collections.EMPTY_MAP).build();

        assertThat(searchRequests).hasSize(3).as("three search parameters instance should be returned");
        assertThat(searchRequests).contains(SearchRequest.forEPG(), SearchRequest.forVOD(), SearchRequest.forCMS());
    }

    @Test
    public void shouldBuildASearchOnAllTheThreeSourcesWhenSourceParameterIsEmpty() throws Exception
    {
        incomingParametersMap.put(SearchRequest.SOURCE, new String[0]);
        List<SearchRequest> searchRequests = new SearchRequestBuilder(incomingParametersMap).build();

        assertThat(searchRequests).hasSize(3).as("three search parameters instance should be returned");
        assertThat(searchRequests).contains(SearchRequest.forEPG(), SearchRequest.forVOD(), SearchRequest.forCMS());
    }

    @Test
    public void whenAlmostOneSourceIsDefinedShouldReturnASummarySearchForEachUndefinedSource() throws Exception
    {
        incomingParametersMap.put(SearchRequest.SOURCE, new String[] { SearchRequest.EPG_SOURCE });
        List<SearchRequest> searchRequests = new SearchRequestBuilder(incomingParametersMap).build();

        assertThat(searchRequests).contains(SearchRequest.forEPG());
        assertThat(searchRequests).contains(SearchRequest.summaryForCMS(), SearchRequest.summaryForCMS());
    }

    @Test
    @SuppressWarnings("serial")
    public void shoulBuildUsingAllDefinedParameters() throws Exception
    {
        incomingParametersMap = new HashMap<String, String[]>()
        {{
            put(SearchRequest.SOURCE, new String[] { "epg" });
            put(SearchRequest.QUERY_STRING, new String[] { "some words" });
            put(SearchRequest.OFFSET, new String[] { "5" });
            put(SearchRequest.HITS_PER_PAGE, new String[] { "30" });
            put(SearchRequest.PARENTAL_CONTROL_RATING, new String[] { "Verde" });
            put(SearchRequest.GENRE, new String[] { "Azione" });
            put(SearchRequest.THEMATIC_AREA, new String[] { "Sport" });
            put(SearchRequest.CATEGORY, new String[] { "SerieTV" });
            put(SearchRequest.DAY, new String[] { "2007-09-18" });
            put(SearchRequest.TITLE, new String[] { "Rambo III" });
            put(SearchRequest.PEOPLE, new String[] { "Sylvester Stallone" });
            put(SearchRequest.FIRST_LETTER, new String[] { "R" });
            put(SearchRequest.FILMNEWS, new String[] { "N" });
            put(SearchRequest.DURATION, new String[] { "92" });
            put(SearchRequest.CHANNEL, new String[] { "Rai Uno" });
            put(SearchRequestBuilder.TIME_SLOT, new String[] { "1900-2100" });
        }};

        SearchRequestBuilder searchRequestBuilder = new SearchRequestBuilder(incomingParametersMap);
        List<SearchRequest> searchRequests = searchRequestBuilder.build();

        assertThat(searchRequests).hasSize(3);

        SearchRequest expectedFullyDefinedRequest = SearchRequest.forEPG();
        expectedFullyDefinedRequest.addQueryString("some words").addOffset("5").addHitsPerPage("30");
        expectedFullyDefinedRequest.addParentalControlRating("Verde").addGenre("Azione").addArea("Sport").addCategory("SerieTV").addDay("2007-09-18");
        expectedFullyDefinedRequest.addTitle("Rambo III").addPeople("Sylvester Stallone").addDuration("92").addFirstLetter("R").addFilmNews("N");
        expectedFullyDefinedRequest.addChannel("Rai Uno").addTimeSlot("2007-09-18", "1900-2100");
        
        assertThat(searchRequests).contains(expectedFullyDefinedRequest);
    }

    @Test
    public void summarySearchRequestsShouldHaveTheSameParametersOfListingSearchRequest() throws Exception
    {
        incomingParametersMap.put(SearchRequest.SOURCE, new String[] { "epg" });
        incomingParametersMap.put(SearchRequest.QUERY_STRING, new String[] { "some words" });
        incomingParametersMap.put(SearchRequest.THEMATIC_AREA, new String[] { "Sport" });
        incomingParametersMap.put(SearchRequest.CATEGORY, new String[] { "SerieTV" });

        List<SearchRequest> searchRequests = new SearchRequestBuilder(incomingParametersMap).build();

        assertThat(searchRequests).hasSize(3);
        assertThat(searchRequests).contains(SearchRequest.forEPG().addQueryString("some words").addArea("Sport").addCategory("SerieTV"));
        assertThat(searchRequests).contains(SearchRequest.summaryForCMS().addQueryString("some words").addArea("Sport").addCategory("SerieTV"));
        assertThat(searchRequests).contains(SearchRequest.summaryForCMS().addQueryString("some words").addArea("Sport").addCategory("SerieTV"));
    }

    @Test
    public void paginationParametersShouldNeverBePresentInTheSummaryRequests() throws Exception
    {
        incomingParametersMap.put(SearchRequest.SOURCE, new String[] { "epg" });
        incomingParametersMap.put(SearchRequest.QUERY_STRING, new String[] { "some words" });
        incomingParametersMap.put(SearchRequest.OFFSET, new String[] { "5" });
        incomingParametersMap.put(SearchRequest.HITS_PER_PAGE, new String[] { "30" });

        List<SearchRequest> searchRequests = new SearchRequestBuilder(incomingParametersMap).build();

        assertThat(searchRequests).hasSize(3);
        assertThat(searchRequests).contains(SearchRequest.forEPG().addQueryString("some words").addOffset("5").addHitsPerPage("30"));
        assertThat(searchRequests).contains(SearchRequest.summaryForCMS().addQueryString("some words"));
        assertThat(searchRequests).contains(SearchRequest.summaryForCMS().addQueryString("some words"));
    }
}
