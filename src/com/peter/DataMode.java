package com.peter;

import com.peter.exceptions.FatalException;
import com.peter.model.Model;
import com.peter.model.Route;
import com.peter.view.DataView;


public class DataMode implements AppState
{

    /*Purpose: The main mode of the application, the default mode.
    * Cohesion: Responsible for setting up/initiating all aspects of the system to do
    * with the initial data displaying mode; facilitates the reading of the data, the creation
    * of the model, creation of the view and then lets the view/model/controllers do the rest.*/
    private RouteApplication sys;

    /*Purpose: Overrided run method called by the RouteApplication when a state switch occurs.
    * Specifically this method, when called, loads the current string, sets up the data structure,
    * supplies that to the model, creates a view based off the model and then finally displays the view.
    * This method has high coupling/bad in terms of testability but this is essentially the start up
    * for the apps data mode, so it is only called once/twice in its entire lifetime. */
    @Override
    public void run(RouteApplication app)
    {
        try
        {
        sys = app;
        StringReaderController src = new StringReaderController();
        src.setupData();
        //handle better later
        Model modelController = new Model(src.getDownloadedInfo(),src,this);
        DataView v = new DataView(modelController);
        v.displayView();
        }
        catch(FatalException e)
        {
            this.run(sys);
        }
    }

    /*Purpose: Method that switches mode to tracking mode. Public so the model can tell
    * this class, based off some route inputted supplied by the view.
    * Not the best coupled method but it gets the job done with moderate coupling occuring.*/
    public void changeMode(Route r)
    {
        sys.setRouteAppState(new TrackingMode());
        sys.setRoute(r);
        sys.run();
    }

    public void retry() 
    {
    
        this.run(sys);
    
    }
}
