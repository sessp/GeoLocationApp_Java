package com.peter;

import com.peter.model.Route;
import com.peter.model.Segment;

import java.util.ArrayList;

public class RemainingWaypointDistanceCalc extends DistanceTemplate
{
    //TODO Check if this is used or not ect.
    @Override
    protected ArrayList<Segment> getNode(Route r)
    {
        ArrayList<Segment> s = new ArrayList<Segment>();
        s.add(r.getCurrentSeg());
        return s;
    }
}
