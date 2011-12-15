package it.matrix.alicehometv.search.test;

import static it.matrix.alicehometv.search.SearchRequest.*;
import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.search.SearchRequest;

import java.util.Arrays;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;


public class TestSearchRequest
{
    @Test
    public void sourceParameterShouldBeSetInCostruction() throws Exception
    {
        assertEquals(SearchRequest.CMS_SOURCE, SearchRequest.forCMS().source());
        assertEquals(SearchRequest.EPG_SOURCE, SearchRequest.forEPG().source());
        assertEquals(SearchRequest.VOD_SOURCE, SearchRequest.forVOD().source());
    }

    @Test
    public void whenUsingSimpleFactoryTypeParameterShouldBeListing() throws Exception
    {
        assertEquals(SearchRequest.TYPE_LISTING_VALUE, SearchRequest.forCMS().valueOf(TYPE));
        assertEquals(SearchRequest.TYPE_LISTING_VALUE, SearchRequest.forEPG().valueOf(TYPE));
        assertEquals(SearchRequest.TYPE_LISTING_VALUE, SearchRequest.forVOD().valueOf(TYPE));
    }
    
    @Test
    public void whenUsingSummaryFactoryTypeParameterShouldSummary() throws Exception
    {
        assertEquals(SearchRequest.TYPE_SUMMARY_VALUE, SearchRequest.summaryForCMS().valueOf(TYPE));
        assertEquals(SearchRequest.TYPE_SUMMARY_VALUE, SearchRequest.summaryForEPG().valueOf(TYPE));
        assertEquals(SearchRequest.TYPE_SUMMARY_VALUE, SearchRequest.summaryForVOD().valueOf(TYPE));
    }
    
    @Test
    public void encodingParameterShouldBeISO_8859_1() throws Exception
    {
        assertEquals("ISO-8859-1", SearchRequest.forCMS().valueOf(ENCODING));
        assertEquals("ISO-8859-1", SearchRequest.forEPG().valueOf(ENCODING));
        assertEquals("ISO-8859-1", SearchRequest.forVOD().valueOf(ENCODING));

    }

    @Test
    public void optionalParametersNotSetShouldHaveNullValue() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forCMS();
        
