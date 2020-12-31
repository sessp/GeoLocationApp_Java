package com.peter;

import com.peter.model.*;
import com.peter.view.TrackingView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class JourneyController
{
    /*Purpose: Controller, handles all logic/calculations for tracking/when on a journey,
    * except what is actually displayed to the user. Essentially this is the backend of the tracking
    * functionality.
    * Coupling: High coupling to whatever route it is associated with. I call it association as a journey
    * isnt owned by a route but rather a journey is associated with several routes.
    * Errors: TODO write later.
    * Note: Journey is pretty much equivalent to Tracking. I just thought it sounded better.
    * User is on a journey rather than user is being tracked.*/
    private Route currentRoute;
    private Point currentWaypoint;
    private TrackingView view;
    private TrackingMode tm;
    private Waypoint trekkerPosition;
    private LinkedList<Segment> remainingSegs;
    private LinkedList<Waypoint> remainingWay;
    /*Needs reference to the current tracking mode so when the journey is complete it can
    * tell the tracking mode such and let it do the rest. Other fields are for the current
    * route being tracked, the current position of the trekker and a list of remaining segments
    * and waypoints. Why are they specific/downcasted/not a list of 'points'? Refer to report.
    * */

    /*Dependency Injection*/
    public JourneyController(Route r,TrackingMode t, TrackingView tv)
    {
        view = tv;
        tm = t;
        currentRoute = r;
        remainingSegs = new LinkedList<Segment>();
        remainingWay = new LinkedList<Waypoint>();
    }

    /*Method simply tells the tracking mode that it is finished. */
    public void journeyFinished()
    {
        tm.changeMode();
        //TODO rename method to reflect finished.
    }

    /*Purpose: Public method that is called by the tracking mode, basically starts tracking.
    * TrackingMode needs access to this method and hence it is public. Or else we would need
    * to have it in the constructor and we don't want this kind of complicated task in a constructor. */
    public void startTracking()
    {
        GpsLocationStub gls = new GpsLocationStub(this);
        gls.generateValues();
    }

    //Purposes: Simple getters/setters.
    public Route getCurrentRoute()
    {
        return currentRoute;
    }
    public Waypoint getPosition()
    {
        return trekkerPosition;
    }

    public Waypoint getTrekkerPosition()
    {
        return trekkerPosition;
    }

    /*Purpose: Public method called by TrackingMode to initialise the remaining segments. Again
    * this could be in the constructor and be a private method but i thought the task was
    * to complicated to be in the constructor and subsequently i made the method public.*/
    public void initTracking()
    {
        /*Set trekker position to the start waypoint of the route. Add segments to remaining
        * segments list, why? Because I don't want to edit the list in the route model class
        * as remaining segments is a list of remaining segments and not a list of all the segments
        * and since i need to perform modification on the remaining list we should make it seperate
        * from the list in route that keeps track of all the segments.*/
        trekkerPosition = currentRoute.getStart();
        view.displayString(" The trekkers current position: " + trekkerPosition.getLat() + trekkerPosition.getLong() + trekkerPosition.getAlt());
        view.displayString("The route has the following segments: ");
        for (Segment s:currentRoute.getSegmentList())
        {
            remainingSegs.add(s);
        }
        for(Segment s1:remainingSegs)
        {
           view.displayString(" " + s1.toStringDetailed());
        }
        getWaypoints();
    }

    /*Purpose: Get waypoints that are remaining and similarly to above add to a list. The
    * method itself iterates through the current routes waypoints, finds  a waypoint,
    * typecasts it to a waypoint and adds it to the list. Why downcasting/typecasting/throwing
    * an exception for functionality?? Refer to my report + original concept is from OOSE book. */
    public void getWaypoints()
    {

        view.displayString("Waypoints on the Routes: ");
        Iterator<Node> it = currentRoute.createIt();

        while(it.hasNext())
        {
            Node n = it.next();
            try
            {
                Waypoint nn = (Waypoint) n.getChild();
                view.displayString(nn.toString());
                remainingWay.add(nn);
            }
            catch (UnsupportedOperationException uoe){}
        }
    }

    /*Purpose: Set tracker position based off of double latitude, longitude and altitude.*/
    public void setPosition(double lat, double lon, double alt)
    {
        trekkerPosition.setLat(lat);
        trekkerPosition.setLong(lon);
        trekkerPosition.setAlt(alt);
    }

    /*Purpose: If a waypoint has been completed and that waypoint is at the end of a segment
    * then mark the segment as complete and update the list. Specifically this class is responsible
    * for updating the segment and updating the list. Determining if segment is complete is done by another
    * method, seperation of concerns.*/
    private void markSegAsCompleted(Segment s)
    {
        //System.out.println("Following segment was completed and has been removed: ");
        //s.printInfo();
        remainingSegs.remove(s);
        ArrayList<Segment> updatedSegListToParse = new ArrayList<Segment>();
        for(Node ss:remainingSegs)
        {
            updatedSegListToParse.add((Segment)ss);
        }
        currentRoute.updateRemainingSegList(updatedSegListToParse);
    }

    /*Purpose: Find a waypoint given latitude, longitude and altitude. Used mostly for
    * testing/custom waypoint setting validation.  Points don't have descriptions
    * or names so we can only really use the numbers to see if a point exists or not,
    * even if 2 waypoints could have a similar altitude/latitude/longitude.*/
    public Waypoint findWay(double la, double lo, double al)
    {
        Waypoint w = new Waypoint("W");
        for (Node n: remainingWay)
        {

            w = (Waypoint) n;
            if((Math.abs(la - w.getLat()) <= 0.00000001) && (Math.abs(lo - w.getLong()) <= 0.00000001) && (Math.abs(al - w.getAlt()) <= 0.00000001))
            {

                return w;
            }
            else
            {
                //TODO exception/error, no such waypoint.
            }

        }
        return w;
    }

    /*Purpose: Used for basically testing. Making sure the segments are being reached
    * as they are suppose to be and that the tracking logic is good. I'll leave it just in case
    * markers want to use it or i want to use it in the demonstration.*/
    public void checkRemainingSeg()
    {
        int segRemain = 0;
        for (Segment s:currentRoute.getRemainingSegList())
        {

            segRemain++;

        }
        System.out.println("***********************************");
        System.out.println("Segments Remaining: " + segRemain);
        System.out.println("***********************************");
    }

    /*Purpose: Getter for remaining segements.*/
    public LinkedList<Segment> getSegmentCopy()
    {
        LinkedList<Segment> s = new LinkedList<Segment>();
        s = (LinkedList<Segment>) remainingSegs.clone();
        return s;
    }

    /*Purpose: Check if the waypoint reached is the last/end waypoint in a segment.*/
    private void checkIfWayIsLastSegment(Node way)
    {
        for(Node n:getSegmentCopy())
        {
            Segment seg = (Segment) n;
            if(seg.getEndW().equals(way))
            {
                /*If it is, mark it as complete.*/
                //seg.printInfo();
                view.displayString("Congratulations, you completed segment: " + seg.toStringDetailed());

                markSegAsCompleted(seg);
            }
        }
    }

    /*Purpose: Retrieve next waypoint/waypoint at the end in the linked list.*/
    public Node getNextWaypoint()
    {
        return remainingWay.element();
        //First or last depending on the order.
    }

    /*Purpose: Called when user has reached a waypoint, and it has been validated.*/
    public Node reachedWaypoint()
    {
        Node n = new Point("Placeholder Node");
        /*If it isn't the last point; remove, output and check if that waypoint is end of a segment*/
        if(remainingWay.element() != null) {
            n = remainingWay.removeFirst();
            //TODO TrackingView
            view.displayString("Waypoint: " + n.toString() + " has been reached!");
            checkIfWayIsLastSegment(n);
        }
        else
        {
            view.displayString("You have reached the end of the route :( ");
        }
        return n;
    }

    /*Purposes: Set waypoint based on some input + Just set waypoint based on a point.*/
    public void setWaypoint(String input)
    {
        Point usersPoint = currentRoute.find(input);
        setWaypoint(usersPoint);
    }
    public void setWaypoint(Point p)
    {
        currentWaypoint = p;
    }

    /*Purpose: Check if it has ended and return boolean that represents if it has ended
    or not.*/
    public boolean isEnded()
    {
        boolean b = true;
        if(remainingWay.isEmpty() == true)
        {
            b = false;
        }
        else
        {
            b = true;
        }

        return b;
    }

    public Node getWaypoint()
    {
        Node w = new Waypoint("");
        if(0 < remainingWay.size())
        {
            //0 and 1
            //Change numbers to 1 later
            w = remainingWay.get(0);
        }
        return w;
    }

    /*Purpose: Return a segment that reflects a path between the users current position and the next
    * waypoint.*/
    public Segment getCurrentPath()
    {
        Segment currentSeg = new Segment("Current","Path between user and next waypoint");
        currentSeg.setStartW(trekkerPosition);
        currentSeg.setEndW(getWaypoint());
        //Possibly logic display issue there. 
        currentRoute.setCurrentSegment(currentSeg);
        currentRoute.getCurrentSeg();
        view.displayString(currentRoute.getCurrentSeg().toStringDetailed());
        return currentSeg;
    }
    
    public void display(String s)
    {
      view.displayString(s);
    }
    
    public String getInput()
    {
       return view.getModeForGPS();
    }
    
    public double getInputN()
    {
       return view.getNumber();
    }
}
