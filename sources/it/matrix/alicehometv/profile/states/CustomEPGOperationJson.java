package it.matrix.alicehometv.profile.states;

import it.matrix.alicehometv.logger.ActivityLogger;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CustomEPGOperationJson extends CustomEPGOperation
{

    protected String userID;

    public static String doEscaping(Object s)
    {
        if (s != null)
        {
            return s.toString().replaceAll("\"", "\\\"");
        }
        return null;
    }

    public static String propertiesList2JSON(List<Properties> list)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[\n");

        for (Properties p : list)
        {
            sb.append("    {\n");
            Enumeration<Object> e = p.keys();
            while (e.hasMoreElements())
            {
                Object o = e.nextElement();
                sb.append("    \"").append(o).append("\"\t: \"").append(doEscaping(p.get(o))).append("\"");
                if (e.hasMoreElements())
                {
                    sb.append(",");
                }
                sb.append("\n");
            }
            sb.append("    }");
            if (list.indexOf(p) < list.size() - 1)
            {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    public void doOperation(HttpServletRequest req, HttpServletResponse resp)
    {
        Map map = req.getParameterMap();
        userID = getUserID(req);

        List<Properties> list = this.getOutput(map);
        if (list != null)
        {
            try
            {
                resp.setContentType("text/plain");
                resp.getWriter().write(propertiesList2JSON(list));
            }
            catch (IOException e)
            {
                ActivityLogger.logException(e);
            }
        }
    }

    public abstract List<Properties> getOutput(Map params);

}
