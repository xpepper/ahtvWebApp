package it.matrix.alicehometv.logger;

import static it.matrix.alicehometv.util.StringUtilies.*;
import static org.apache.commons.lang.StringUtils.*;
import it.matrix.alicehometv.search.HitsCounter;
import it.matrix.alicehometv.search.SearchRequestAnalyzer;
import it.matrix.alicehometv.util.CookieValueDetector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TraceLogger
{
    public static final String KP_COOKIE_NAME = "kahtv";
    private static final String SEPARATOR = "|";
    
    private static final String LOGGER_NAME = "bi";

    private static Logger itsLogger;
    
    private static Logger bi()
    {
        if (itsLogger == null)
        {
            if (LogManager.exists(LOGGER_NAME) != null)
                itsLogger = Logger.getLogger(LOGGER_NAME);
            else
                ActivityLogger.error("logger 'bi' for the business intelligence was not found");
        }

        return itsLogger;
    }    
    
    public static void traceUsing(CookieValueDetector cookieValueDetector, SearchRequestAnalyzer searchRequestAnalyzer, String searchResponse)
    {
        if (searchRequestAnalyzer.isSearchingFromAlertingSection() || searchRequestAnalyzer.isANextPageOfTheSearch())
            return;
        
        String sunrisePassportCode = cookieValueDetector.detectAhtvCookie().sunrisePassportCode();
        String kpCookieValue = cookieValueDetector.valueOf(KP_COOKIE_NAME);
        
        AdvancedSearchFiltersFormatter filtersFormatter = new AdvancedSearchFiltersFormatter(searchRequestAnalyzer);
        int totalHits = HitsCounter.on(searchResponse);
        
        bi().trace(
                nullSafeString(sunrisePassportCode) + SEPARATOR + 
                formatKpCookie(kpCookieValue) + SEPARATOR +
                filtersFormatter.searchType() + SEPARATOR + 
                nullSafeString(searchRequestAnalyzer.queryString()) + SEPARATOR + 
                filtersFormatter.allFilters() + SEPARATOR + 
                totalHits);
    }

    private static String formatKpCookie(String kpCookieValue)
    {
        return isNotEmpty(kpCookieValue)? KP_COOKIE_NAME + "=" + nullSafeString(kpCookieValue) : EMPTY;
    }

}
