/*
 * ShowCountAlert.java
 *
 * Created on 31 gennaio 2008, 16.34
 */

package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.alert.CallSpAlert;
import it.matrix.alicehometv.alert.VediShowCountAlert;
import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.util.AhtvCookie;
import it.matrix.alicehometv.util.CookieValueDetector;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author AlessandroBalasini
 * @version
 */
public class ShowCountAlert extends HttpServlet {
    
    
     private String getShowCountAlert(HttpServletRequest request){
        ActivityLogger.info("enter getShowCountAlert");        
        JSONObject json = new JSONObject();
        JSONObject tmpJson = new JSONObject();
        JSONArray jsonItems = new JSONArray();
        int result = 0;
        ArrayList arrayResult = new ArrayList();
        try{
            CallSpAlert spAlert = new CallSpAlert();
            //recupero codAhtv
            //recupero il cli presente nel cookie
            AhtvCookie ahtvCookie = new CookieValueDetector(request.getCookies()).detectAhtvCookie();
            Integer codAhtv = ahtvCookie.ahtvUserId();
            if (codAhtv == null){
                throw new IllegalArgumentException("Il codAhtv non può assumere valore: " + codAhtv);                
            }
            DbConnectionProvider dbConnectionProvider = (DbConnectionProvider) getServletContext().getAttribute(DbConnectionProvider.ATTRIBUTE_NAME);
            
            arrayResult = spAlert.ritornaShowCountAlert(dbConnectionProvider,codAhtv);
            for (int k=0; k<arrayResult.size(); k++){
                VediShowCountAlert listaShowCountAlert = (VediShowCountAlert)arrayResult.get(k);
                tmpJson.put("startDate",listaShowCountAlert.getStartDate());
                tmpJson.put("endDate",listaShowCountAlert.getEndDate());
                tmpJson.put("count_sms_settati",listaShowCountAlert.getCountSmsSet());
                tmpJson.put("count_sms_rimasti",listaShowCountAlert.getCountSms());
                tmpJson.put("count_email_settati",listaShowCountAlert.getCountEmailSet());
                tmpJson.put("count_email_rimasti",listaShowCountAlert.getCountEmail());
                jsonItems.add(tmpJson);          
            }    
                json.put("ResultCode",CallSpAlert.RISULTATO_MESSAGGIO_OK);
                json.put("ResultMessage","");
                json.put("Alerts", jsonItems);
                ActivityLogger.debug("TEST=="+json.toString());                       
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
        out.println("<title>Servlet ShowCountAlert</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet ShowCountAlert at " + request.getContextPath () + "</h1>");
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
        String result = getShowCountAlert(request);
        processRequest(request, response, result);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String result = getShowCountAlert(request);
        processRequest(request, response, result);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
