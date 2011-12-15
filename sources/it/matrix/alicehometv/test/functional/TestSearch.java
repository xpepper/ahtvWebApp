package it.matrix.alicehometv.test.functional;

import it.matrix.alicehometv.search.SearchRequest;

import java.io.BufferedReader;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.mockrunner.servlet.BasicServletTestCaseAdapter;

public class TestSearch extends BasicServletTestCaseAdapter
{
    protected void setUp() throws Exception
    {
        super.setUp();

        createServlet(SearchServletForTest.class);
    }
    
    public void testReturnedXMLDeclaration() throws Exception
    {
        doGet();

        BufferedReader reader = getOutputAsBufferedReader();
        assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>", reader.readLine().trim());
        assertEquals("<COMPOSITE-RESULTPAGE>", reader.readLine().trim());
    }
    
    public void testPARAMShouldEchoesAllSubmittedParameters() throws Exception
    {
        addRequestParameter(SearchRequest.QUERY_STRING, "aStringToSearch");
        addRequestParameter(SearchRequest.OFFSET, "5");
        addRequestParameter(SearchRequest.HITS_PER_PAGE, "30");
        addRequestParameter(SearchRequest.PARENTAL_CONTROL_RATING, "Verde");
        addRequestParameter(SearchRequest.GENRE, "Azione");
        addRequestParameter(SearchRequest.THEMATIC_AREA, "Intrattenimento");
        addRequestParameter(SearchRequest.CATEGORY, "SerieTV");
        addRequestParameter(SearchRequest.DAY, "2007-09-18");
        addRequestParameter(SearchRequest.TITLE, "Rambo III");
        addRequestParameter(SearchRequest.PEOPLE, "Hugh Jackman");
        addRequestParameter(SearchRequest.FILMNEWS, "N");
        addRequestParameter(SearchRequest.DURATION, "93");
        addRequestParameter(SearchRequest.FIRST_LETTER, "R");        
        
        doGet();
        
        checkPARAMContainsSources("epg", "vod", "edit");
        checkPARAMContainsAttributes("aStringToSearch", "5", "30", "Verde", "Azione", "Intrattenimento", "SerieTV", "2007-09-18", "Rambo III", "Hugh Jackman", "N", "93", "R");
    }

    private void checkPARAMContainsAttributes(String queryString, String offset, String rows, String rating, String genre, String thematicArea, String category, String day, String title, String people, String filmnews, String duration, String firstLetter)
    {
        NodeList paramElements = getOutputAsW3CDocument().getElementsByTagName("PARAM");
        for (int i = 0; i < paramElements.getLength(); i++)
        {
            NamedNodeMap attributes = paramElements.item(i).getAttributes();
            assertEquals("wrong PARAM attributes number", 16, attributes.getLength());
            
            assertEquals("listing", attributes.getNamedItem("type").getTextContent());
            assertEquals("ISO-8859-1", attributes.getNamedItem("encoding").getTextContent());
            
            assertEquals(queryString, attributes.getNamedItem("qs").getTextContent());
            assertEquals(offset, attributes.getNamedItem("offset").getTextContent());
            assertEquals(rows, attributes.getNamedItem("hits").getTextContent());
            assertEquals(rating, attributes.getNamedItem("livellopc").getTextContent());
            assertEquals(genre, attributes.getNamedItem("genere").getTextContent());
            assertEquals(thematicArea, attributes.getNamedItem("area").getTextContent());
            assertEquals(category, attributes.getNamedItem("categoria").getTextContent());
            assertEquals(day, attributes.getNamedItem("startdate").getTextContent());
            assertEquals(title, attributes.getNamedItem("titolo").getTextContent());
            assertEquals(people, attributes.getNamedItem("persone").getTextContent());
            assertEquals(filmnews, attributes.getNamedItem("filmnews").getTextContent());
            assertEquals(duration, attributes.getNamedItem("durata").getTextContent());
            assertEquals(firstLetter, attributes.getNamedItem("iniziale").getTextContent());
        }
    }

    
    public void testWithQueryStringWithNotASCIIChars()
    {
        addRequestParameter(SearchRequest.SOURCE, SearchRequest.EPG_SOURCE);
        addRequestParameter(SearchRequest.QUERY_STRING, "Perché");

        doGet();
        
        NodeList paramElements = getOutputAsW3CDocument().getElementsByTagName("PARAM");
        assertEquals("Perché", paramElements.item(0).getAttributes().getNamedItem("qs").getTextContent());
    }

    public void testXmlEncodingShouldBeISO88591() throws Exception
    {
        addRequestParameter(SearchRequest.SOURCE, SearchRequest.EPG_SOURCE);

        doGet();

        assertEquals("ISO-8859-1", getOutputAsW3CDocument().getXmlEncoding());
    }

    private void checkPARAMContainsSources(String epgSource, String vodSource, String cmsSource)
    {
        NodeList paramElements = getOutputAsW3CDocument().getElementsByTagName("PARAM");
        assertEquals("There should be one PARAM element for each source (epg, vod, edit)", 3, paramElements.getLength());
        assertEquals(epgSource, paramElements.item(0).getAttributes().getNamedItem("source").getTextContent());
        assertEquals(vodSource, paramElements.item(1).getAttributes().getNamedItem("source").getTextContent());
        assertEquals(cmsSource, paramElements.item(2).getAttributes().getNamedItem("source").getTextContent());
    }
}

