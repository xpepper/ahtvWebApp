package it.matrix.alicehometv.logger.test;

import static it.matrix.alicehometv.logger.TraceLogger.*;
import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.apache.commons.lang.StringUtils.*;
import static org.fest.assertions.Assertions.*;
import it.matrix.alicehometv.logger.TraceLogger;
import it.matrix.alicehometv.search.SearchRequest;
import it.matrix.alicehometv.search.SearchRequestAnalyzer;
import it.matrix.alicehometv.servlet.test.AhtvCookieCreator;
import it.matrix.alicehometv.util.CookieValueDetector;
import it.matrix.alicehometv.util.test.AssertUtils;
import it.matrix.alicehometv.util.test.ListUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.springframework.mock.web.MockHttpServletRequest;

@SuppressWarnings("unchecked")
public class TestTraceLogger
{
    private static final Cookie[] NO_COOKIES = new Cookie[0];
    
    private File biLogFile;
    private MockHttpServletRequest fakeRequest;

    @Before
    public void setUp() 
    {
        biLogFile = new File(System.getProperty("user.home") + File.separator + "bi.log");
        biLogFile.delete();

        fakeRequest = new MockHttpServletRequest();
    }
    
    @After
    public void tearDown() throws IOException
    {
        biLogFile.delete();
    }
    
    @Test
    public void kpCookieValueShouldBeLoggedIntoFile() throws Exception
    {
        CookieValueDetector cookieValueDetector = new CookieValueDetector(NO_COOKIES);
        
        TraceLogger.traceUsing(cookieValueDetector, new SearchRequestAnalyzer(Collections.EMPTY_MAP), "any search response");
        assertThat(lastLoggedLine()).contains("|" + EMPTY + "|SIMPLE|");
        
        cookieValueDetector = new CookieValueDetector(new Cookie[] {new Cookie(KP_COOKIE_NAME, "a cookie value")});
        TraceLogger.traceUsing(cookieValueDetector, new SearchRequestAnalyzer(Collections.EMPTY_MAP), "any search response");

        assertThat(lastLoggedLine()).contains("|" + KP_COOKIE_NAME + "=" + "a cookie value|SIMPLE|");
    }
    
    @Test
    public void sunrisePassportCodeShouldBeLoggedIntoFile() throws Exception
    {
        CookieValueDetector cookieValueDetector = new CookieValueDetector(NO_COOKIES);   
        TraceLogger.traceUsing(cookieValueDetector, new SearchRequestAnalyzer(Collections.EMPTY_MAP), "any search response");
        AssertUtils.assertNotContains(lastLoggedLine(), "|aSunrisePassportCode|");
        
        cookieValueDetector = new CookieValueDetector(new Cookie[] { AhtvCookieCreator.withSunrisePassportCode("aSunrisePassportCode") });
        TraceLogger.traceUsing(cookieValueDetector, new SearchRequestAnalyzer(Collections.EMPTY_MAP), "any search response");

        assertThat(lastLoggedLine()).contains("|aSunrisePassportCode|");
    }

    @Test
    public void whenQsParameterIsPresentItShouldBeLoggedIntoFile() throws Exception
    {
        SearchRequestAnalyzer searchRequestAnalyzer = new SearchRequestAnalyzer(Collections.EMPTY_MAP);
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), searchRequestAnalyzer, "any search response");

        assertThat(lastLoggedLine()).contains("|SIMPLE|||").as("qs parameter is not present");
        
        fakeRequest.addParameter(SearchRequest.QUERY_STRING, "some words I like to search about");
        searchRequestAnalyzer = new SearchRequestAnalyzer(fakeRequest.getParameterMap());
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), searchRequestAnalyzer, "any search response");
        
        assertThat(lastLoggedLine()).contains("|SIMPLE|some words I like to search about||").as("qs parameter is present");;
    }

    @Test
    public void shouldNotTraceLogWhenIsSearchingFromAlertingSection() throws Exception
    {
        int initialSize = logSize();
        
        SearchRequestAnalyzer searchRequestAnalyzer = new SearchRequestAnalyzer(Collections.EMPTY_MAP);
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), searchRequestAnalyzer, "any search response");
        
        assertThat(logSize()).isGreaterThan(initialSize).as("Size should grown, by default");
        
        int sizeBeforeLogRequest = logSize();
        fakeRequest.addParameter("isSearchingFromAlertingSection", "yes");
        
        searchRequestAnalyzer = new SearchRequestAnalyzer(fakeRequest.getParameterMap());
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), searchRequestAnalyzer, "any search response");
        
        assertThat(logSize()).isEqualTo(sizeBeforeLogRequest).as("When Alerting Search is detected, no log should be produced");
    }
    
    @Test
    public void shouldOnlyTraceLogTheFirstPageSearch() throws Exception
    {
        int initialSize = logSize();
        
        prepareRequestToSearchInTheFirstPage(fakeRequest);
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), new SearchRequestAnalyzer(fakeRequest.getParameterMap()), "any search response");
        
        assertThat(logSize()).isGreaterThan(initialSize).as("Should log the first page of the search");
        
        int sizeBeforeLogRequest = logSize();
        prepareRequestToSearchInTheSecondPage(fakeRequest);
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), new SearchRequestAnalyzer(fakeRequest.getParameterMap()), "any search response");
        
        assertThat(logSize()).isEqualTo(sizeBeforeLogRequest).as("When searching in pages after the first, no log should be produced");
    }
    
    @Test
    public void advancedSearchFiltersShouldBeLoggedIntoFile() throws Exception
    {
        fakeRequest.addParameter("AdvancedSearch", "any");
        fakeRequest.addParameter(SearchRequest.DAY, "any");
        fakeRequest.addParameter(SearchRequest.FILMNEWS, "any");
        
        SearchRequestAnalyzer requestAnalyzer = new SearchRequestAnalyzer(fakeRequest.getParameterMap());
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), requestAnalyzer, "any search response");

        assertThat(lastLoggedLine()).contains("|" + SearchRequest.DAY + "+" + SearchRequest.FILMNEWS + "|");
    }
    
    @Test
    public void searchTypeShouldBeLoggedIntoFile() throws Exception
    {
        fakeRequest.addParameter("AdvancedSearch", "true");

        SearchRequestAnalyzer requestAnalyzer = new SearchRequestAnalyzer(fakeRequest.getParameterMap());
        TraceLogger.traceUsing(new CookieValueDetector(NO_COOKIES), requestAnalyzer, "any response");

        assertThat(lastLoggedLine()).contains("|ADVANCED|");
        assertNotContains(lastLoggedLine(), "|SIMPLE|");
    }

    private String lastLoggedLine() throws IOException
    {
        List<String> logContentAsList = FileUtils.readLines(biLogFile);
        return ListUtils.lastElementOf(logContentAsList);
    }

    private int logSize() throws IOException
    {
        return FileUtils.readLines(biLogFile).size();
    }

    private void prepareRequestToSearchInTheSecondPage(MockHttpServletRequest aFakeRequest)
    {
        aFakeRequest.removeParameter(SearchRequest.OFFSET);
        aFakeRequest.addParameter(SearchRequest.OFFSET, "10");
    }

    private void prepareRequestToSearchInTheFirstPage(MockHttpServletRequest aFakeRequest)
    {
        aFakeRequest.removeParameter(SearchRequest.OFFSET);
        aFakeRequest.addParameter(SearchRequest.OFFSET, "0");
    }    
}
