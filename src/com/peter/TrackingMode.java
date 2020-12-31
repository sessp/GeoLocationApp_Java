package com.peter;

import com.peter.model.Route;
import com.peter.view.TrackingView;

public class TrackingMode implements AppState
{

    /*Purpose: Represents the tracking system state.
    * Coupling: Low coupling with the system as it needs the ability to tell the system
    * to switch states when the need arises.*/
    private RouteApplication sys;

    /*Purpose: Method is run when the tracking mode state is set. Start a journey, initialise the tracker
     * and start tracking.*/
    @Override
    public void run(RouteApplication app)
    {
        sys = app;
        TrackingView tv = new TrackingView();
        JourneyController jc = new JourneyController(app.getRoute(),this, tv);
        tv.displayString("-----Tracking mode now enabled-----");
        jc.initTracking();
        jc.startTracking();
    }


    /*Purpose: Simple method to change states from this state to the datamode state
    * and then finally run said state.*/
    public void changeMode()
    {
        sys.setRouteAppState(new DataMode());
        sys.run();
    }
}