        for (String optionalParameter : SearchRequest.allOptionalParameters())
            assertNull(searchRequest.valueOf(optionalParameter));            
    }
    
    @Test
    public void emptyOptionalParametersShouldHaveNullValue() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forCMS().addArea(StringUtils.EMPTY);
        
        assertNull(searchRequest.valueOf(THEMATIC_AREA));
    }

    @Test
    public void addAndRetrieveBasicOptionalParameters() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addQueryString("anyString").addOffset("anyOffset").addHitsPerPage("anyRows");
        
        assertEquals("anyString", searchRequest.valueOf(QUERY_STRING));
        assertEquals("anyOffset", searchRequest.valueOf(OFFSET));
        assertEquals("anyRows", searchRequest.valueOf(HITS_PER_PAGE));
    }
    
    @Test
    public void addAndRetrieveAdvancedOptionalParameters() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG();
        searchRequest.addParentalControlRating("Rosso").addGenre("Azione").addArea("Cinema").addCategory("Poltronissima").addDay("2007-09-18");
        searchRequest.addTitle("Rambo III").addPeople("Sylvester Stallone").addDuration("93").addFirstLetter("R").addFilmNews("N");
        
        assertEquals("Rosso", searchRequest.valueOf(PARENTAL_CONTROL_RATING));
        assertEquals("Azione", searchRequest.valueOf(GENRE));
        assertEquals("Cinema", searchRequest.valueOf(THEMATIC_AREA));
        assertEquals("Poltronissima", searchRequest.valueOf(CATEGORY));
        assertEquals("2007-09-18", searchRequest.valueOf(DAY));
        assertEquals("Rambo III", searchRequest.valueOf(TITLE));
        assertEquals("Sylvester Stallone", searchRequest.valueOf(PEOPLE));
        assertEquals("93", searchRequest.valueOf(DURATION));
        assertEquals("R", searchRequest.valueOf(FIRST_LETTER));
        assertEquals("N", searchRequest.valueOf(FILMNEWS));
    }
    
    @Test
    public void addChannelShouldWrapTheChannelNameInDoubleQuotes() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addChannel("Rai Uno");
        assertEquals("\"Rai Uno\"", searchRequest.valueOf(CHANNEL));
    }
    
    @Test
    public void testAllOptionalParameters() throws Exception
    {
        assertTrue(SearchRequest.allOptionalParameters().containsAll(Arrays.asList(QUERY_STRING, OFFSET, HITS_PER_PAGE, FIRST_LETTER)));
        assertTrue(SearchRequest.allOptionalParameters().containsAll(ALL_ADVANCED_PARAMETERS));
        assertTrue(SearchRequest.allOptionalParameters().contains(CHANNEL));
        
        assertTrue(SearchRequest.allOptionalParameters().containsAll(Arrays.asList(TIME_SLOT_FROM, TIME_SLOT_TO)));
    }
    
    @Test
    public void testAddTimeSlot() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addTimeSlot("2008-01-30", "2100-2400");
        assertEquals("200801302100" , searchRequest.valueOf(TIME_SLOT_FROM));
        assertEquals("200801302400" , searchRequest.valueOf(TIME_SLOT_TO));
    }
    
    @Test
    public void testAddTimeSlotWithNoDay() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addTimeSlot(null, "2100-2400");
        assertNull(searchRequest.valueOf(TIME_SLOT_FROM));
        assertNull(searchRequest.valueOf(TIME_SLOT_TO));
    }
    
    @Test
    public void testAddTimeSlotWithInvalidTimeSlot() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addTimeSlot("2008-01-30", "invalidTimeSlot");
        assertNull(searchRequest.valueOf(TIME_SLOT_FROM));
        assertNull(searchRequest.valueOf(TIME_SLOT_TO));
    }
    
    @Test
    public void testAddTimeSlotWithNullDayAndTimeSlot() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG().addTimeSlot("2008-01-30", null);
        assertNull(searchRequest.valueOf(TIME_SLOT_FROM));
        assertNull(searchRequest.valueOf(TIME_SLOT_TO));
        
        searchRequest = SearchRequest.forEPG().addTimeSlot(null, null);
        assertNull(searchRequest.valueOf(TIME_SLOT_FROM));
        assertNull(searchRequest.valueOf(TIME_SLOT_TO));
    }
    
    @Test
    public void notExistingParameterShouldHaveNullValue() throws Exception
    {
        assertNull(SearchRequest.forVOD().valueOf("notExistingParameterName"));
    }

    @Test
    public void testEquals() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forCMS();
        SearchRequest sameSearchRequest = SearchRequest.forCMS();
        
        assertEquals(searchRequest, sameSearchRequest);
        assertNotSame(searchRequest, sameSearchRequest);
        
        SearchRequest anotherSearchRequest = SearchRequest.forCMS().addQueryString("Hello!");
        assertNotEquals(searchRequest, anotherSearchRequest);
    }
    
    @Test
    public void testToNameValuePairs() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forCMS();
        searchRequest.addQueryString("anyString").addOffset("anyOffset").addHitsPerPage("anyRows");
        searchRequest.addParentalControlRating("anyRate").addGenre("anyGenre").addArea("anyArea").addCategory("anyCategory").addChannel("anyChannel");
        searchRequest.addTitle("anyTitle").addPeople("anyPerson").addDuration("anyDuration").addFirstLetter("anyLetter").addFilmNews("anyNewsFlag");
        searchRequest.addDay("anyDay").addTimeSlot("anyDay", "from-to");
        
        Object[] expected = new NameValuePair[] { 
                new NameValuePair(SOURCE, CMS_SOURCE),
                new NameValuePair(TYPE, TYPE_LISTING_VALUE),
                new NameValuePair(ENCODING, ENCODING_VALUE),
                new NameValuePair(QUERY_STRING, "anyString"), 
                new NameValuePair(OFFSET, "anyOffset"), 
                new NameValuePair(HITS_PER_PAGE, "anyRows"),
                new NameValuePair(PARENTAL_CONTROL_RATING, "anyRate"),
                new NameValuePair(GENRE, "anyGenre"),
                new NameValuePair(THEMATIC_AREA, "anyArea"),
                new NameValuePair(CATEGORY, "anyCategory"),
                new NameValuePair(DAY, "anyDay"),
                new NameValuePair(TITLE, "anyTitle"),
                new NameValuePair(PEOPLE, "anyPerson"),
                new NameValuePair(FILMNEWS, "anyNewsFlag"),
                new NameValuePair(DURATION, "anyDuration"),
                new NameValuePair(FIRST_LETTER, "anyLetter"),
                new NameValuePair(CHANNEL, "\"anyChannel\""),
                new NameValuePair(TIME_SLOT_FROM, "anyDayfrom"),
                new NameValuePair(TIME_SLOT_TO, "anyDayto")
                };

        assertArrayEqualsIgnoringOrder(expected, searchRequest.toNameValuePairs());
    }

    public void onlyMandatoryParametersShouldAlwaysBePresentInTheNameValuePais() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.summaryForEPG();

        Object[] expected = new NameValuePair[] { 
                new NameValuePair(SOURCE, EPG_SOURCE), 
                new NameValuePair(TYPE, TYPE_SUMMARY_VALUE), 
                new NameValuePair(ENCODING, ENCODING_VALUE) 
                };

        assertArrayEquals(expected, searchRequest.toNameValuePairs());
    }

    @Test
    public void specialCharsForFASTShouldBeReplacedWithWhiteSpacesInTheQueryStringParameterValue() throws Exception
    {
        SearchRequest searchRequest = SearchRequest.forEPG();
        searchRequest.addQueryString("!some,words#with%illegal&chars(will*be/stripped.with=spaces[");

        assertEquals(" some words with illegal chars will be stripped with spaces ", searchRequest.valueOf(QUERY_STRING));
    }    
    
}
