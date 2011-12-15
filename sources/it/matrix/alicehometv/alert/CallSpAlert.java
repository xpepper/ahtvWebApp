/*
 * CallSpAlert.java
 *
 * Created on 10 gennaio 2008, 10.48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.matrix.alicehometv.alert;

import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.tomcat.dbcp.dbcp.DelegatingConnection;



/**
 *
 * @author alessandrobalasini
 */
public class CallSpAlert {
    
    /** Creates a new instance of CallSpAlert */
    public CallSpAlert() {
    }
    
    private static int RISULTATO_SP_OK = 0;
    private static int RISULTATO_SP_KO_ASSET_NO_TIME = -9;
    private static int RISULTATO_SP_KO_MAX_LIMITE_SEND_SMS = -10;
    private static int RISULTATO_SP_KO_ASSET_START_NO_DEL = -9;
    public static int RISULTATO_MESSAGGIO_OK = 200;
      
    public int ritornaAlertAdd(DbConnectionProvider dbConnectionProvider, int codAhtv,List listAlertAddType,List listAlertAssetId,List listAlertListTime)throws SQLException{
        Connection con = null;
        CallableStatement callStm = null;
        try {
            //AlertStore alertStore = (AlertStore) arrayParamSp.get(0);
            //con = Utils.openDBConnection();
            con = dbConnectionProvider.connection();
            Connection ncon = ((DelegatingConnection) con).getInnermostDelegate();
            OracleConnection ocon = (OracleConnection) ncon;
            ArrayDescriptor descAddType = ArrayDescriptor.createDescriptor("LIST_IDTIPOINVIO", ocon);
            ArrayDescriptor descAssetId = ArrayDescriptor.createDescriptor("LIST_ASSETID", ocon);
            ArrayDescriptor descListTime = ArrayDescriptor.createDescriptor("LIST_TIME", ocon);

            if (con != null){
                // Call a procedure with one IN/OUT parameter
                callStm = ocon.prepareCall("{call pkg_fe_ahtv_alert.SetAlertEpg(?,?,?,?,?,?)}");
                
                //codice Ahtv
                callStm.setInt(1, codAhtv);

                //LIST_IDTIPOINVIO
                ARRAY listaAddType = new ARRAY(descAddType, ocon, listAlertAddType.toArray(new String[listAlertAddType.size()-1]));
                callStm.setArray(2, listaAddType);
               
                //LIST_ASSETID
                ARRAY listaAssetId = new ARRAY(descAssetId, ocon, listAlertAssetId.toArray(new String[listAlertAssetId.size()-1]));
                callStm.setArray(3, listaAssetId);

                //LIST_TIME
                ARRAY listaListTime = new ARRAY(descListTime, ocon, listAlertListTime.toArray(new Integer[0]));
                callStm.setArray(4, listaListTime);


                callStm.registerOutParameter(5, Types.VARCHAR);
                callStm.registerOutParameter(6, Types.NUMERIC);

                // Execute the stored procedure and retrieve the IN/OUT value
                callStm.execute();
                
                if (RISULTATO_SP_OK == callStm.getInt(6)){
                    return RISULTATO_MESSAGGIO_OK;
                } else if (RISULTATO_SP_KO_ASSET_NO_TIME == callStm.getInt(6)){
                    return RISULTATO_SP_KO_ASSET_NO_TIME;
                } else if (RISULTATO_SP_KO_MAX_LIMITE_SEND_SMS == callStm.getInt(6)){
                    return RISULTATO_SP_KO_MAX_LIMITE_SEND_SMS;                
                } else {    
                    //se sp è andata in errore ritorno eccezione
//                    ActivityLogger.error("La SP ritorna un errore==="+callStm.getString(5));
                    throw new SQLException("La SP ritorna un errore==="+callStm.getString(5));
                }                                       
            }else{
//                ActivityLogger.error("connection is null");
                throw new SQLException("connection is null");
            }
        }catch (SQLException sqlex) {
//            ActivityLogger.error("Exception: "+callStm.getString(5)+"-->"+sqlex);
            throw new SQLException("Exception: "+callStm.getString(5)+"-->"+sqlex);
        }finally {
            try{
                if (callStm != null){
                    callStm.close();
                }
            }catch(Exception ex){
                //nothing
            }

            try{
                if (con != null){
                    //Utils.closeDBConnection(con);
                    dbConnectionProvider.close(con);
                }
            }catch(Exception ex){
                //nothing
            }            
        }        
    }
    
