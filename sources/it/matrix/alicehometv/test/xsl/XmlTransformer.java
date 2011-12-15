package it.matrix.alicehometv.test.xsl;

import java.io.StringWriter;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.input.SAXBuilder;
import org.jdom.transform.JDOMSource;

public class XmlTransformer
{
    private final String itsXSLFilename;

    public XmlTransformer(String anXSLFilename)
    {
        itsXSLFilename = anXSLFilename;
    }

    public String runOn(String anXMLFilePath) throws Exception
    {
        Source sourceXml = new JDOMSource(new SAXBuilder().build(anXMLFilePath));

        String xslFilePath = Thread.currentThread().getContextClassLoader().getResource(itsXSLFilename).getPath();

        Templates xmlToJsonXsl = TransformerFactory.newInstance().newTemplates(new StreamSource(xslFilePath));

        StringWriter stringWriter = new StringWriter();
        xmlToJsonXsl.newTransformer().transform(sourceXml, new StreamResult(stringWriter));

        return stringWriter.toString();
    }
}
