package com.peter;

import com.peter.model.Node;

import java.util.Iterator;
import java.util.Stack;

public class NodeIterator implements Iterator
{
    /*Purpose: An iterator for iterating through nodes. The inspiration for this class
    * was taken from the OOSE Book Head First Design Patterns and small tweaks were
    * made to support my software system. */
    private Stack<Iterator<Node>> s = new Stack<Iterator<Node>>();

    public NodeIterator(Iterator it)
    {
        s.push(it);
    }
    @Override
    public boolean hasNext()
    {
        if(s.empty())
        {
            return false;
        }
        else
        {
            Iterator<Node> i = s.peek();
            if(!i.hasNext())
            {
                s.pop();
                return hasNext();
            }
            else
            {
                return true;
            }
        }
    }

    @Override
    public Object next()
    {
        if(hasNext())
        {
            Iterator<Node> i = s.peek();
            Node n = i.next();

            s.push(n.createIt());
            return n;
        }
        else
        {
            return null;
        }
    }



}
