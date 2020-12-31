package com.peter.model;

public class Waypoint extends Point
{

    private double lat;
    private double lon;
    private String s;
    private double alt;

    /*Dependency Injection*/
    public Waypoint(String sTest)
    {
        super(sTest);
        s = sTest;
    }

    /*Purpose: Waypoint is a child so when getChild() is called by route, we return this/the waypoint.*/
    @Override
    public Node getChild() {
        return this;
    }

    /*Getters + Setters.*/
    public void setLat(double l){lat = l;}
    public void setLong(double l){lon = l;}
    public void setAlt(double l){alt = l;}
    public double getLat(){return lat;}
    public double getLong(){return lon;}
    public double getAlt(){return alt;}
    public String getS(){return s;}
    public void setAll(double lat, double longitude,double alt)
    {
        this.setAlt(alt);
        this.setLong(longitude);
        this.setLat(lat);
    }

    /*Purpose: Simple toString.*/
    @Override
    public String toString()
    {
        return "Waypoint: " + lat + " " + lon + " " + alt;
    }

    /*TODO possibly remove later.*/
    @Override
    public boolean isWaypoint()
    {
        return true;
    }

    /*Waypoint is not a segment, so instead of returning it just throw an unsupportedoperationexception.*/
    @Override
    public Node getChildSegment() {
        throw new UnsupportedOperationException();
    }

    /*Both these printing methods purpose are testing related only. All real printing to user
     * is done through the view.*/
    @Override
    public void printInfo()
    {

        System.out.println("Waypoint: " + lat + " " + lon + " " + alt + " " + super.isVisited());

    }

    @Override
    public void printOnlySeg()
    {

    }
}
