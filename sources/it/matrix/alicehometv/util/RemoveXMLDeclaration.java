package it.matrix.alicehometv.util;

public class RemoveXMLDeclaration
{
    public static String on(String xmlString)
    {
        return xmlString.replaceAll("<\\?xml.version.+\\?>", "");
    }

}
