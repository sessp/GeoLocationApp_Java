package com.peter.model;

import com.peter.NullIterator;

import java.util.Iterator;

public class Point implements Node
{

    /*Purpose: Superclass for the leaf nodes in my composite pattern design, for proper justification
    * look at the report.
    * Cohesion: Simple super leaf model class, with simple testing methods, getters + setters.
    * Errors: All errors that are possible here are handled by the string reader controller, as that is
    * where the data is created.
    * Coupling: No real coupling at all.*/

    /*S is for testing/marking purposes and serves no real purpose in regards with the task at hand.*/
    private String s;
    private boolean hasBeenVisited = false;
    private boolean pointVisited = false;

    /*Dependency Injection*/
    public Point(String sTest)
    {
        s = sTest;
    }

    /*Purpose: Simple find method used mostly for testing and not really relevant to task at hand.*/
    @Override
    public Point find(String n) {
        Point f = null;
        if(this.s.equals(n)){ f = this;}
        return f;
    }

    @Override
    public void printInfo()
    {

        System.out.println(s);

    }


    @Override
    public void printOnlySeg()
    {}

    @Override
    public boolean isVisited()
    {
        return pointVisited;
    }


    @Override
    public void markVisited()
    {

        pointVisited = true;

    }


    @Override
    public void resetVisited()
    {
        if(hasBeenVisited == true)
        {
            hasBeenVisited = false;
        }

    }

    /*Purpose: A null iterator, refer to my report for why i use this/justfication.
    * From a purpose point of view we don't want a point (waypoint/segment) to return an iterator
    * as they have nothing to iterate over, only a route does, so we get a point to return a null
    * iterator. From the OOSE Book.*/
    @Override
    public Iterator<Node> createIt()
    {
        return new NullIterator();
    }

    @Override
    public Node getChild() {
        return this;
    }

    @Override
    public Node getChildSegment() {
        return this;
    }


    @Override
    public boolean isWaypoint()
    {
        return false;
    }

    @Override
    public boolean isSegment() {
        return false;
    }

    /*All non-commented methods are just getters/setters so there is no real need to comment.*/
}
