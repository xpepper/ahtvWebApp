package it.matrix.alicehometv.util.test;

import static junit.framework.Assert.*;
import it.matrix.alicehometv.util.RemoveXMLDeclaration;

import org.junit.Test;

public class TestRemoveXMLDeclaration
{
    @Test
    public void withEmptyResult() throws Exception
    {
        String simpleXml = 
            "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + 
            "<RESULTPAGE>" +
        	"  <QUERY QS=\"qwerty\" SUGGESTION=\"\" SOURCE=\"\" HITS=\"10\" OFFSET=\"0\" CLIENT=\"web\" ENCODING=\"ISO-8859-1\"/>" +
        	"  <EMPTYRESULTSET TOTALHITS=\"0\"/>" +
        	"</RESULTPAGE>";
        
        assertTrue(simpleXml.startsWith("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"));
        
        String resultingXml = RemoveXMLDeclaration.on(simpleXml);
        
        assertFalse(resultingXml.startsWith("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"));
        assertTrue(resultingXml.startsWith("<RESULTPAGE>"));
    }    
    
}
