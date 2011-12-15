package it.matrix.alicehometv.search;

import java.io.IOException;

public interface FASTInvoker
{
    String on(SearchRequest searchRequest) throws IOException;
}