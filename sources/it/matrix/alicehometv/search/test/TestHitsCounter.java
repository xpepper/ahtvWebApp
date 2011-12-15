package it.matrix.alicehometv.search.test;

import static junit.framework.Assert.*;
import it.matrix.alicehometv.search.HitsCounter;

import org.junit.Test;

public class TestHitsCounter
{
    @Test
    public void extractTotalHits() throws Exception
    {
        String response = simpleFastResponseWithTotalHits(123);
        
        int hits = HitsCounter.on(response);
        assertEquals(123, hits);
    }

    @Test public void shouldSumTotalHits() throws Exception
    {
        String response = 
            "<?xml version=\"1.0\" encoding=\"any\"?>" + 
            "<RESULTPAGE>" + 
            "   <QUERY QS=\"walking\" SUGGESTION=\"\" SOURCE=\"\" HITS=\"1\" OFFSET=\"0\" CLIENT=\"web\" ENCODING=\"any\"/>" + 
            "   <RESULTSET FIRSTHIT=\"1\" LASTHIT=\"1\" HITS=\"1\" TOTALHITS=\"" + 5 + "\" TIME=\"0.1068\">" + 
            "       <HIT NO=\"1\">" + 
            "       </HIT>" + 
            "   </RESULTSET>" +
            "   <RESULTSET FIRSTHIT=\"1\" LASTHIT=\"1\" HITS=\"1\" TOTALHITS=\"" + 11 + "\" TIME=\"0.1068\">" + 
            "       <HIT NO=\"1\">" + 
            "       </HIT>" + 
            "   </RESULTSET>" + 

            "</RESULTPAGE>";
        
        int hits = HitsCounter.on(response);
        assertEquals(5 + 11, hits);
    }

    
    @Test
    public void withEmptyResult() throws Exception
    {
        String emptyResult = 
            "<?xml version=\"1.0\" encoding=\"any\"?>" + 
            "<RESULTPAGE>" +
        	"  <QUERY QS=\"qwerty\" SUGGESTION=\"\" SOURCE=\"\" HITS=\"10\" OFFSET=\"0\" CLIENT=\"web\" ENCODING=\"any\"/>" +
        	"  <EMPTYRESULTSET TOTALHITS=\"0\"/>" +
        	"</RESULTPAGE>";
        
        
        assertEquals(0, HitsCounter.on(emptyResult));
    }    
    
    private String simpleFastResponseWithTotalHits(final int totalHits)
    {
        return 
        "<?xml version=\"1.0\" encoding=\"any\"?>" + 
        "<RESULTPAGE>" + 
        "   <QUERY QS=\"walking\" SUGGESTION=\"\" SOURCE=\"\" HITS=\"1\" OFFSET=\"0\" CLIENT=\"web\" ENCODING=\"any\"/>" + 
        "   <RESULTSET FIRSTHIT=\"1\" LASTHIT=\"1\" HITS=\"1\" TOTALHITS=\"" + totalHits + "\" TIME=\"0.1068\">" + 
        "       <HIT NO=\"1\">" + 
        "           <assetid>50012304</assetid>" + 
        "           <title>Walking Tall 2 - La rivincita</title>        " + 
        "           <PictureFile>http://images.iptv.alice.it/images/cms/locandina.jpg</PictureFile>" + 
        "           <CategoryOmp>Poltronissima-Azione</CategoryOmp>" + 
        "           <rating>Verde</rating>" + 
        "           <price>2.99</price>" + 
        "           <duration>103</duration>" + 
        "           <url>/VOD/scheda.html?assetid=50012304</url>" + 
        "       </HIT>" + 
        "   </RESULTSET>" + 
        "</RESULTPAGE>";
    }
}
