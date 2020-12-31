package com.peter;

import com.peter.model.Route;

public class RouteApplication
{
    /*Purpose: Represents the internal state of the application/system. State here is system
    * state as the system is either tracking or not tracking/displaying.
    * State Pattern*/
    private Route trackingRoute;
    private AppState applicationState = new DataMode();

    /*Purpose: Set state of the route application.*/
    public void setRouteAppState(AppState updatedState)
    {

        applicationState = updatedState;

    }

    /*Purpose: Run the run() method of whatever state is currently assigned.*/
    public void run()
    {
        applicationState.run(this);
    }

    /*Purpose: Getter for the route that has been set by the display state.*/
    public Route getRoute()
    {

        return trackingRoute;

    }

    /*Purpose: Setter for the route that has been obtained by the display state and
    is going to be used by the tracking state.*/
    public void setRoute(Route r)
    {
        trackingRoute = r;
    }
}
