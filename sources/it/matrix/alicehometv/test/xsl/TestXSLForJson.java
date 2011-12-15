package it.matrix.alicehometv.test.xsl;

import static org.fest.assertions.Assertions.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

public class TestXSLForJson
{
    private XmlTransformer xmlToJsonTransformer;

    @Before
    public void setUp()
    {
        xmlToJsonTransformer = new XmlTransformer("search-json.xsl");
    }
    
    @Test
    public void testJsonContainsTotalHitsReports() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultWithTotalHitsOnly.xml");
        
        JSONObject jsonObject = JSONObject.fromObject(resultAsJson);
        
        assertThat(jsonObject.get("resultsEPG")).isEqualTo("8063");
        assertThat(jsonObject.get("resultsVOD")).isEqualTo("187");
        assertThat(jsonObject.get("resultsEDIT")).isEqualTo("35");
    }

    @Test
    public void testJsonHitsComposition() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultSampleWith3Hits.xml");
        
        JSONArray results = JSONObject.fromObject(resultAsJson).getJSONArray("Hits");

        assertThat(results).as("There should be 3 hits in the result").hasSize(3);
        
        JSONObject epgHit = JSONObject.fromObject(results.get(0));
        assertThat(epgHit.get("type")).isEqualTo("EPG");
        assertThat(epgHit.get("title")).isEqualTo("any EPG title");
        assertThat(epgHit.get("assetid")).isEqualTo("50939997");
        assertThat(epgHit.get("category")).isEqualTo("Intrattenimento");
        assertThat(epgHit.get("startdate")).isEqualTo("2007-11-01 01:50");
        assertThat(epgHit.get("logo")).isEqualTo("/img/canali/italia_1.png");
        assertThat(epgHit.get("channel_name")).isEqualTo("Italia 1");
        assertThat(epgHit.get("channel_number")).isEqualTo("106");
        assertThat(epgHit.get("rating")).isEqualTo("Verde");
        assertThat(epgHit.get("duration")).isEqualTo("60");
        assertThat(epgHit.get("description")).isEqualTo("Quiz telefonico da Piazza Italia, la piazza piu' bella d'italia");
        assertThat(epgHit.get("link")).isEqualTo("/GuidaTV/index.html?date=2007-11-01&time=0150&tab=0&assetId=50535042&channelid=106");
        
        JSONObject vodHit = JSONObject.fromObject(results.get(1));
        assertThat(vodHit.get("type")).isEqualTo("VOD");
        assertThat(vodHit.get("assetid")).isEqualTo("12345");
        assertThat(vodHit.get("title")).isEqualTo("any VOD title");
        assertThat(vodHit.get("director")).isEqualTo("Peyton Reed");
        assertThat(vodHit.get("actor")).isEqualTo("Adam Brody");
        assertThat(vodHit.get("rating")).isEqualTo("Giallo");
        assertThat(vodHit.get("duration")).isEqualTo("120");
        assertThat(vodHit.get("link")).isEqualTo("/VOD/scheda_serie_tv.html?assetId=12345");
        
        JSONObject cmsHit = JSONObject.fromObject(results.get(2));
        assertThat(cmsHit.get("type")).isEqualTo("EDIT");
        assertThat(cmsHit.get("title")).isEqualTo("any CMS title");
        assertThat(cmsHit.get("abstract")).isEqualTo("Mettiti comodo! Un tecnico verr&agrave; a casa tua e provveder&agrave; ad installare il decoder!");
        assertThat(cmsHit.get("link")).isEqualTo("/Installazione/index.html");
    }

    @Test
    public void transformationShouldEscapeDoubleQuotes() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultWithDoubleQuotes.xml");
        
        JSONArray results = JSONObject.fromObject(resultAsJson).getJSONArray("Hits");

        JSONObject epgHit = JSONObject.fromObject(results.get(0));
        assertThat(epgHit.get("type")).isEqualTo("EPG");
        assertThat(epgHit.get("title")).isEqualTo("epg title with \"double quotes\"");
        assertThat(epgHit.get("description")).isEqualTo("Quiz telefonico da \"Piazza Italia\", la piazza piu' bella d'italia");
        
        JSONObject vodHit = JSONObject.fromObject(results.get(1));
        assertThat(vodHit.get("type")).isEqualTo("VOD");
        assertThat(vodHit.get("title")).isEqualTo("title with \"double quotes\"");
        assertThat(vodHit.get("director")).isEqualTo("Peyton \"The Boss\" Reed");
        assertThat(vodHit.get("actor")).isEqualTo("Adam \"Mashing\" Brody");
    }
    
    @Test
    public void transformationShouldNormalizeAllSpacesInCmsAbstract() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultWithUnNormalizedSpacesInCms.xml");
        
        JSONArray results = JSONObject.fromObject(resultAsJson).getJSONArray("Hits");

        JSONObject vodHit = JSONObject.fromObject(results.get(0));
        assertThat(vodHit.get("type")).isEqualTo("EDIT");
        assertThat(vodHit.get("abstract")).isEqualTo("Mettiti comodo! Un tecnico verra' a casa tua e provvedera' ad installare il decoder!");
    }
    
    @Test
    public void transformationShouldNormalizeAllSpacesInTitle() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultWithUnNormalizedSpacesInTitle.xml");
        
        JSONArray results = JSONObject.fromObject(resultAsJson).getJSONArray("Hits");

        JSONObject vodHit = JSONObject.fromObject(results.get(0));
        assertThat(vodHit.get("type")).isEqualTo("EDIT");
        assertThat(vodHit.get("title")).isEqualTo("L' Eredità");
    }
    
    @Test
    public void transformationShouldNormalizeAllSpacesInEPGDescription() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultWithUnNormalizedSpacesInEPGDescription.xml");
        
        JSONArray results = JSONObject.fromObject(resultAsJson).getJSONArray("Hits");

        JSONObject vodHit = JSONObject.fromObject(results.get(0));
        assertThat(vodHit.get("type")).isEqualTo("EPG");
        assertThat(vodHit.get("description")).isEqualTo("Quiz telefonico da Piazza Italia");
    }
    
    @Test
    public void testJsonContainsSTAGIONEFieldForVod() throws Exception
    {
        String resultAsJson = xmlToJsonTransformer.runOn("./testData/searchResultWithFieldSTAGIONEforVOD.xml");
        
        JSONArray results = JSONObject.fromObject(resultAsJson).getJSONArray("Hits");
        JSONObject vodHit = JSONObject.fromObject(results.get(0));
        assertThat(vodHit.get("type")).isEqualTo("VOD");
        assertThat(vodHit.get("season")).isEqualTo("1");
    }
}
