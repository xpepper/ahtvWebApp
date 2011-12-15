package it.matrix.alicehometv.profile.states;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.EPG;

import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomEPGUserChannelList extends CustomEPGOperation
{

    @Override
    public void doOperation(HttpServletRequest req, HttpServletResponse resp)
    {

        String pathInfo = req.getPathInfo();
        String[] dati = pathInfo.split("/");

        String data = dati[1];
        String temaID = dati[2].split("\\.")[0];
        int tema = 1;
        try
        {
            tema = Integer.parseInt(temaID);
        }
        catch (Exception e)
        {
            tema = 1;
        }

        String userID = getUserID(req);
        EPG epg = new EPG(getConnection());

        /**
         * userID vuoto = non autenticato, tutti i canali del tema; userID esiste ma non ha preferiti = tutti i canali del tema; userID esiste ed ha preferiti = soltanto i canali del tema che esistono tra i preferiti
         */

        req.setAttribute("data", data);
        req.setAttribute("temaID", temaID);

        if (userID == null || userID.length() < 1)
        {
            // Non autenticato
            ActivityLogger.debug("Non autenticato");
            closeConnection();
            doForward(req, resp);
            return;
        }

        List<Properties> canaliTema = null;
        try
        {
            canaliTema = epg.getChannelList(tema);
        }
        catch (SQLException e)
        {
            ActivityLogger.logException("Errore nella lista canali utente", e);
        }

        List<Properties> canaliUtente = null;
        try
        {
            canaliUtente = epg.getUserChannelList(userID);
        }
        catch (SQLException e)
        {
            ActivityLogger.logException("Errore nella lista canali utente", e);
        }

        closeConnection();

        if (canaliUtente == null || canaliUtente.size() < 1)
        {
            // Non ha preferiti
            ActivityLogger.debug("Senza preferiti");
            doForward(req, resp);
            return;
        }

        List<Properties> outList = buildList(canaliTema, canaliUtente);

        ActivityLogger.debug("Tema: " + canaliTema.toString());
        ActivityLogger.debug("Utente: " + canaliUtente.toString());
        ActivityLogger.debug("Risultato: " + outList.toString());

        req.setAttribute("lista", outList);

        doForward(req, resp);
    }

    private void doForward(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.getSession().getServletContext().getRequestDispatcher("/templates/epg.jsp").forward(req, resp);
        }
        catch (Exception e)
        {
            ActivityLogger.logException("Errore nella forward", e);
        }
    }

    private static List<Properties> buildList(List<Properties> canali, List<Properties> canaliUtente)
    {
        List<Properties> lista = new LinkedList<Properties>();
        for (Properties p : canali)
        {
            if (existsIn(p.getProperty("id"), canaliUtente, "channel_id"))
            {
                lista.add(p);
            }
        }
        return lista;
    }

    private static boolean existsIn(String s, List<Properties> lista, String key)
    {
        for (Properties p : lista)
        {
            if (s.equals(p.get(key)))
            {
                return true;
            }
        }
        return false;
    }
}
