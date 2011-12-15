package it.matrix.alicehometv.util.test;

import java.util.List;

public class ListUtils
{
    public static <E> E lastElementOf(List<E> aList)
    {
        return aList.isEmpty() ? null : aList.get(aList.size() - 1);
    }
}
