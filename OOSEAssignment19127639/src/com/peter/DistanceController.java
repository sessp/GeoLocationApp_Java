package com.peter;

import com.peter.model.Route;

public class DistanceController
{
    /*Purpose: Controller responsible for distance calculation, for the data mode.
    Cohesion: Facilitate the measurement of the total distance of some route r.
    Coupling: no real coupling is occuring other than to the measurement + template distance objects but that is required
    as it's part of the classes cohesion/responsibility.
    * TODO create a factory for this + enable polymorphism.*/

    public String calcTotalDistance(Route r)
    {
        double horiDistance = 0.0;
        double vertUpDistance = 0.0;
        double vertDownDistance = 0.0;
        Measurement m = new VerticalClimbing();
        Measurement m1 = new VerticalDescent();
        Measurement m2 = new HorizontalDistance();
        DistanceTemplate totalDistance = new TotalDistanceCalc();
        vertUpDistance = totalDistance.calculateDistance(r,m," Vertical Climbing ");
        vertDownDistance = totalDistance.calculateDistance(r,m1," Vertical Descent ");
        horiDistance = totalDistance.calculateDistance(r,m2," Horizontal ");
        String distance = " Total Vertical Climbing Distance: " + String.valueOf(vertUpDistance) + " " + " Total Vertical Decent Distance: " + String.valueOf(vertDownDistance) + " " + " Total Horizontal Distance: " + String.valueOf(horiDistance);
        return distance;
    }

}
