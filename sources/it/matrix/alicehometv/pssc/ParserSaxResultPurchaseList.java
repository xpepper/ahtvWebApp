package it.matrix.alicehometv.pssc;

import it.matrix.alicehometv.logger.ActivityLogger;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSaxResultPurchaseList extends DefaultHandler{
    
    private static final String TAG_RESULT = "result";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_NAME = "name";
    private static final String TAG_START_DATE = "startDate";
    private static final String TAG_END_DATE = "endDate";
    private static final String TAG_TRANSACTION_CODE = "transactionCode";
    private static final String TAG_PRICE = "price";
    private static final String TAG_RATING = "rating";
    private static final String TAG_STATE = "state";
    private static final String TAG_PAYMENT_TYPE = "paymentType";
    
    private String result;
    private String description;
    
    private InfoContent infoContent;
    private ArrayList purchaseList = new ArrayList();
    
    private String nomeTag;
    private boolean valoreTag;
    
    /** Creates a new instance of ParserSaxEmailSms */
    public ParserSaxResultPurchaseList() {        
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
        if (TAG_CONTENT.equalsIgnoreCase(eName)){
            infoContent = new InfoContent();  
        }
    }

    public void endElement(String namespaceURI,
                           String sName, // simple name
                           String qName  // qualified name
                          ){
        setValoreTag(false);   
        if (TAG_CONTENT.equalsIgnoreCase(qName)){
            purchaseList.add(infoContent);                     
        }       
    }
    
    public void characters(char[] ch, int start, int length) { 
        if (isValoreTag()){
            String data = new String(ch, start, length); 
            if (TAG_RESULT.equalsIgnoreCase(getNomeTag())){
               setResult(data);
            } else if (TAG_DESCRIPTION.equalsIgnoreCase(getNomeTag())){
                setDescription(data);
            }  else if (TAG_NAME.equalsIgnoreCase(getNomeTag())){
                infoContent.setNome(data);
            } else if (TAG_START_DATE.equalsIgnoreCase(getNomeTag())){
                infoContent.setDateFrom(data);
            } else if (TAG_END_DATE.equalsIgnoreCase(getNomeTag())){
                infoContent.setDateTo(data);
            } else if (TAG_TRANSACTION_CODE.equalsIgnoreCase(getNomeTag())){
               infoContent.setTrasCode(data);
            } else if (TAG_PRICE.equalsIgnoreCase(getNomeTag())){
                infoContent.setPrice(data);
            } else if (TAG_RATING.equalsIgnoreCase(getNomeTag())){
                infoContent.setRating(data);
            } else if (TAG_STATE.equalsIgnoreCase(getNomeTag())){
                infoContent.setState(data);
            } else if (TAG_PAYMENT_TYPE.equalsIgnoreCase(getNomeTag())){
                infoContent.setPay(data);
            }
            //ActivityLogger.debug("data==="+data);
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

    public ArrayList getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(ArrayList purchaseList) {
        this.purchaseList = purchaseList;
    }
            
     public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }        
}
