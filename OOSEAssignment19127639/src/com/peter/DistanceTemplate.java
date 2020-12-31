package com.peter;

import com.peter.model.Route;
import com.peter.model.Segment;
import com.peter.model.Waypoint;

import java.util.ArrayList;

public abstract class DistanceTemplate
{

    /*Purpose: Template Method for calculating the distance, whether remaining or total.
    * Coupling: None really, No lasting relationships; just a few objects that are only used
    * when the method is called. No obj knows of this classes existence other than the view and
    * journey controller, so minimal.
    * Errors: No errors can occur here.*/
    protected abstract ArrayList<Segment> getNode(Route r);
    /*Hook method that the other classes implement, because the distance calculation depends
    * on the collection of nodes. ie remaining calculation is done based on a collection of the
    * remaining nodes, and total calculation is done based on a collection of ALL nodes within the route.
    * So since the two takes are similar use template method pattern. */

    /*Purpose: Template method for this class. Calaculates the distance based on some collection of nodes.*/
    public double calculateDistance(Route r,Measurement m,String ss)
    {

        double distance = 0.0;

        ArrayList<Segment> segments = getNode(r);
        /*Get list of Nodes.*/

        for (Segment s:segments)
        {


            Waypoint start = (Waypoint) s.getStartW();
            Waypoint end = (Waypoint) s.getEndW();
            double distanceOfSegment = m.measure(start,end);
            distance = distance +  distanceOfSegment;


        }
        System.out.println("Total" + ss + "distance:" + distance);

       return distance;
    }

}
