package com.peter.model;

public class Segment extends Point
{

    /*Purpose: Another model class, this one is for the leaf node segment. It is a type of point.
    * Coupling: Low, simple association with a start and end node.
    * Error handling: Again, all errors possible here would be thrown at reading/construction time,
    * so we'll let the class that does the reading/construction handle that, StringReaderClass. */
    private String description;
    private Node startW;
    private Node endW;
    private boolean hasStart = false;
    private boolean hasEnd = false;
    private boolean segVisited = false;
    //TODO remove segVisited. Also possibly remove hasStart/hasEnd + all their related methods?

    /*Dependency Injection*/
    public Segment(String sTest, String s)
    {
        super(sTest);
        description = s;
    }

    public void setDescription(String s){description = s;}
    public String getDescription(){return description;}

    public Node getStartW(){return startW;}
    public Node getEndW(){return endW;}

    /*Purpose: Get all the waypoints, used in iterator. This is not a waypoint, so return
    * exception for unsupportedoperation. Again, refer to report for design reasons as to why
    * i do it like this.*/
    @Override
    public Node getChild() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node getChildSegment() {
       return this;
    }

    /*All other methods are just getters/setters. No real comments to make.*/

    public void setStartW(Node w)
    {
        startW = w;
    }
    public void setEndW(Node w)
    {
        endW = w;
    }

    @Override
    public boolean isSegment() {
        return true;
    }

    @Override
    public String toString()
    {
        return "Segment: " + getDescription();
    }

    public String toStringDetailed(){return "Segment: " + getDescription() + " " + startW.toString() + " " + endW.toString();}

    /*Both these printing methods purpose are testing related only. All real printing to user
    * is done through the view.*/
    @Override
    public void printInfo()
    {

        System.out.println("Segment: " + description + " " + startW + " " + endW);
    }

    @Override
    public void printOnlySeg()
    {

        System.out.println("Segment: " + description + " " + startW + " " + endW);
    }

    public boolean hasBE()
    {
       return hasEnd;
    }

    public boolean hasBS()
    {
       return hasStart;
    }


}
