package com.peter;

import com.peter.exceptions.InvalidArgException;

public class ValidationController
{
    /*Purpose: is responsible for validating all the various aspects of the systems
    * datatypes that need validating.
    * Coupling: Low/None, class is written so that the class doesn't care about other classes
    * and only the values they pass.
    * Cohesion: Responsible for handling all the validation checks. Doesn't handle throw errors,
    * it simply returns booleans to indicate if things are valid or not. It is up to the calling
    * class to throw exceptions were necessary.
    * */

    /*Purpose: Validate that the horizontal distance between two points is between 0 and 10 and
    * the vertical distance between two points is between 0 and 2. It doesn't know that they
    * are actually distances or what is using them, it just checks if some number is between
    * some range.*/
    public boolean checkWithinBounds(double horizontalDistance,double verticalDistance)
    {
        //System.out.println("Horizontal Distance = " + horizontalDistance);
        //System.out.println("Vert1cle Distance = " + verticalDistance);
        boolean isInBoundary = false;
        if((horizontalDistance >= 0.0) && (horizontalDistance <= 10.0))
        {
            if(((verticalDistance >= 0.0) && (verticalDistance <= 2.0)))
            {
                isInBoundary = true;
            }
        }


        return isInBoundary;
    }

    public void validateWaypoint(double lat, double lon, double alt) throws InvalidArgException
    {
        
        if((lat < -90.0) || (lat > 90.0))
        {
           throw new InvalidArgException("Latitude is out of bounds", new Throwable());
        }
        if((lon < -180.0) || (lon > 180.0))
        {
           throw new InvalidArgException("Longitude is out of bounds", new Throwable());
        }
    }


}