     public int ritornaAlertDel(DbConnectionProvider dbConnectionProvider, int codAhtv,List listAlertAddType,List listAlertAssetId,List listAlertListTime)throws SQLException{
        Connection con = null;
        CallableStatement callStm = null;
        try {
            //con = Utils.openDBConnection();
            con = dbConnectionProvider.connection();
            Connection ncon = ((DelegatingConnection) con).getInnermostDelegate();
            OracleConnection ocon = (OracleConnection) ncon;
            ArrayDescriptor descAddType = ArrayDescriptor.createDescriptor("LIST_IDTIPOINVIO", ocon);
            ArrayDescriptor descAssetId = ArrayDescriptor.createDescriptor("LIST_ASSETID", ocon);
        
            if (con != null){
                // Call a procedure with one IN/OUT parameter
                callStm = ocon.prepareCall("{call pkg_fe_ahtv_alert.DeleteAlertEpg(?,?,?,?,?)}");
                
                //codice Ahtv
                callStm.setInt(1, codAhtv);

                //LIST_IDTIPOINVIO
                ARRAY listaAddType = new ARRAY(descAddType, ocon, listAlertAddType.toArray(new String[listAlertAddType.size()-1]));
                callStm.setArray(2, listaAddType);
               
                //LIST_ASSETID
                ARRAY listaAssetId = new ARRAY(descAssetId, ocon, listAlertAssetId.toArray(new String[listAlertAssetId.size()-1]));
                callStm.setArray(3, listaAssetId);

                callStm.registerOutParameter(4, Types.VARCHAR);
                callStm.registerOutParameter(5, Types.NUMERIC);

                // Execute the stored procedure and retrieve the IN/OUT value
                callStm.execute();
             
                if (RISULTATO_SP_OK == callStm.getInt(5)){
                    return RISULTATO_MESSAGGIO_OK;
                } else if (RISULTATO_SP_KO_ASSET_START_NO_DEL == callStm.getInt(5)){
                    return RISULTATO_SP_KO_ASSET_START_NO_DEL;
                } else{
                    //se sp è andata in errore ritorno eccezione
//                    ActivityLogger.error("La SP ritorna un errore==="+ callStm.getString(4));
                    throw new SQLException("La SP ritorna un errore==="+ callStm.getString(4));
                }                                       
            }else{
//                ActivityLogger.error("connection is null");
                throw new SQLException("connection is null");
            }
        }catch (SQLException sqlex) {
//            ActivityLogger.error("Exception: "+callStm.getString(4)+"-->"+sqlex);
            throw new SQLException("Exception: "+callStm.getString(4)+"-->"+sqlex);
        }finally {
            try{
                if (callStm != null){
                    callStm.close();
                }
            }catch(Exception ex){
                //nothing
            }

            try{
                if (con != null){
                    //Utils.closeDBConnection(con);
                    dbConnectionProvider.close(con);
                }
            }catch(Exception ex){
                //nothing
            }            
        }        
    }
    
     public ArrayList ritornaAlertList(DbConnectionProvider dbConnectionProvider,int codAhtv,List listAlertAddType,List listAlertAssetId,List listAlertListTime)throws SQLException{
        Connection con = null;
        CallableStatement callStm = null;
        ResultSet rs = null;
        try {
            ArrayList result = new ArrayList();
            //con = Utils.openDBConnection();
            con = dbConnectionProvider.connection();
            Connection ncon = ((DelegatingConnection) con).getInnermostDelegate();
            OracleConnection ocon = (OracleConnection) ncon;
            
            if (con != null){
                // Call a procedure with one IN/OUT parameter
                callStm = ocon.prepareCall("{call pkg_fe_ahtv_alert.GetAlertEpg(?,?,?,?)}");
                
                //codice Ahtv
                callStm.setInt(1, codAhtv);
                callStm.registerOutParameter(2, OracleTypes.CURSOR);        
                callStm.registerOutParameter(3, Types.VARCHAR);
                callStm.registerOutParameter(4, Types.NUMERIC);

                // Execute the stored procedure and retrieve the IN/OUT value
                callStm.execute();
                
                rs = (ResultSet) callStm.getObject(2);
		while (rs.next()) {
			VediListAlert listaAlert = new VediListAlert();
                        listaAlert.setAssetId(Integer.parseInt(rs.getString("n_assetid")));
			listaAlert.setTitle(rs.getString("v_title"));
                        listaAlert.setChannelName(rs.getString("v_channelName"));
                        listaAlert.setIdChannel(rs.getInt("n_channelID"));
                        listaAlert.setStartDate(rs.getString("v_startDate"));
                        listaAlert.setStartHour(rs.getString("v_startHour"));
                        listaAlert.setIdTipoInvio(rs.getString("v_idtipoinvio"));
                        listaAlert.setStatus(rs.getString("v_status"));
                        result.add(listaAlert);
		}

                if (RISULTATO_SP_OK == callStm.getInt(4)){
                    return result;
                }else{
                    //se sp è andata in errore ritorno eccezione
//                    ActivityLogger.error("La SP ritorna un errore==="+callStm.getString(3));
                    throw new SQLException("La SP ritorna un errore==="+callStm.getString(3));
                }                                       
            }else{
//                ActivityLogger.error("connection is null");
                throw new SQLException("connection is null");
            }
        }catch (SQLException sqlex) {
//            ActivityLogger.error("Exception: "+callStm.getString(3)+"-->"+sqlex);
            throw new SQLException("Exception: "+callStm.getString(3)+"-->"+sqlex);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                }
            }catch(Exception ex){
                //nothing
            }
            
