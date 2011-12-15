/*
 * CallSPGetAvvisami.java
 *
 * Created on 23 gennaio 2008, 10.12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.matrix.alicehometv.alert;

import it.matrix.alicehometv.db.DbConnectionProvider;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.apache.tomcat.dbcp.dbcp.DelegatingConnection;

/**
 *
 * @author alessandrobalasini
 */
public class CallSPAvvisami {
    
    /** Creates a new instance of CallSPGetAvvisami */
    public CallSPAvvisami() {
    }
 
    public static int RISULTATO_MESSAGGIO_OK = 200;
    private static int RISULTATO_SP_OK = 0;
    
    public ArrayList ritornaAvvisamiList(DbConnectionProvider dbConnectionProvider,int codAhtv)throws SQLException{
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
                callStm = ocon.prepareCall("{call PKG_FE_AHTV_LOGIN.GetProfiloUtente(?,?,?,?)}");
                
                //codice Ahtv
                callStm.setInt(1, codAhtv);
                callStm.registerOutParameter(2, OracleTypes.CURSOR);        
                callStm.registerOutParameter(3, Types.VARCHAR);
                callStm.registerOutParameter(4, Types.NUMERIC);

                // Execute the stored procedure and retrieve the IN/OUT value
                callStm.execute();
                
                rs = (ResultSet) callStm.getObject(2);
		while (rs.next()) {
			VediListAvvisami listaAvvisami = new VediListAvvisami();
                        listaAvvisami.setTipoServizio(rs.getString("v_idconsenso"));
                        listaAvvisami.setTipoInvio(rs.getString("v_idtipoinvio"));
                        listaAvvisami.setTipoConsenso(rs.getInt("n_flg_consenso"));
                        result.add(listaAvvisami);
		}

                if (RISULTATO_SP_OK == callStm.getInt(4)){
                    return result;
                }else{
                    //se sp è andata in errore ritorno eccezione
                    throw new SQLException("La SP ritorna un errore==="+callStm.getString(3));
                }                                       
            }else{
                throw new SQLException("connection is null");
            }
        }catch (SQLException sqlex) {
            throw new SQLException("Exception -->"+sqlex);
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
    
    //
    public int setTipoAvvisami(DbConnectionProvider dbConnectionProvider,int codAhtv, String tipoServizio, String tipoInvio, String tipoConsenso)throws SQLException{
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
                callStm = ocon.prepareCall("{call PKG_FE_AHTV_LOGIN.SetConsensoUtente(?,?,?,?,?,?)}");
                
                //codice Ahtv
                callStm.setInt(1, codAhtv);
                callStm.setString(2, tipoServizio.toUpperCase());        
                callStm.setString(3, tipoInvio.toUpperCase());
                callStm.setInt(4, Integer.parseInt(tipoConsenso));
                callStm.registerOutParameter(5, Types.VARCHAR);
                callStm.registerOutParameter(6, Types.NUMERIC);

                // Execute the stored procedure and retrieve the IN/OUT value
                callStm.execute();
                
                 if (RISULTATO_SP_OK == callStm.getInt(6)){
                    return RISULTATO_MESSAGGIO_OK;
                } else {    
                    //se sp è andata in errore ritorno eccezione
                    throw new SQLException("La SP ritorna un errore==="+callStm.getString(5));
                }  
            }else{
                throw new SQLException("connection is null");
            }
        }catch (SQLException sqlex) {
            throw new SQLException("Exception -->"+sqlex);
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
