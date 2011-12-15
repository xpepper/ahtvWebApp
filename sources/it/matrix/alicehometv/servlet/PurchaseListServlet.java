package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.pssc.*;
import it.matrix.alicehometv.util.AhtvCookie;
import it.matrix.alicehometv.util.CookieValueDetector;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PurchaseListServlet extends HttpServlet {

    private static final String AHTV_COOKIE_NAME = "ahtv";
    private static final String RESULT_SUCCESS = "3000";
    private static final String ACTION = "PurchaseList";
    
    private String rispostaServizioPurchaseList(String message){
        ActivityLogger.info("enter rispostaServizioPurchaseList");  
        OutputStream os = null;
        PrintWriter pw = null;
        HttpURLConnection conn = null;
        try{
            URL url = new URL(MyAhtvConfiguration.URL_PSSC_PURCHASE_LIST());
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-length", Integer.toString(message.length()));
            conn.setRequestProperty("Content-type", "text/xml");
            os = conn.getOutputStream();
            pw = new PrintWriter(os);
            pw.print(message);
            pw.flush();
            //conn.getResponseCode() == HttpURLConnection.HTTP_OK
            ActivityLogger.info("RISULTATO FINALE==="+conn.getResponseCode());
            
            InputStream bodyInputStream = conn.getInputStream();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(bodyInputStream));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = rdr.readLine()) != null) {
                sb.append(line);             
            }
            ActivityLogger.info("sb.toString()==="+sb.toString());
            return sb.toString();
        }catch(Exception ex){
            ActivityLogger.error("Exception rispostaServizioPurchaseList --> "+ex);
            return null;
        } finally{
            try{
                if (pw != null){
                    pw.close();
                }
            }catch(Exception ex){
                //nothing
            }
            
            try{
                if (os != null){
                    os.close();
                }
            }catch(Exception ex){
                //nothing
            }
            
            try{
                if (conn != null){
                    conn.disconnect();  
                }
            }catch(Exception ex){
                //nothing
            }
        }         
    }
    
    private ParserSaxResultPurchaseList parserXmlRitornoFromPSSC(String xmlString)throws SAXException, ParserConfigurationException, Exception{
        ParserSaxResultPurchaseList handler = new ParserSaxResultPurchaseList();
        Reader reader = new StringReader(xmlString);
        InputSource inputSource = new InputSource(reader);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(inputSource, handler);        
        return handler;
    }
            
  private int trascodificaValueMonth(int valueRangeMesi, String monthStart, String yearEnd, String monthEnd){
    ActivityLogger.info("enter trascodificaValueMonth");
    int resMese = 0;
    Calendar calendar = new GregorianCalendar();
    Date data = new Date();
    calendar.setTime(data);
    int annoCorrente = calendar.get(Calendar.YEAR);
    int meseCorrente = calendar.get(Calendar.MONTH)+1;

//    ActivityLogger.debug("annoCorrente==="+annoCorrente);
//    ActivityLogger.debug("meseCorrente==="+meseCorrente);
    
    int meseStart = Integer.parseInt(monthStart);
    int meseEnd = Integer.parseInt(monthEnd);
    int annoEnd = Integer.parseInt(yearEnd);
//    ActivityLogger.debug("meseStart==="+meseStart);
//    ActivityLogger.debug("meseEnd==="+meseEnd);
//    ActivityLogger.debug("annoEnd==="+annoEnd);
    
    int diffAnno = annoCorrente-annoEnd;
    int diffMese = meseCorrente - meseEnd;
//    ActivityLogger.debug("diffAnno==="+diffAnno);
//    ActivityLogger.debug("diffMese==="+diffMese);
//    
//    ActivityLogger.debug("valueRangeMesi==="+valueRangeMesi);
    
    if (meseStart!=meseEnd){
        //mese multipli (range di "n" mesi)
//        if (diffAnno == 0){
            diffMese =  diffMese + valueRangeMesi;
//        }  else if (diffAnno > 0){
//            diffMese =  diffMese + valueRangeMesi-1;
//       }  
//        ActivityLogger.debug("diffMese==="+diffMese);
   }     

  // ActivityLogger.debug("diffAnno==="+diffAnno);
   if (diffAnno == 0){
       resMese = diffMese;            
   }else if (diffAnno > 0){
       resMese = (12*diffAnno)+diffMese;
   }

   if (resMese < 0 || diffAnno < 0){
       resMese = -1;
   }else if (resMese > 99){
       resMese = 99;
   }

   ActivityLogger.debug("resMese ===" +resMese);
   return resMese;        
}
      
    private String sendXmlToPSSCPurchaseList(String cli, int contatore, String meseStart, String annoEnd, String meseEnd) throws ParserConfigurationException, Exception{
        GetPayload pLoad = new GetPayload();
        int resMese = trascodificaValueMonth(contatore, meseStart, annoEnd, meseEnd);
        if (resMese >= 0){
//            ActivityLogger.debug("IF==="+resMese);
            pLoad.getXmlToIPTV(ACTION, cli,String.valueOf(resMese)); 
            return pLoad.serialize();            
        } else {
//            ActivityLogger.debug("ELSE==="+resMese);
            return null;
        }    
    }
     
    protected String getPurchaseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ActivityLogger.info("enter getPurchaseList");        
        JSONObject json = new JSONObject();
        JSONObject tmpJson = new JSONObject();
        JSONArray jsonItems = new JSONArray();
        try{
            //recupero range di date per la visualizzazione degli acquisti (data inizio e data fine) 
            String meseStart = request.getParameter("meseStart");
            String meseEnd = request.getParameter("meseEnd");
            String annoEnd = request.getParameter("annoEnd");
            
             //recupero il cli presente nel cookie
            AhtvCookie ahtvCookie = new CookieValueDetector(request.getCookies()).detectAhtvCookie();
            request.setAttribute("cliUser", ahtvCookie.get("cli"));
            ActivityLogger.debug("TEST COOKIE==="+request.getAttribute("cliUser"));
            
            int rangeMesi = Integer.parseInt(meseEnd) - Integer.parseInt(meseStart);
            ParserSaxResultPurchaseList handler = null;
            for (int i=0; i<=rangeMesi; i++){
                String message = sendXmlToPSSCPurchaseList(request.getAttribute("cliUser").toString(), i, meseStart, annoEnd, meseEnd);
                if (message != null){
                    ActivityLogger.debug("MESSAGE -->"+message);
                    String xmlString = rispostaServizioPurchaseList(message);
                    handler = parserXmlRitornoFromPSSC(xmlString);
                    Collections.sort(handler.getPurchaseList());
                    ActivityLogger.debug("LUNGHEZZA==="+handler.getPurchaseList().size());
                    for (int k=0; k<handler.getPurchaseList().size(); k++){
                        InfoContent info = (InfoContent)handler.getPurchaseList().get(k);

                        tmpJson.put("Name",info.getNome());
                        tmpJson.put("dateFrom", info.getDateFrom());
                        tmpJson.put("dateTo", info.getDateTo());
                        tmpJson.put("TrasCode", info.getTrasCode());
                        tmpJson.put("Price", info.getPrice());
                        tmpJson.put("rating", info.getRating());
                        tmpJson.put("state", info.getState());
                        tmpJson.put("Pay", info.getPay());
                        jsonItems.add(tmpJson);          
                    }   
                }    
            }
            if (!(handler.getResult().equalsIgnoreCase(RESULT_SUCCESS))){
                throw  new Exception ("Exception: il risultato prodotto non è success ma il tag result contiene il seguente valore: " + handler.getResult());
            }
            json.put("ResultCode",handler.getResult());
            json.put("ResultMessage",handler.getDescription());
            json.put("Orders", jsonItems);
            ActivityLogger.debug("TEST=="+json.toString());      
        }catch(Exception ex){
            ActivityLogger.error("Exception ==="+ex);
            json.put("ResultCode",HttpURLConnection.HTTP_NOT_FOUND);            
        }       
        return json.toString();
     }
     
     protected void processRequest(HttpServletRequest request, HttpServletResponse response, String result) throws ServletException, IOException { 
     
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
/*        
        String meseStart = request.getParameter("meseStart");
        String annoStart = request.getParameter("annoStart");
        String meseEnd = request.getParameter("meseEnd");
        String annoEnd = request.getParameter("annoEnd");

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet PurchaseList</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet PurchaseList at " + request.getContextPath () + "</h1>");
        out.println("<p>Mese di riferimento: " + meseStart + "</p>");
        out.println("<p>Anno di riferimento: " + annoStart + "</p>");
        out.println("<p>Mese di riferimento: " + meseEnd + "</p>");
        out.println("<p>Anno di riferimento: " + annoEnd + "</p>");
        out.println("</body>");
        out.println("</html>");
*/
        out.println(result);
        out.close();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String result = getPurchaseList(request, response);
        processRequest(request, response, result);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String result = getPurchaseList(request, response);
        processRequest(request, response, result);
    }    
}
