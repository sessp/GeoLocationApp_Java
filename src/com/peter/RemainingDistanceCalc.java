package com.peter;

import com.peter.model.Route;
import com.peter.model.Segment;

import java.util.ArrayList;

public class RemainingDistanceCalc extends DistanceTemplate
{

    /*Template hook method implementation for calculating the remaining distance.
    * Simply returns a list of the remaining segments in a route. */
    @Override
    protected ArrayList<Segment> getNode(Route r)
    {
        return r.getRemainingSegList();
    }
}
