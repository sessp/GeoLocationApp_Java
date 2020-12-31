package com.peter;

import com.peter.model.Node;
import java.util.Iterator;

public class NullIterator implements Iterator
{
    /*Purpose: Null Iterator, used when certain nodes don't need to pass an actual
    * iterator and need to pass a null iterator instead.*/
    public Object next()
    {
        return null;
    }

    public boolean hasNext()
    {
        return false;
    }





}
