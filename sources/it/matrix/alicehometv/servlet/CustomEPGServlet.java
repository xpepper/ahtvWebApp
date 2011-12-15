package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.states.CustomEPGOperation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CustomEPGServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doService(req, resp);
    }

    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String pathInfo = req.getPathInfo();
        DbConnectionProvider dbConnectionProvider = (DbConnectionProvider) getServletContext().getAttribute(DbConnectionProvider.ATTRIBUTE_NAME);

        CustomEPGOperation operation = CustomEPGOperation.getAssociatedState(pathInfo);
        ActivityLogger.debug(pathInfo + " => " + (operation != null ? operation.getClass().getName() : "(null)"));
        if (operation != null)
        {
            operation.setDbConnectionProvider(dbConnectionProvider);
            operation.doOperation(req, resp);
        }
    }
}
