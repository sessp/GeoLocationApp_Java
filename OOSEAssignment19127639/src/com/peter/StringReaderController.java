package com.peter;

import com.peter.exceptions.FatalException;
import com.peter.exceptions.InvalidArgException;
import com.peter.model.Route;
import com.peter.model.Segment;
import com.peter.model.Waypoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StringReaderController
{
    /*Purpose: Responsible for reading in the string that is retrieved and constructing
    * the composite data structure.
    * Cohesion: Responsible for creating the data based off of the string. Part of that means
    * initialising the routes as in order to create the composite structure that needs to be done here.
    * E.G the route.setupSegList() ect.
    * Coupling: No real coupling, doesn't know too much about other classes. Minimal.*/
 private Segment seg;
 private String stringToProcess;

    private Map<String,Route> routes;

    public StringReaderController()
    {
        routes = new HashMap<String,Route>();
    }

    /*Purpose: Called by the current state to setup the data. If there is an error with the formatting
    * or some other exception, handle it.*/
    public void setupData() throws FatalException
    {
        try
        {
            stringToProcess = downloadString();
            seperateString();
        }
        catch(InvalidArgException e){throw new FatalException("A fatal Error occured",e);}
        catch(IOException e1){throw new FatalException("A fatal Error occured",e1);}
    }

    /*Purpose: download the string from the GeoUtilsStub/retrieveRouteData()*/
    private String downloadString() throws IOException
    {
        GeoUtilsStub gus = new GeoUtilsStub();
        return gus.retrieveRouteData();
    }

    /*Purpose: Preread the number of routes, for testing purposes.*/
    private int getRoutes()
    {
        int numR = 0;
        String[] lines = stringToProcess.split("\\n");
        for (String l: lines)
        {
            if((l.trim().length() > 0) && (!l.contains("-")))
            {
                String p[] = l.split(" ",2);
                Route r = new Route();
                r.setName(p[0]);
                r.setDescription(p[1]);
                routes.put(p[0],r);

                numR++;
            }
        }
        System.out.println(String.format("There are %d Routes", numR));
        return numR;
    }

    /*Purpose: used for testing purposes.*/
    public void test()
    {
        for (Map.Entry<String,Route> r:routes.entrySet())
        {
            System.out.println(r.getValue().toString());
        }
    }

    /*Purpose: Read/Parse the information in the string and construct it
    * into a composite datastructure.*/
    private void seperateString() throws InvalidArgException
    {
        ValidationController vc = new ValidationController();
        getRoutes();
        test();
        String currentRoute = "";
        boolean isSubroute = false;
        ArrayList<String> listOfSubRoutes = new ArrayList<String>();

        Waypoint w1 = new Waypoint("W");
        Waypoint w2 = new Waypoint("E");
        Waypoint w3 = new Waypoint("E");
        Route r = new Route("R","R");

        String[] routez = stringToProcess.split("\\n");
        /*Split the string up into several lines*/
        for (String s: routez) {
            if ((s.trim().length() > 0) && (!s.contains("-"))) {
                //If line is a new route.
                isSubroute = false;
                String p[] = s.split(" ", 2);

                currentRoute = p[0];


            }
            if (s.contains("-")) {

                String[] way = s.split(",", 4);
                if (way.length == 3)
                {
                    //If line is an end waypoint/contains no segment.
                    /*Parse and create data*/
                    w2 = new Waypoint("End");
                    double d1 = Double.parseDouble(way[0]);
                    double d2 = Double.parseDouble(way[1]);
                    double d3 = Double.parseDouble(way[2]);
                    vc.validateWaypoint(d1, d2, d3);
                    w2.setLat(d1);
                    w2.setLong(d2);
                    w2.setAlt(d3);
                    /*Add to currentRoute*/
                    routes.get(currentRoute).addNode(w2);
                    /*If line is the end of a subroute then create a segment, that will
                    * be linked to the super route later.*/
                    if(isSubroute == true)
                    {
                        seg = new Segment("S", "");
                        seg.setEndW(w2);
                        routes.get(currentRoute).addNode(seg);
                        isSubroute = false;
                    }

                }
                if (way.length == 4) {
                    if (way[3].charAt(0) == '*') {
                        //If line contains a subroute.
                        isSubroute = true;
                        w1 = new Waypoint(way[3]);
                        double dd1 = Double.parseDouble(way[0]);
                        double dd2 = Double.parseDouble(way[1]);
                        double dd3 = Double.parseDouble(way[2]);
                        w1.setLat(dd1);
                        w1.setLong(dd2);
                        w1.setAlt(dd3);
                        vc.validateWaypoint(dd1, dd2, dd3);
                        Route subRoute = new Route("R", way[3]);
                        /*Create a segment which will be linked later*/
                        seg = new Segment("S", "");
                        seg.setStartW(w1);

                        /*Get rid of the * in the name and add it to the composite structure.*/
                        StringBuilder sb = new StringBuilder(way[3]);
                        sb.deleteCharAt(0);
                        String updated = sb.toString();

                        routes.get(currentRoute).addNode(w1);
                        routes.get(currentRoute).addNode(seg);
                        listOfSubRoutes.add(way[3]);
                        routes.get(currentRoute).addNode(routes.get(updated));

                    } else {
                        //It's just a normal waypoint.
                        w1 = new Waypoint("j");
                        double dd1 = Double.parseDouble(way[0]);
                        double dd2 = Double.parseDouble(way[1]);
                        double dd3 = Double.parseDouble(way[2]);
                        vc.validateWaypoint(dd1, dd2, dd3);
                        w1.setLat(dd1);
                        w1.setLong(dd2);
                        w1.setAlt(dd3);
                        seg = new Segment("S", way[3]);

                        seg.setStartW(w1);
                        routes.get(currentRoute).addSeg(seg);

                        routes.get(currentRoute).addNode(w1);

                        routes.get(currentRoute).addNode(seg);
                    }
                }
            }
            if (s.trim().length() == 0) {
            }
        }

        for (Map.Entry<String,Route> routeEntry:routes.entrySet())
        {
            routes.get(routeEntry.getKey()).setupWayList();
            routes.get(routeEntry.getKey()).setupSegList();
        }

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

    }

    public Map<String,Route> getDownloadedInfo(){return routes;}


    /*Purpose: Method is used for testing purposes.*/
    public void printRoutes()
    {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        for (Map.Entry<String,Route> r:routes.entrySet())
        {
            r.getValue().printInfo();
        }
    }
}
