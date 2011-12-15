package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.captcha.CaptchaServiceSigleton;
import it.matrix.alicehometv.logger.ActivityLogger;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.*;

@SuppressWarnings("serial")
public class ImageCaptchaServlet extends HttpServlet
{
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ImageFormatException, IOException 
    {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try
        {
            // get the session id that will identify the generated captcha. The same id must be used to validate the response
            String captchaId = httpServletRequest.getSession().getId();
            jpegOutputStream = createCaptchaWith(captchaId, httpServletRequest.getLocale());
        }
        catch (CaptchaServiceException exception)
        {
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
            return;
        }

        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(jpegOutputStream.toByteArray());
        outputStream.flush();
        outputStream.close();
    }

    private ByteArrayOutputStream createCaptchaWith(String captchaId, Locale locale) throws IOException
    {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        BufferedImage challenge = CaptchaServiceSigleton.instance().getImageChallengeForID(captchaId, locale);

        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
        jpegEncoder.encode(challenge);

        ActivityLogger.info("Created captcha for user session " + captchaId + " and locale " + locale);
        return jpegOutputStream;
    }
}
