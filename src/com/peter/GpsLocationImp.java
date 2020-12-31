package com.peter;

import com.peter.model.Segment;
import com.peter.model.Waypoint;

public class GpsLocationImp
{

    /*Purpose: Uses the coordinates to update the gps location.
    * Cohesion: Purpose is to facilitate the tracking along the journey,
    * whever the location of the user is updated this method is called and this method updates
    * the application accordingly.
    * Coupling: Needs reference to the journey controller, as its functionality depends on it.*/
    private JourneyController jc;
    private double previousDistance = 100.0;
    /*This variable is used as a base maximum so at the start we can set any distance to
    * being the previous distance.*/

    /*Dependency Injection */
    public GpsLocationImp(JourneyController j)
    {
        jc = j;
    }

    /*Purpose: Purpose of this method is to update any relevant information/internal state
    * of the tracking functionality that relies on changes to the users location. E.G current position
    * There is a lot going on here so i've commented as best as i can in order to ensure you understand
    * what is happening.*/
    public void locationReceived(double latitude,double longitude,double altitude)
    {

        /*(0) Between this and the segment marked (1), all this code is done regardless of
        * whether the user has reached a waypoint, that is to say whether this method is called
        * the section between here and the comment marked (1) is also performed. */
        /*Received a new position, so update the journey controller with the new position
        * and get the next waypoint so we can do the next part.*/
        jc.setPosition(latitude,longitude,altitude);
        Waypoint nextW = (Waypoint) jc.getNextWaypoint();

        /*With the previous information we now have, that is we have position data and the
        * position of the next waypoint, we can now calculate distance. Create measurements,
        * calculate distances between user position and next waypoint and display.*/
        /*TODO create TrackingView and have that display all of this.*/
        GeoUtilsStub gus = new GeoUtilsStub();
        Measurement rwayDown = new VerticalDescent();
        Measurement rwayUp = new VerticalClimbing();
        Measurement rhori = new HorizontalDistance();
        double distance = gus.calcMetresDistance(latitude,longitude,nextW.getLat(),nextW.getLong());
        Segment userToNextWaypointSegment = jc.getCurrentPath();
        Waypoint userStartPositionInSegment = (Waypoint) userToNextWaypointSegment.getStartW();
        Waypoint wayPositionInSegment = (Waypoint) userToNextWaypointSegment.getEndW();
        double verticalDownRemaining = rwayDown.measure(userStartPositionInSegment,wayPositionInSegment);
        double verticalAscendingRemaining = rwayUp.measure(userStartPositionInSegment,wayPositionInSegment);
        double horizontalWaypointRemaining = Math.floor(gus.calcMetresDistance(userStartPositionInSegment.getLat(),userStartPositionInSegment.getLong(),wayPositionInSegment.getLat(),wayPositionInSegment.getLong()) * 1e4 / 1e4);
        jc.display("Remaining Vertical Descent Distance between user and next waypoint is:" + verticalDownRemaining + " meters");
        jc.display("Remaining Vertical Climbing Distance between user and next waypoint is:" + verticalAscendingRemaining + " meters");
        jc.display("Remaining Horizontal Distance between user and next waypoint is:" + horizontalWaypointRemaining + " meters");

        DistanceTemplate remainingDistanceCalc = new RemainingDistanceCalc();
        remainingDistanceCalc.calculateDistance(jc.getCurrentRoute(), rwayUp," Remaining VerticalClimbing ");
        remainingDistanceCalc.calculateDistance(jc.getCurrentRoute(), rwayDown, " Remaining VerticalDown");
        remainingDistanceCalc.calculateDistance(jc.getCurrentRoute(), rhori," Remaining Horizontal ");

        /*(1) this next code is to do with checking if that distance is > 0  x < 10 and is > 0 x < 2
        * If that is  the case it updates the journey controller. */
        if((distance < 10.0) && (verticalAscendingRemaining < 2.0))
        {
            /*TODO vertical distance > 2. just do it in validator.*/
            jc.display("---------------------------Waypoint Reached-------------------------------");
            jc.reachedWaypoint();
            jc.getCurrentPath();
            previousDistance = 100;
            jc.display("---------------------------Waypoint Reached-------------------------------");
        }
        else if(distance < previousDistance)
        {
            /*REMOVE THIS if you don't use my random stub
            /*this is to do more or less w/ how I have implemented my stub. This section should be removed if the real GPSLocation was implemented, 
            it wouldn't cause any functionality issues but it wouldn't really serve a purpose and would be useless as this is meant for the 
            random stub. By the next section I mean this else if block and the next one. 
            * The main purpose of this part is just to make sure that if the distance generated is closer to
            * the next waypoint then use that as the minimum for the random generation, or else we could be here
            * for years waiting for the random generator to generate a point that is close enough to the next waypoint.
            * So basically. lets say the GPSLocation generates and calls this method with point 1,2,3
            * and the next waypoint is 10,11,12. For simplicity sake lets pretend the distance is 20.
            * If the next point generated, lets say 3,4,5 has a distance of 15, then we set that as the users current waypoint
            * so that inthe GpsLocationStub it generates a point between the current user location, that being 3,4,5 and next waypoint
            * being 10,11,12 which results in a point that is even closer to 10,11,12 and this continues until the randomly generated
            * point is within the waypoint distance and then it updates the app and it starts back at the minimum.
            *
            * So long story short this section of code here is just making sure you aren't waiting here for years
            * waiting for the right random number to be generated, and as i said above this will all still work
            * with the proper implementation of GpsLocation, it just wouldn't do anything.
            *
            * For testing purposes you could comment the next two blocks out if you like, it would result
            * in more accurate functionality IF you used your own implementation of GPSLocation */
            Waypoint p = new Waypoint("S");
            p.setAlt(altitude);
            p.setLong(longitude);
            p.setLat(latitude);
            previousDistance = distance;
        }
        else if(Math.abs(distance - previousDistance) <= 0.00000001)
        {
             /*REMOVE THIS if you don't use my random stub*/
            /*This bit of code just makes sure the random generator doesn't generate the same point more
            * than a few times, because of how small the numbers are i am dealing with is sometimes the random
            * generator 'repeats' a point.*/
            jc.display("---------------------------Waypoint Reached-------------------------------");
            jc.setPosition(nextW.getLat(), nextW.getLong(),nextW.getAlt());
            jc.reachedWaypoint();
            jc.getCurrentPath();
            jc.display("---------------------------Waypoint Reached-------------------------------");
          
        }

    }

}
