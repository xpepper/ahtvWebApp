/*
 * GetAvvisami.java
 *
 * Created on 23 gennaio 2008, 10.10
 */

package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.alert.CallSPAvvisami;
import it.matrix.alicehometv.alert.VediListAvvisami;
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
 * @author alessandrobalasini
 * @version
 */
public class GetAvvisami extends HttpServlet {
    
    private String getAvvisami(HttpServletRequest request){
        ActivityLogger.info("enter getAvvisami");        
        JSONObject json = new JSONObject();
        JSONObject tmpJson = new JSONObject();
        JSONArray jsonItems = new JSONArray();
        ArrayList arrayResult = new ArrayList();
        try{
            CallSPAvvisami getAvvisami = new CallSPAvvisami();
            //recupero codAhtv
            //recupero il cli presente nel cookie
            AhtvCookie ahtvCookie = new CookieValueDetector(request.getCookies()).detectAhtvCookie();
            Integer codAhtv = ahtvCookie.ahtvUserId();
            if (codAhtv == null){
                throw new IllegalArgumentException("Il codAhtv non può assumere valore: " + codAhtv);                
            }
            DbConnectionProvider dbConnectionProvider = (DbConnectionProvider) getServletContext().getAttribute(DbConnectionProvider.ATTRIBUTE_NAME);
           
            arrayResult = getAvvisami.ritornaAvvisamiList(dbConnectionProvider,codAhtv);
            for (int k=0; k<arrayResult.size(); k++){
                VediListAvvisami vediListAvvisami = (VediListAvvisami)arrayResult.get(k);
                tmpJson.put("tipoServizio", vediListAvvisami.getTipoServizio());
                tmpJson.put("tipoInvio", vediListAvvisami.getTipoInvio());
                tmpJson.put("tipoConsenso", vediListAvvisami.getTipoConsenso());
                
                jsonItems.add(tmpJson);          
             }    
            json.put("ResultCode",CallSPAvvisami.RISULTATO_MESSAGGIO_OK);
            json.put("ResultMessage","OK");
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
        out.println("<title>Servlet GetAvvisami</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet GetAvvisami at " + request.getContextPath () + "</h1>");
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
        String result = getAvvisami(request);
        processRequest(request, response, result);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String result = getAvvisami(request);
        processRequest(request, response, result);        
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
