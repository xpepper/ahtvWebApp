package it.matrix.alicehometv.pssc;

import it.matrix.alicehometv.profile.iptv.IPTVProfile;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSaxResultPersonalData extends DefaultHandler{
    
    private static final String TAG_PC_LEVEL = "pcLevel";
    private static final String TAG_PC_PIN = "pcPin";
    private static final String TAG_PURCHASE_PIN = "purchasePin";
    private static final String TAG_RESULT = "result";
    
    private IPTVProfile iptvProfile = new IPTVProfile();
    
    private String nomeTag;
    private boolean valoreTag;
    
    private String result;
    
    /** Creates a new instance of ParserSaxEmailSms */
    public ParserSaxResultPersonalData() {        
    }
    
    
    public void startDocument(){
       // ActivityLogger.info("startDocument");      
    }

    public void endDocument(){
         //ActivityLogger.info("endDocument");
    }

    public void startElement(String namespaceURI,
                             String lName, // local name
                             String qName, // qualified name
                             Attributes attrs){
        
        //logger.info("enter startElement");
        setValoreTag(true);
        String eName = lName; // element name
        if ("".equals(eName)){
            eName = qName; // namespaceAware = false
        }
        setNomeTag(eName);      
        
    }

    public void endElement(String namespaceURI,
                           String sName, // simple name
                           String qName  // qualified name
                          ){
        setValoreTag(false);           
    }
    
    public void characters(char[] ch, int start, int length) { 
        if (isValoreTag()){
            String data = new String(ch, start, length); 
            if (TAG_PC_LEVEL.equalsIgnoreCase(getNomeTag())){
               getIptvProfile().setPcLevel(Integer.parseInt(data));
            } else if (TAG_PC_PIN.equalsIgnoreCase(getNomeTag())){
                getIptvProfile().setPcPin(data);
            } else if (TAG_PURCHASE_PIN.equalsIgnoreCase(getNomeTag())){
                getIptvProfile().setPurchasePin(data);
            }  else if (TAG_RESULT.equalsIgnoreCase(getNomeTag())){
                setResult(data);
            }      
        }    
   }    

    public boolean isValoreTag() {
        return valoreTag;
    }

    public void setValoreTag(boolean valoreTag) {
        this.valoreTag = valoreTag;
    }

    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }   

    public IPTVProfile getIptvProfile() {
        return iptvProfile;
    }

    public void setIptvProfile(IPTVProfile iptvProfile) {
        this.iptvProfile = iptvProfile;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
