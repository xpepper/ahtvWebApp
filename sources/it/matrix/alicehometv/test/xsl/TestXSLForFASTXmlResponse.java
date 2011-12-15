package it.matrix.alicehometv.test.xsl;

import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Test;

public class TestXSLForFASTXmlResponse extends XMLTestCase
{
    @Test
    public void testTrasformationFromFASTResponseToComposedXML() throws Exception
    {
        String transformationResult = new XmlTransformer("search.xsl").runOn("./testData/FASTSearchResponse.xml");

        assertXpathExists("/RESULTS/EPG[@TOTAL='123']", transformationResult);
        assertXpathExists("/RESULTS/VOD[@TOTAL='456']", transformationResult);
        assertXpathExists("/RESULTS/EDIT[@TOTAL='789']", transformationResult);
        
        assertXpathEvaluatesTo("Scrivimi una canzone", "/RESULTS/VOD/HIT/TITLE", transformationResult);
        assertXpathEvaluatesTo("50078428", "/RESULTS/VOD/HIT/ASSETID", transformationResult);
        assertXpathEvaluatesTo("Verde", "/RESULTS/VOD/HIT/RATING", transformationResult);
        assertXpathEvaluatesTo("2007-10-19T22:00:00Z", "/RESULTS/VOD/HIT/STARTDATE", transformationResult);
        assertXpathEvaluatesTo("100", "/RESULTS/VOD/HIT/DURATION", transformationResult);
        assertXpathEvaluatesTo("Brad Garrett|Drew Barrymore|Hugh  Grant|Kristen Johnston", "/RESULTS/VOD/HIT/ACTOR", transformationResult);
        assertXpathEvaluatesTo("Marc Lawrence", "/RESULTS/VOD/HIT/DIRECTOR", transformationResult);
        assertXpathEvaluatesTo("/VOD/scheda_film.html?assetId=50078428", "/RESULTS/VOD/HIT/URL", transformationResult);
    }
}
