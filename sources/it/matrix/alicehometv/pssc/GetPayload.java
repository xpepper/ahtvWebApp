package it.matrix.alicehometv.pssc;

import it.matrix.alicehometv.logger.ActivityLogger;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class GetPayload {
    
    /** Creates a new instance of Payload */
    public GetPayload() {
    }
 
    protected Document m_document;
    private static final int MAX_LEN = 12;
    //attributi
    private static final String CLI = "cli";
    private static final String MONTH = "month";
        
    private void initDocument() throws ParserConfigurationException {
        ActivityLogger.info("enter initDocument");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        m_document = builder.newDocument();        
    }
    
    public String serialize() throws Exception {
        ActivityLogger.info("enter serialize");
//        DOMSerializerImpl serial = new DOMSerializerImpl();  
//        String result = serial.writeToString(m_document);
          Transformer xformer = TransformerFactory.newInstance().newTransformer();
          xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
          Source source = new DOMSource(m_document);
          StringWriter writer = new StringWriter();
          Result result = new StreamResult(writer);
          xformer.transform(source, result);
          writer.close();
          return writer.toString();


      //  return result;
    }
    
    private String validaValueCli(String cli) throws IllegalArgumentException{
        ActivityLogger.info("enter validaValueCli");
        if (cli.length()>MAX_LEN){
            throw new IllegalArgumentException("Il tag GUID del XML contiene un valore non valido");
        }else{
            while (cli.length()<MAX_LEN){
                cli = "0".concat(cli);
            }
        }
        return cli;
    }
    
  
    
    public void getXmlToIPTV(String action, String valueCli, String valueMonth)throws ParserConfigurationException{
        //calcolo del Payload
        ActivityLogger.info("enter getPayload");
       
        initDocument();
        Element elmMessage = m_document.createElement("UabGetIPTVDataRequest");
        m_document.appendChild(elmMessage);
        //tag Action
        Element elmAction = m_document.createElement("Action");
        elmMessage.appendChild(elmAction);
        Text txtAction = m_document.createTextNode(action);
        elmAction.appendChild(txtAction);
        //tag payload
        Element elmPayload = m_document.createElement("Payload");
        elmMessage.appendChild(elmPayload);
        //add Attribute
        Element elmAttribute = m_document.createElement("attribute");
        elmPayload.appendChild(elmAttribute);
        //attributo name di Attribute
        Attr attrMessageType = m_document.createAttribute("name");
        attrMessageType.setValue(CLI);
        elmAttribute.setAttributeNode(attrMessageType);
        //attributo value di Attribute
        attrMessageType = m_document.createAttribute("value");
        attrMessageType.setValue(validaValueCli(valueCli));
        elmAttribute.setAttributeNode(attrMessageType);     
        
        if (valueMonth!=null){
            elmAttribute = m_document.createElement("attribute");
            elmPayload.appendChild(elmAttribute);
            //attributo name di Attribute
            attrMessageType = m_document.createAttribute("name");
            attrMessageType.setValue(MONTH);
            elmAttribute.setAttributeNode(attrMessageType);
            //attributo value di Attribute
            attrMessageType = m_document.createAttribute("value");
            attrMessageType.setValue(valueMonth);
            elmAttribute.setAttributeNode(attrMessageType); 
        }    
        
        //tag Timestamp
        GetTimestamp ts = new GetTimestamp();
        Element elmTimestamp = m_document.createElement("TimeStamp");
        elmMessage.appendChild(elmTimestamp);
        Text txtTimestamp = m_document.createTextNode(ts.getTimeStamp());
        elmTimestamp.appendChild(txtTimestamp);
    }    
}
