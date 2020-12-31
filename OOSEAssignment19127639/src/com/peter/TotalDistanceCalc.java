package com.peter;

import com.peter.model.Route;
import com.peter.model.Segment;

import java.util.ArrayList;

public class TotalDistanceCalc extends DistanceTemplate
{
    /*Purpose: Hook method for the calculate distance template pattern method. Return
    * a list of all the segments in a route.*/
    @Override
    protected ArrayList<Segment> getNode(Route r)
    {
        return r.getSegmentList();
    }
}

