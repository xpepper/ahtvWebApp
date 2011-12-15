<%@page import="java.util.*,it.matrix.alicehometv.logger.ActivityLogger"%>
<%
	String data = (String) request.getAttribute("data");
	String temaID = (String) request.getAttribute("temaID");
	List<Properties> lista = (List<Properties>) request.getAttribute("lista");
	response.setContentType("text/xml");

	if (lista != null) {
	%><canali>
	<%
			// Facciamo vedere soltanto i canali preferiti
			for (Properties p : lista) {
				String path = "/flash/xml/programmazione/" + data + "/canali/" + p.getProperty("numero") + "/programmazione.xml";
				ActivityLogger.debug(path);
	%>
		<canale>
		<nome><%=p.getProperty("nome")%></nome>
		<numero><%=p.getProperty("numero")%></numero>
		<logo><%=p.getProperty("imgFile")%></logo>
		<codice><%=p.getProperty("numero")%></codice>
		<!--#include virtual="<%= path %>"-->
		</canale>
	<%
	         }
	%></canali>
	<%
	} else {
			// Facciamo vedere tutto il tema
			String path = "/flash/xml/programmazione/" + data + "/temi/" + temaID + ".xml";
			ActivityLogger.debug(path);
	%><!--#include virtual="<%= path %>"-->
<%
	}
%>