            try{
                if (callStm != null){
                    callStm.close();
                }
            }catch(Exception ex){
                //nothing
            }

            try{
                if (con != null){
                    //Utils.closeDBConnection(con);
                    dbConnectionProvider.close(con);                    
                }
            }catch(Exception ex){
                //nothing
            }            
        }       
    }
     
     public ArrayList ritornaShowCountAlert(DbConnectionProvider dbConnectionProvider,int codAhtv)throws SQLException{
//        ActivityLogger.info("enter ritornaShowCountAlert");
        Connection con = null;
        CallableStatement callStm = null;
        ResultSet rs = null;
        try {
            ArrayList result = new ArrayList();
            //con = Utils.openDBConnection();
            con = dbConnectionProvider.connection();
            Connection ncon = ((DelegatingConnection) con).getInnermostDelegate();
            OracleConnection ocon = (OracleConnection) ncon;
//            ActivityLogger.debug("con==="+con);
            if (con != null){
                // Call a procedure with one IN/OUT parameter
                callStm = ocon.prepareCall("{call pkg_fe_ahtv_alert.getcountalert(?,?,?,?)}");
          
//                ActivityLogger.debug("codAhtv==="+codAhtv);
          
                //codice Ahtv
                callStm.setInt(1, codAhtv);
                callStm.registerOutParameter(2, OracleTypes.CURSOR);        
                callStm.registerOutParameter(3, Types.VARCHAR);
                callStm.registerOutParameter(4, Types.NUMERIC);

                // Execute the stored procedure and retrieve the IN/OUT value
                callStm.execute();
                ActivityLogger.debug("callStm.execute()");
                rs = (ResultSet) callStm.getObject(2);
		while (rs.next()) {
			VediShowCountAlert listaShowCountAlert = new VediShowCountAlert();
//                        ActivityLogger.debug("start_date");
//                        ActivityLogger.debug("start_date==="+rs.getString("start_date"));
                        listaShowCountAlert.setStartDate(rs.getString("start_date"));
//                        ActivityLogger.debug("end_date");
//                        ActivityLogger.debug("end_date==="+rs.getString("end_date"));
                        listaShowCountAlert.setEndDate(rs.getString("end_date"));
//                        ActivityLogger.debug("count_sms_set");
//                        ActivityLogger.debug("count_sms_set==="+rs.getString("count_sms_set"));
                        listaShowCountAlert.setCountSmsSet(rs.getInt("count_sms_set"));
//                        ActivityLogger.debug("count_sms");
//                        ActivityLogger.debug("count_sms==="+rs.getString("count_sms"));
                        listaShowCountAlert.setCountSms(rs.getInt("count_sms"));
//                        ActivityLogger.debug("count_email_set");
//                        ActivityLogger.debug("count_email_set==="+rs.getString("count_email_set"));
                        listaShowCountAlert.setCountEmailSet(rs.getInt("count_email_set"));
//                        ActivityLogger.debug("count_email_sms");
//                        ActivityLogger.debug("count_email_sms==="+rs.getString("count_email_sms"));
                        listaShowCountAlert.setCountEmail(rs.getInt("count_email"));
                        result.add(listaShowCountAlert);
		}
//                ActivityLogger.debug("out while");
                if (RISULTATO_SP_OK == callStm.getInt(4)){
                    return result;
                }else{
                    //se sp è andata in errore ritorno eccezione
//                    ActivityLogger.error("La SP ritorna un errore==="+callStm.getString(3));
                    throw new SQLException("La SP ritorna un errore==="+callStm.getString(3));
                }                                       
            }else{
//                ActivityLogger.error("connection is null");
                throw new SQLException("connection is null");
            }
        }catch (SQLException sqlex) {
//            ActivityLogger.error("Exception: "+callStm.getString(3)+"-->"+sqlex);
            throw new SQLException("Exception: "+callStm.getString(3)+"-->"+sqlex);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                }
            }catch(Exception ex){
                //nothing
            }
            
            try{
                if (callStm != null){
                    callStm.close();
                }
            }catch(Exception ex){
                //nothing
            }

            try{
                if (con != null){
                    //Utils.closeDBConnection(con);
                    dbConnectionProvider.close(con);                    
                }
            }catch(Exception ex){
                //nothing
            }            
        }       
    }
}

