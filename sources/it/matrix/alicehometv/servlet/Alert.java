/*
 * Alert.java
 *
 * Created on 10 gennaio 2008, 9.40
 */

package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.alert.CallSpAlert;
import it.matrix.alicehometv.alert.VediListAlert;
import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.util.AhtvCookie;
import it.matrix.alicehometv.util.CookieValueDetector;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author alessandrobalasini
 * @version
 */
public class Alert extends HttpServlet {
    
    public static final String ALERT_LIST = "LIST";
    public static final String ALERT_ADD = "ADD";
    public static final String ALERT_DEL = "DEL";
    
    private static final String ASSET_ID_SPLIT = ",";
        
    private ArrayList getTokenizer(String paramToken) throws Exception{
        ArrayList result = new ArrayList();
        ActivityLogger.debug("listParamToken==="+paramToken);
        StringTokenizer token = new StringTokenizer(paramToken);
        while (token.hasMoreElements()){
            result.add(token.nextToken(ASSET_ID_SPLIT));
        }
        return result;
    }
    
    private String getAlertType(HttpServletRequest request){
        ActivityLogger.info("enter getAlertType");        
        JSONObject json = new JSONObject();
        JSONObject tmpJson = new JSONObject();
        JSONArray jsonItems = new JSONArray();
        int result = 0;
        ArrayList arrayResult = new ArrayList();
        try{
            String type = request.getParameter("type");
            String assetId = request.getParameter("assetId");
            String addType = request.getParameter("addType");
            
            CallSpAlert spAlert = new CallSpAlert();
            List listAlertAddType = new ArrayList();
            List listAlertAssetId = new ArrayList();
            List listAlertListTime = new ArrayList();
            //recupero codAhtv
            //recupero il cli presente nel cookie
            
            AhtvCookie ahtvCookie = new CookieValueDetector(request.getCookies()).detectAhtvCookie();
            Integer codAhtv = ahtvCookie.ahtvUserId();
            if (codAhtv == null){
                throw new IllegalArgumentException("Il codAhtv non può assumere valore: " + codAhtv);                
            }
            /*
            //addType=SMS,EMAIL
            if (addType==null){
                listAlertAddType.add(null);
            }else{
                StringTokenizer token = new StringTokenizer(addType);
                while (token.hasMoreElements()){
                    listAlertAddType.add(token.nextToken(ASSET_ID_SPLIT));
                }                
            }    
            */
            //assetId=1000,2000,3000 
            if (assetId==null){
                //addType=SMS,EMAIL
                listAlertAddType.add(addType);
                //assetId
                listAlertAssetId.add(null);    
                //setListTime
                listAlertListTime.add(new Integer(0));
            }else{
                StringTokenizer token = new StringTokenizer(assetId);
                while (token.hasMoreElements()){
                    //addType=SMS,EMAIL
                    listAlertAddType.add(addType);
                    //assetId
                    listAlertAssetId.add(token.nextToken(ASSET_ID_SPLIT));
                    //setListTime
                    listAlertListTime.add(new Integer(0));
                }                
            }
            
            DbConnectionProvider dbConnectionProvider = (DbConnectionProvider) getServletContext().getAttribute(DbConnectionProvider.ATTRIBUTE_NAME);
            
            if (ALERT_ADD.equalsIgnoreCase(type) || ALERT_DEL.equalsIgnoreCase(type)){
                //ALERT_ADD
                if (ALERT_ADD.equalsIgnoreCase(type)){
                    //ADD
                    result = spAlert.ritornaAlertAdd(dbConnectionProvider, codAhtv,listAlertAddType,listAlertAssetId,listAlertListTime);
                } else {
                    //DEL
                    result = spAlert.ritornaAlertDel(dbConnectionProvider,codAhtv,listAlertAddType,listAlertAssetId,listAlertListTime);
                }        
                json.put("ResultCode",result);
                json.put("ResultMessage","");
                json.put("Result","");
                
            } else if (ALERT_LIST.equalsIgnoreCase(type)){
                arrayResult = spAlert.ritornaAlertList(dbConnectionProvider,codAhtv,listAlertAddType,listAlertAssetId,listAlertListTime);
                for (int k=0; k<arrayResult.size(); k++){
                    VediListAlert vediListAlert = (VediListAlert)arrayResult.get(k);
                    tmpJson.put("assetId",vediListAlert.getAssetId());
                    tmpJson.put("titolo", vediListAlert.getTitle());
                    tmpJson.put("canale", vediListAlert.getChannelName());
                    tmpJson.put("numCanale", vediListAlert.getIdChannel());
                    tmpJson.put("data", vediListAlert.getStartDate());
                    tmpJson.put("ora", vediListAlert.getStartHour());
                    tmpJson.put("avviso", vediListAlert.getIdTipoInvio());
                    tmpJson.put("stato", vediListAlert.getStatus());
                    jsonItems.add(tmpJson);          
                }    
                json.put("ResultCode",CallSpAlert.RISULTATO_MESSAGGIO_OK);
                json.put("ResultMessage","");
                json.put("Alerts", jsonItems);
                ActivityLogger.debug("TEST=="+json.toString());    
            }else{
                throw new IllegalArgumentException ("Il valore type non è corretto");
            }            
        }catch(Exception ex){
            ActivityLogger.error("Exception ==="+ex);
            json.put("ResultCode",HttpURLConnection.HTTP_NOT_FOUND); 
        }    
        return json.toString();
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String result)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        /* TODO output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Alert</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet Alert at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
         */
        out.println(result);
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String result = getAlertType(request);
        processRequest(request, response, result);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String result = getAlertType(request);
        processRequest(request, response, result);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
