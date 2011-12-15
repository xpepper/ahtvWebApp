package it.matrix.alicehometv.search;

import static org.apache.commons.lang.StringUtils.*;

import java.util.*;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.*;

public class SearchRequest
{
    public static final String SOURCE = "source";
    public static final String TYPE = "type";
    public static final String ENCODING = "encoding";
    
    public static final String QUERY_STRING = "qs";
    public static final String OFFSET = "offset";
    public static final String HITS_PER_PAGE = "hits";
    public static final String FIRST_LETTER = "iniziale";
    public static final String CHANNEL = "channel";

    public static final String PARENTAL_CONTROL_RATING = "livellopc";
    public static final String GENRE = "genere";
    public static final String THEMATIC_AREA = "area";
    public static final String CATEGORY = "categoria";
    public static final String DAY = "startdate";
    public static final String TITLE = "titolo";
    public static final String PEOPLE = "persone";
    public static final String DURATION = "durata";
    public static final String FILMNEWS = "filmnews";
    public static final List<String> ALL_ADVANCED_PARAMETERS = 
        Arrays.asList(PARENTAL_CONTROL_RATING, GENRE, THEMATIC_AREA, CATEGORY, DAY, TITLE, PEOPLE, DURATION, FILMNEWS);

    public static final String TIME_SLOT_FROM = "dfs";
    public static final String TIME_SLOT_TO = "dts";
    
    public static final String VOD_SOURCE = "vod";
    public static final String EPG_SOURCE = "epg";
    public static final String CMS_SOURCE = "edit";
    public static final List<String> ALL_SOURCES = Arrays.asList(EPG_SOURCE, VOD_SOURCE, CMS_SOURCE);

    public static final String ENCODING_VALUE = "ISO-8859-1";
    public static final String TYPE_LISTING_VALUE = "listing";
    public static final String TYPE_SUMMARY_VALUE = "summary";
    
    private static final String TIME_SLOT_SEPARATOR = "-";

    private HashMap<String, String> itsParameterMap;

    @SuppressWarnings("serial")
    SearchRequest(final String sourceToSearchOn, final String typeOfResult)
    {
        itsParameterMap = new HashMap<String, String>() 
        {{
            put(SOURCE, sourceToSearchOn);
            put(TYPE, typeOfResult);
            put(ENCODING, ENCODING_VALUE);
        }};
    }

    public static List<String> allOptionalParameters()
    {
        List<String> parameters = new ArrayList<String>();
        Collections.addAll(parameters, QUERY_STRING, OFFSET, HITS_PER_PAGE, FIRST_LETTER, CHANNEL);
        Collections.addAll(parameters, TIME_SLOT_FROM, TIME_SLOT_TO);
        parameters.addAll(ALL_ADVANCED_PARAMETERS);
        return parameters;
    }
    
    public static SearchRequest forEPG()
    {
        return new SearchRequest(EPG_SOURCE, TYPE_LISTING_VALUE);
    }
    
    public static SearchRequest forVOD()
    {
        return new SearchRequest(VOD_SOURCE, TYPE_LISTING_VALUE);    
    }

    public static SearchRequest forCMS()
    {
        return new SearchRequest(CMS_SOURCE, TYPE_LISTING_VALUE);    
    }

    public static SearchRequest summaryForVOD()
    {
        return new SearchRequest(VOD_SOURCE, TYPE_SUMMARY_VALUE);
    }
    
    public static SearchRequest summaryForEPG()
    {
        return new SearchRequest(EPG_SOURCE, TYPE_SUMMARY_VALUE);
    }

    public static SearchRequest summaryForCMS()
    {
        return new SearchRequest(CMS_SOURCE, TYPE_SUMMARY_VALUE);
    }

    public String valueOf(String parameterName)
    {
        return itsParameterMap.get(parameterName);
    }
    
    public NameValuePair[] toNameValuePairs()
    {
        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new NameValuePair(SOURCE, source()));
        nameValuePairs.add(new NameValuePair(TYPE, valueOf(TYPE)));
        nameValuePairs.add(new NameValuePair(ENCODING, ENCODING_VALUE));

