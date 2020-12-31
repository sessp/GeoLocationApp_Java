package com.peter;

import com.peter.exceptions.InvalidArgException;
import com.peter.model.Node;
import com.peter.model.Route;
import com.peter.model.Waypoint;
import com.peter.view.DataView;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GpsLocationStub
{
    /*Purpose: Stub for the GPSLocation class. All of this isn't really relevant to the specification,
    * only that it makes the rest of the program functional, in terms of tracking, and if this is
    * replaced with the actual GPSLocation it can be assumed that the program will still run.
    * Coupling: Needs to keep a reference of the journey controller, as that handles all the
    * logic for the controlling and this just updates the current point. Also needs to keep a reference
    * of the GpsLocationImp/whatever class has the locationReceived() method.*/
    private JourneyController jc;
    private boolean firstRun = true;
    private GpsLocationImp gpsLocation;
    class InnerTask extends TimerTask
    {
        @Override
        public void run()
        {
            boolean finished = false;
            if(jc.isEnded() == true)
            {
                Waypoint currentPosition;
                if(firstRun == true)
                {
                    /*Basically if this is the first run/first time executing set the
                    * current users position as the starting waypoint. Used for simplicity reasons
                    * as the distance between a point 0,0,0 and my test data would be a massive number
                    * and we would be here forever waiting for the random generator to generate waypoints
                    * closer. */

                    currentPosition = (Waypoint) jc.getCurrentRoute().getStart();
                    firstRun = false;
                    //set the first waypoint as the starting position here!
                }
                else
                {
                    /*If this isn't first run then the current position is whatever the journey controller
                    * has for the trekkers position. */
                    currentPosition = jc.getTrekkerPosition();
                }
                Waypoint nextW = (Waypoint) jc.getNextWaypoint();
                

                /*TODO tracking view here!*/
                String input = jc.getInput();
                Waypoint c = updateFactory(input,currentPosition,nextW);
                /*Does the user want the GPSLocation to get the point
                * or do they want to manually enter a point? */

                jc.display("******************************Location Updated******************************");
                jc.display("GPS parses the following coords: " + c.toString());
                jc.display("******************************Location Updated******************************");
                gpsLocation.locationReceived(c.getLat(), c.getLong(), c.getAlt());

            }
            else
            {
                finished = true;

                jc.display("Trek is completed.");
                cancel();
            }
            if(finished == true)
            {
                /*If finished tell jc and that will handle the rest.*/
                jc.journeyFinished();

            }


        }
    }

    /*Factory method responsible for creating the waypoint and passing it to the calling method
    * based on input as auto or man to represent automatic and manual, in the specification.*/
    private Waypoint updateFactory(String input, Waypoint current, Waypoint next)
    {
        Waypoint w = new Waypoint("W");
        if(input.equals("auto"))
        {
            /*If automatic, get the user position from the GPSLocation, or in this case
            * as a stub; randomly generate a point between the users position and next waypoint.*/
            w = generateRandom(current,next);
        }
        else if(input.equals("man"))
        {

            /*Or let the user indicate said point.*/
           w = indicateWaypoint();

        }
        else
        {
            //TODO Handle Error/Exception/Just do something.
        }
        return w;
    }

    /*Purpose: Simple method that returns a valid, existing waypoint. Or throws exception
    * if not valid.*/
    private Waypoint indicateWaypoint()
    {
        Waypoint custom = new Waypoint("R");
        try
        {
            ValidationController vc = new ValidationController();
            Scanner s = new Scanner(System.in);
            jc.display("Please enter your latitude");
            double lat = jc.getInputN();
            jc.display("Please enter your longitude");
            double lon = jc.getInputN();
            jc.display("Please enter your Altitude");
            double alt = jc.getInputN();
            vc.validateWaypoint(lat,lon,alt);
        
            custom = jc.findWay(lat,lon,alt);
        }
        catch(InvalidArgException e)
        {
             jc.display(e.getMessage());
             updateFactory("auto",jc.getTrekkerPosition(),(Waypoint)jc.getNextWaypoint());
        }
        

        return custom;
    }


    /*Purpose: This is essentially my stub, although the entire class is technically,
    * this generates the users position and acts as GPSLocation, returning the point to the
    * timer task which then passes that to locationReceived() in GpsLocationImp*/
    private Waypoint generateRandom(Waypoint currentPosition, Waypoint nextW)
    {
        Waypoint random = new Waypoint("R");
        Random rLat = new Random();
        double lat = (nextW.getLat() - currentPosition.getLat()) * rLat.nextDouble() + currentPosition.getLat();

        Random rLong = new Random();
        double lon = (nextW.getLong() - currentPosition.getLong()) * rLong.nextDouble() + currentPosition.getLong();

        Random rAlt = new Random();
        double alt = (nextW.getAlt() - currentPosition.getAlt()) * rAlt.nextDouble() + currentPosition.getAlt();

        random.setLat((Math.floor(lat * 1e4) / 1e4));
        random.setLong((Math.floor(lon * 1e4) / 1e4));
        random.setAlt((Math.floor(alt * 1e4) / 1e4));


        return random;
    }

    private Route route;
      public GpsLocationStub(JourneyController j)
      {
          jc = j;
          route = j.getCurrentRoute();
          //TODO sort out indirect dependency :)
      }

      /*Purpose: Method is responsible for creating timer task objects
      * and in essence the entire tracking functionality, in terms of actually tracking.
      * We only want to create 1 as multiple ones resulted in concurrency errors during testing,
      * and i've had enough of OS and shared data accessing for 1 semester. */
      public void generateValues()
      {
          boolean createdTask = false;

          gpsLocation = new GpsLocationImp(jc);
          while(jc.isEnded() == true)
          {
              if(createdTask == false)
              {
                  System.out.println(" ");
                  System.out.println("--------------------GPS Tracking System Activated!--------------- ");
                  System.out.println(" ");
                  InnerTask task = new InnerTask();
                  Timer t = new Timer(true);
                  t.schedule(task, 1000, 1000);
                  createdTask = true;

              }
          }

      }

}
