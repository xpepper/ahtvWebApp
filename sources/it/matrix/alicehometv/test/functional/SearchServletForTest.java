package it.matrix.alicehometv.test.functional;

import it.matrix.alicehometv.servlet.SearchServlet;

@SuppressWarnings("serial")
public class SearchServletForTest extends SearchServlet
{
    @Override
    protected void configureFAST()
    {
        FAST_HOST = "wrapper.alice.it.master";
        FAST_PORT = 10015;
        FAST_WRAPPER_PATH = "/iptv/cgi/wrapper.cgi";
    }
}
