package it.matrix.alicehometv.profile.iptv;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.pssc.GetPayload;
import it.matrix.alicehometv.pssc.ParserSaxResultPersonalData;
import it.matrix.alicehometv.util.TimeProvider;
import it.telecomitalia.pssc.PSSCOrderEntryServiceSoap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.xml.parsers.*;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IPTVUserProfileBrokerImpl implements IPTVUserProfileBroker
{
    private static final String OK_ACK_RESULT_CODE = "200";
    private static final String OK_SUCCESS_RESULT_CODE = "1000";

    private static final String SET_IPTV_OPERATION_TYPE = "SetIPTVData";
    private static final String PERSONAL_DATA_ACTION = "PersonalData";
    
    private String urlHttpForFinder;
    private PSSCOrderEntryServiceSoap psscOrderExecutor;
    private TimeProvider timeProvider;

    public IPTVUserProfileBrokerImpl(String urlForFinder, PSSCOrderEntryServiceSoap aPsscOrderExecutor, TimeProvider aTimeProvider)
    {
        urlHttpForFinder = urlForFinder;
        psscOrderExecutor = aPsscOrderExecutor;
        timeProvider = aTimeProvider;
    }

    public IPTVProfile finderForCli(String cli)
    {
        ActivityLogger.info("enter finderForCli");
        ParserSaxResultPersonalData handler = null;
        try
        {
            String message = sendXmlToPSSCPersonalData(cli);
            ActivityLogger.debug("MESSAGE -->" + message);
            String xmlString = sendMessagePersonalData(message);
            handler = parserXmlRitornoFromPSSC(xmlString);

            if (!OK_SUCCESS_RESULT_CODE.equalsIgnoreCase(handler.getResult()))
            {
                return new NullIPTVProfile();
            }
            return handler.getIptvProfile();
        }
        catch (Exception ex)
        {
            ActivityLogger.error("Exception ===" + ex);
            NullIPTVProfile niptvProfile = new NullIPTVProfile();
            return niptvProfile;
        }

    }

    @Override
    public boolean modifyProfileWith(IPTVProfile profile)
    {
        String payload = 
            "<Payload>" +
                "<ProductOfferingID>SetIPTVPersonalData</ProductOfferingID>" +
                "<CharacterizedBy>" +
                    "<ofInterestTo>" +
                        "<CLI>" + StringUtils.leftPad(profile.cli(), 12, '0') + "</CLI>" +
                        "<pc_level>" + profile.getPcLevel() + "</pc_level>" +
                        "<pc_pin>" + profile.getPcPin() + "</pc_pin>" +
                        "<purchase_pin>" + profile.getPurchasePin() + "</purchase_pin>" +
                        "<pin_remember></pin_remember>" +
                    "</ofInterestTo>" +
                "</CharacterizedBy>" +
            "</Payload>";   

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(timeProvider.now().getTime());

        ActivityLogger.debug("Invoking " + SET_IPTV_OPERATION_TYPE + " on psscOrderEntryRequest with payload:" + payload);
        String resultCode = psscOrderExecutor.psscOrderEntryRequest(SET_IPTV_OPERATION_TYPE, payload, timeStamp);
        
        ActivityLogger.debug("Response from PSSC is: " + resultCode);
        return OK_ACK_RESULT_CODE.equals(resultCode);
    }
    
    private String sendMessagePersonalData(String message)
    {
        OutputStream os = null;
        PrintWriter pw = null;
        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(urlHttpForFinder);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-length", Integer.toString(message.length()));
            conn.setRequestProperty("Content-type", "text/xml");
            os = conn.getOutputStream();
            pw = new PrintWriter(os);
            pw.print(message);
            pw.flush();
            ActivityLogger.info("RISULTATO FINALE===" + conn.getResponseCode());

            InputStream bodyInputStream = conn.getInputStream();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(bodyInputStream));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = rdr.readLine()) != null)
            {
                sb.append(line);
            }
            ActivityLogger.info("sb.toString()===" + sb.toString());
            return sb.toString();
        }
        catch (Exception ex)
        {
            ActivityLogger.error("Exception rispostaServizioPurchaseList --> " + ex);
            return null;
        }
        finally
        {
            try
            {
                if (pw != null)
                {
                    pw.close();
                }
            }
            catch (Exception ex)
            {
            }

            try
            {
                if (os != null)
                {
                    os.close();
                }
            }
            catch (Exception ex)
            {
            }

            try
            {
                if (conn != null)
                {
                    conn.disconnect();
                }
            }
            catch (Exception ex)
            {
            }
        }
    }


    private String sendXmlToPSSCPersonalData(String cli) throws ParserConfigurationException, Exception
    {
        GetPayload pLoad = new GetPayload();
        pLoad.getXmlToIPTV(PERSONAL_DATA_ACTION, cli, null);
        return pLoad.serialize();
    }

    private ParserSaxResultPersonalData parserXmlRitornoFromPSSC(String xmlString) throws SAXException, ParserConfigurationException, Exception
    {
        ParserSaxResultPersonalData handler = new ParserSaxResultPersonalData();
        Reader reader = new StringReader(xmlString);
        InputSource inputSource = new InputSource(reader);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(inputSource, handler);
        return handler;
    }
}