        for (String eachParameter : allOptionalParameters())
        {
            if (isNotEmpty(valueOf(eachParameter)))
                nameValuePairs.add(new NameValuePair(eachParameter, valueOf(eachParameter)));
        }

        return nameValuePairs.toArray(new NameValuePair[0]);
    }
    
    public String source()
    {
        return valueOf(SOURCE);
    }

    public SearchRequest addQueryString(String aStringToSearch)
    {
        String clearedStringToSearch = removeFASTSpecialCharsFrom(aStringToSearch);
        putIfValueIsNotNull(QUERY_STRING, clearedStringToSearch);
        return this;
    }

    private String removeFASTSpecialCharsFrom(String aStringToSearch)
    {
        return replaceChars(aStringToSearch, "!\"#$%&'()*+,-./:;<=>?@[\\]^_",  "                              ");
    }

    public SearchRequest addOffset(String offset)
    {
        putIfValueIsNotNull(OFFSET, offset);
        return this;
    }

    public SearchRequest addHitsPerPage(String hitsPerPage)
    {
        putIfValueIsNotNull(HITS_PER_PAGE, hitsPerPage);
        return this;
    }
    
    public SearchRequest addParentalControlRating(String aRate)
    {
        putIfValueIsNotNull(PARENTAL_CONTROL_RATING, aRate);
        return this;
    }

    public SearchRequest addGenre(String aGenre)
    {
        putIfValueIsNotNull(GENRE, aGenre);
        return this;
    }

    public SearchRequest addArea(String aThematicArea)
    {
        putIfValueIsNotNull(THEMATIC_AREA, aThematicArea);
        return this;
    }

    public SearchRequest addCategory(String aCategory)
    {
        putIfValueIsNotNull(CATEGORY, aCategory);
        return this;
    }

    public SearchRequest addDay(String aDayasYYYYMMDD)
    {
        putIfValueIsNotNull(DAY, aDayasYYYYMMDD);
        return this;
    }

    public SearchRequest addTitle(String aTitle)
    {
        putIfValueIsNotNull(TITLE, aTitle);
        return this;
    }
    
    public SearchRequest addPeople(String people)
    {
        putIfValueIsNotNull(PEOPLE, people);
        return this;
    }

    public SearchRequest addDuration(String duration)
    {
        putIfValueIsNotNull(DURATION, duration);
        return this;
    }

    public SearchRequest addFirstLetter(String firstLetter)
    {
        putIfValueIsNotNull(FIRST_LETTER, firstLetter);
        return this;
    }

    public SearchRequest addFilmNews(String filmNewsFlag)
    {
        putIfValueIsNotNull(FILMNEWS, filmNewsFlag);
        return this;
    }

    public SearchRequest addChannel(String channelName)
    {
        putBetweenDoubleQuotesIfValueIsNotNull(CHANNEL, channelName);
        return this;
    }

    public SearchRequest addTimeSlot(String day, String timeSlot)
    {
        if (isNotEmpty(day) && isValidTimeSlot(timeSlot))
        {
            String dayWithNoDashes = remove(day, "-");
            String[] slots = timeSlot.split(TIME_SLOT_SEPARATOR);
            itsParameterMap.put(TIME_SLOT_FROM, dayWithNoDashes + slots[0]);
            itsParameterMap.put(TIME_SLOT_TO, dayWithNoDashes + slots[1]);
        }
        return this;
    }

    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    private void putIfValueIsNotNull(String key, String value)
    {
        if (isNotEmpty(value))
            itsParameterMap.put(key, value);
    }

    private void putBetweenDoubleQuotesIfValueIsNotNull(String key, String value)
    {
        final String DOUBLE_QUOTES = "\"";
        if (isNotEmpty(value))
            itsParameterMap.put(key, DOUBLE_QUOTES + value + DOUBLE_QUOTES);
    }
    
    private boolean isValidTimeSlot(String timeSlot)
    {
        return StringUtils.contains(timeSlot, TIME_SLOT_SEPARATOR);
    }
}
