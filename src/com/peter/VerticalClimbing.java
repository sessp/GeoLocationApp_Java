package com.peter;

import com.peter.model.Waypoint;

public class VerticalClimbing implements Measurement
{

    /*Purpose: Strategy Pattern implementation responsible for measuring the vertical
    * climbing distance between 2 points. */
    @Override
    public double measure(Waypoint w1, Waypoint w2)
    {
        double distance = 0.0;
        if(w2.getAlt() - w1.getAlt() > 0.0)
        {
            distance = w2.getAlt() - w1.getAlt();
        }



        return distance;
    }
}
