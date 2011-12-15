package it.matrix.alicehometv.profile;

import it.matrix.alicehometv.logger.ActivityLogger;

import java.sql.*;
import java.util.*;

import oracle.jdbc.*;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.tomcat.dbcp.dbcp.DelegatingConnection;

public class EPG {

	private Connection conn = null;

	public EPG(Connection conn) {
		if (conn != null) {
			this.conn = conn;
			ActivityLogger.debug("Connection: " + conn.getClass().getName());
		}
	}

	public static void putIn(Properties p, ResultSet rs, String keyName, String fieldName) throws SQLException {
		String value = rs.getString(fieldName);
		if (keyName != null && value != null) {
			p.put(keyName, value);
		}
	}

	/**
	 * codTema == 0 : tutti i temi
	 */
	public List<Properties> getChannelList(int codTema) throws SQLException {
		List<Properties> lista = new Vector<Properties>();

		String sql = "CALL PKG_FE_AHTV_CANALI.GetListaCanali (?,?,?,?)";
		CallableStatement stmt = conn.prepareCall(sql);

		if (codTema == 0) {
			stmt.setNull(1, Types.NUMERIC);
		} else {
			stmt.setInt(1, codTema);
		}
		stmt.registerOutParameter(2, OracleTypes.CURSOR);
		stmt.registerOutParameter(3, Types.VARCHAR);
		stmt.registerOutParameter(4, Types.NUMERIC);
		stmt.execute();

		String errMsg = stmt.getString(3);
		int errCode = stmt.getInt(4);

		ActivityLogger.info("SQL " + sql + "; MSG=" + errMsg + "; CODE=" + errCode);

		ResultSet rs = (ResultSet) stmt.getObject(2);
		while (rs.next()) {
			Properties p = new Properties();
			putIn(p, rs, "id", "v_channel_name");
			putIn(p, rs, "nome", "v_nomecanale");
			putIn(p, rs, "descrizione", "v_descrizione");
			putIn(p, rs, "imgFile", "v_nomefile");
			putIn(p, rs, "temaId", "n_temaid");
			putIn(p, rs, "numero", "n_idchannel");
			putIn(p, rs, "icona", "v_icona");
			lista.add(p);
			
			
			
	       // “icona”    : “/img/canali/Rai_uno.gif

			
			
		}

		return lista;
	}

	public List<Properties> getThemeList() throws SQLException {

		List<Properties> lista = new Vector<Properties>();

		String sql = "CALL PKG_FE_AHTV_CANALI.GetListaTemi (?,?,?)";
		CallableStatement stmt = conn.prepareCall(sql);

		stmt.registerOutParameter(1, OracleTypes.CURSOR);
		stmt.registerOutParameter(2, Types.VARCHAR);
		stmt.registerOutParameter(3, Types.NUMERIC);
		stmt.execute();

		String errMsg = stmt.getString(2);
		int errCode = stmt.getInt(3);

		ActivityLogger.info("SQL " + sql + "; MSG=" + errMsg + "; CODE=" + errCode);

		ResultSet rs = (ResultSet) stmt.getObject(1);
		while (rs.next()) {
			Properties p = new Properties();
			putIn(p, rs, "tema_id", "n_temaid");
			putIn(p, rs, "descrizione", "v_tema");
			lista.add(p);
		}

		return lista;
	}

	public List<Properties> getUserChannelList(String userId) throws SQLException {

		if (userId == null || userId.length() < 1) {
			ActivityLogger.warning("getUserChannelList: UserId vuoto");
			return null;
		}

		String sql = "CALL PKG_FE_AHTV_CANALI.GetCanaliUtente (?,?,?,?)";

		CallableStatement stmt = conn.prepareCall(sql);
		stmt.setString(1, userId);
		stmt.registerOutParameter(2, OracleTypes.CURSOR);
		stmt.registerOutParameter(3, Types.VARCHAR);
		stmt.registerOutParameter(4, Types.NUMERIC);
		stmt.execute();

		String errMsg = stmt.getString(3);
		int errCode = stmt.getInt(4);

		ActivityLogger.info("SQL " + sql + "; MSG=" + errMsg + "; CODE=" + errCode);

		List<Properties> lista = new Vector<Properties>();

		ResultSet rs = (ResultSet) stmt.getObject(2);
		while (rs.next()) {
			Properties p = new Properties();
			putIn(p, rs, "user_id", "n_idutente");
			putIn(p, rs, "channel_id", "v_channel_name");
			lista.add(p);
		}

		return lista;
	}

	public boolean setUserChannelList(String userId, List<String> list) throws SQLException {

		// ActivityLogger.debug(userId + "; " + list.toString() + " (" +
		// list.size() + ")");

		if (userId == null || userId.length() < 1) {
			ActivityLogger.warning("setUserChannelList: UserId vuoto");
			return false;
		}

		String sql = "CALL PKG_FE_AHTV_CANALI.SetCanaliUtente (?,?,?,?)";

		Connection nconn = ((DelegatingConnection) conn).getInnermostDelegate();
		ActivityLogger.debug(nconn.getClass().getName());
		OracleConnection oconn = (OracleConnection) nconn;

		ArrayDescriptor desc = ArrayDescriptor.createDescriptor("LIST_CHANNELNAME", oconn);

		CallableStatement stmt = oconn.prepareCall(sql);
		stmt.setString(1, userId);
		if (list != null && list.size() > 0) {
			ARRAY lista = new ARRAY(desc, oconn, list.toArray(new String[1]));
			((OracleCallableStatement) stmt).setARRAY(2, lista);
		} else {
			((OracleCallableStatement) stmt).setNull(2, OracleTypes.ARRAY, "LIST_CHANNELNAME");
		}
		stmt.registerOutParameter(3, Types.VARCHAR);
		stmt.registerOutParameter(4, Types.NUMERIC);
		stmt.execute();

		String errMsg = stmt.getString(3);
		int errCode = stmt.getInt(4);

		ActivityLogger.info("SQL " + sql + "; MSG=" + errMsg + "; CODE=" + errCode);

		return errCode == 0;
	}
}
