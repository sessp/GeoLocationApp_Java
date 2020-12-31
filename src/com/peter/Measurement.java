package com.peter;

import com.peter.model.Waypoint;

public interface Measurement
{

    /*Purpose: Interface/main strategy method that the strategies for calculating measurement
    * implement. */
    public double measure(Waypoint w1, Waypoint w2);

}
