package it.matrix.alicehometv.util;

import java.io.*;

public class StreamReader
{
    public String readFrom(InputStream stream, String charSet) throws IOException 
    {
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(stream, charSet));

        StringBuilder collectedChars = new StringBuilder(); 
        char[] buffer = new char[4096];
        int numberOfReadChars = 0;
        try
        {
            while ((numberOfReadChars = responseReader.read(buffer)) != -1)
                collectedChars.append(new String(buffer, 0, numberOfReadChars));
        }
        finally
        {
            responseReader.close();
        }

        return collectedChars.toString();
    }
}
