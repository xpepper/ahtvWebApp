package it.matrix.alicehometv.search;

import java.io.IOException;
import java.util.List;

public interface SearchExecutor 
{
    String runOn(List<SearchRequest> searchRequests) throws IOException;
}