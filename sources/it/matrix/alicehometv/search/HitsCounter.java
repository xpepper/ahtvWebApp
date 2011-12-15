package it.matrix.alicehometv.search;

import it.matrix.alicehometv.logger.ActivityLogger;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class HitsCounter
{
    public static int on(String response)
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        Integer totalHits = 0;
        try
        {
            Document document = documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(response)));

            XPathExpression expression = xpath.compile("//@TOTALHITS");
            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++)
                totalHits += Integer.valueOf(nodeList.item(i).getNodeValue());
        }
        catch (Exception e)
        {
            ActivityLogger.warning("Cannot compute total hits: " + e.getMessage());
        }
        
        return totalHits;
    }
}
