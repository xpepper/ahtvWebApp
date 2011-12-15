package it.matrix.alicehometv.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.*;

public class ActivityLogger
{
    private static final String LOGGER_NAME = "application";
    
    private static Level itsOldLevel;
    private static Logger itsLogger;
    
    private static Logger logger()
    {
        if (itsLogger == null)
        {
            if (LogManager.exists(LOGGER_NAME) != null)
                itsLogger = Logger.getLogger(LOGGER_NAME);
            else
            {
                itsLogger = Logger.getLogger("default logger");
                itsLogger.setLevel(Level.DEBUG);
                ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout("[%d{dd MMM yyyy HH:mm:ss}] %-5p %c - %m %n"));
                consoleAppender.setName(ConsoleAppender.SYSTEM_OUT);
                consoleAppender.setTarget(ConsoleAppender.SYSTEM_OUT);
                itsLogger.addAppender(consoleAppender);
            }
            itsOldLevel = itsLogger.getLevel();
        }

        return itsLogger;
    }
    
    public static void off()
    {
        itsOldLevel = logger().getLevel();
        logger().setLevel(Level.OFF);
    }

    public static void resume()
    {
        logger().setLevel(itsOldLevel);
    }
    
    public static void debug(String aMessage)
    {
        logger().debug(aMessage);
    }

    public static void info(String aMessage)
    {
        logger().info(aMessage);
    }

    public static void warning(String aMessage)
    {
        logger().warn(aMessage);
    }

    public static void error(String aMessage)
    {
        logger().error(aMessage);
    }

    public static void fatal(String aMessage)
    {
        logger().fatal(aMessage);
    }

    @SuppressWarnings("unchecked")
    public static void logParametersFrom(HttpServletRequest aRequest)
    {
        if (logger().isDebugEnabled())
        {
            debug("*** Headers ***");
            Enumeration<String> headerNames = aRequest.getHeaderNames();
            if (headerNames != null)
            {
                while (headerNames.hasMoreElements())
                {
                    String eachHeaderName = headerNames.nextElement();
                    debug(eachHeaderName + ": " + aRequest.getHeader(eachHeaderName));
                }
            }
            
            debug("*** Character Encoding ***");
            debug(aRequest.getCharacterEncoding());
            
            debug("*** Request parameters ***");
            Enumeration<String> parameterNames = aRequest.getParameterNames();
            if (parameterNames != null)
            {
                while (parameterNames.hasMoreElements())
                {
                    String eachParameterName = parameterNames.nextElement();
                    String[] parameterValues = aRequest.getParameterValues(eachParameterName);
                    debug(eachParameterName + ": " + ArrayUtils.toString(parameterValues));
                }
            }
             
        }
    }

    public static void logException(Throwable exc)
    {
        logException("", exc);
    }

    public static void logException(String aMessage, Throwable exc)
    {
        error(aMessage + " " + printStackTraceIntoString(exc));
    }

    private static String printStackTraceIntoString(Throwable exc)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exc.printStackTrace(printWriter);

        return stringWriter.toString();
    }
}
