package com.peter.model;

import com.peter.DataMode;
import com.peter.DistanceController;
import com.peter.StringReaderController;
import com.peter.exceptions.FatalException;
import com.peter.exceptions.InvalidRouteException;

import java.util.Map;

/*
 * Cohesion/Purpose: This class is basically responsible for making sure the model is up to date + acting as a
 * link between the model and the view. It contains a map of the current routes, a string reader controller
 * for if the model needs updating and a reference to the current state, so that if need be it can
 * tell the current state to switch and supply the new state with the correct model data.
 *
 * Error Handling: No real errors can occur here and if they did it probably wouldn't even be handled by this.
 * Ie when updating the routes there could be an invalid format error but the src will handle that, ect.*/

public class Model
{
    private Map<String,Route> processedRoutes;
    private StringReaderController src;
    private DataMode dm;

    //simple constructor w/ dependency injection.
    public Model(Map<String,Route> r, StringReaderController rc, DataMode d)
    {
        dm = d;
        processedRoutes = r;
        src = rc;
    }

    //public Map<String,Route> getProcessedRoutes(){return processedRoutes;}

    /*Purpose: Facilitate the calculation of the total distance + pass it onto the calling method,
    * in this case the view.
    * Coupling: Medium coupling w/ distance controller. Model doesn't know the inner workings of DistanceController,
    * distancecontroller simply does its stuff and model gets the output and hands it over to the view.*/
    public String calcTotalDistance()
    {

        String output = "";
            for (String s:processedRoutes.keySet())
            {
                DistanceController dc = new DistanceController();
                String sToDisplay = dc.calcTotalDistance(processedRoutes.get(s));
                output = processedRoutes.get(s).toStringSimple() + " \n " + sToDisplay;
                System.out.println(output);
            }

        return output;
    }

    /*Used by calling obj/the view to request an updated version. Equivalent to a getter of sorts.*/
    public void updateModel()
    {
        requestUpdatedPoints();
    }

    /*Actually facilitates/initiates the updating. Private as the view doesn't need to know how
    * it is done, just that it gets an updated version.*/
    private void requestUpdatedPoints()
    {
        try
        {
           src.setupData();
           processedRoutes = src.getDownloadedInfo();
        }
        catch(FatalException e)
        {
            dm.retry();
        }

    }
    /*Purpose: Simple getter for the map, used in conjuction w/ the view
     to find a route based on user input */
    /*TODO this throws error if route doesnt exist, fix method signature, create exception ect.*/
    public Route findRoute(String routeName) throws InvalidRouteException
    {
        Route r = new Route();
        try
        {
          r = processedRoutes.get(routeName);
        }
        catch (NullPointerException e)
        {
            throw new InvalidRouteException("The route you entered is invalid!",e);
        }
        return r;
    }


    /*Purpose: Tells the DM to change mode, and supplies DM with the inputted route obtained from the view.*/
    public void switchToTracking(Route r)
    {
        dm.changeMode(r);
    }
}