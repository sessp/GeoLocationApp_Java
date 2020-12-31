package com.peter;

import com.peter.model.Waypoint;

public class VerticalDescent implements Measurement
{

    /*Purpose: Strategy Pattern implementation responsible for measuring the vertical
     * descent distance between 2 points. Used by other classes. */
    @Override
    public double measure(Waypoint w1, Waypoint w2)
    {
       double distance = 0.0;

       if (w1.getAlt() - w2.getAlt() > 0.0)
       {
               distance = Math.abs((w1.getAlt() - w2.getAlt()));
       }

       return distance;
    }
}
