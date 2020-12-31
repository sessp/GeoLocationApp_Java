package com.peter;

import com.peter.model.Waypoint;

public class HorizontalDistance implements Measurement
{

    /*Purpose: Strategy pattern for calculating
    * Cohesion: Purpose is to calculate and return the horizontal distance between two points.
    * Used in conjuction with measurement strategy pattern, which in turn is used in template method
    * and other classes to calculate this for many points.
    * Errors: Validation and Exceptions are handled here in terms of validating and making sure
    * no harddrive erasure occurs.
    * Coupling: Limited/small. Coupled to GeoUtilsStub/GeoUtils as it needs to access the method
    * that calculates all of this.*/

    @Override
    public double measure(Waypoint w1, Waypoint w2)
    {
        double distance = 0.0;
        GeoUtilsStub gus = new GeoUtilsStub();
        distance = gus.calcMetresDistance(w1.getLat(),w1.getLong(),w2.getLat(),w2.getLong());
        //This method gives you the value but you actually have to add it to total it/use it.


        return distance;
    }
}
