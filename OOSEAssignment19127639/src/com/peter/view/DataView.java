package com.peter.view;


import com.peter.exceptions.InvalidArgException;
import com.peter.exceptions.InvalidRouteException;
import com.peter.model.Model;
import com.peter.model.Route;

import java.util.Scanner;

public class DataView
{

    /*Purpose: Takes in all input from user, gives it to model/controllers to figure out. Also
    * receives strings to output from the model/other classes.
    * Cohesion: Handle input/output and communicate with controller/model.
    * Coupling: Moderate to high, in terms of amt of classes that know it. Because it is the view,
    * and because of the specific software structure I have done the view needs to know of the existence
    * of multiple classes in order to do it's job. This could probably be fixed by seperating the
    * concerns/cohesion of the class but it is the view, and it's purpose is quite broad.
    * Errors: Only errors are to do with input; and they are handled by Model. */
    private Model model;

    /*Dependency Injection :D */
    public DataView(Model m)
    {
        model = m;
    }

    /*Purpose: Display the default view to the user and then Display Menu. Public because
    * it needs to be called from whatever system state is currently in the system.*/
    public void displayView()
    {

        displayDataMode();
        displayMenu();

    }

    /*Purpose: Display Menu. Public because sometimes we just want the menu, so this to be called
    * and not the displayDataMode(). */
    public void displayMenu()
    {
        System.out.println("Please enter input: 1 to download, go to track, 3 to exit, 'details' to display route details ");
        Scanner sc = new Scanner(System.in);
        String optionNumber = sc.nextLine();
        try {

            switch (optionNumber) {
                case "1":
                    System.out.println("Normal Display");
                    downloadUpdated();
                    displayView();
                    break;
                case "go":
                    getTracking();
                    break;
                case "3":
                    System.exit(0);
                    
                    break;
                case "details":
                    System.out.println("Please input the route name");
                    Scanner s = new Scanner(System.in);
                    String st = s.nextLine();
                    displayDetails(st);
                    break;
                default:
                    throw new InvalidArgException("Invalid argument",new Throwable());
            }
        }
        catch(InvalidArgException e)
        {
            System.out.println("The arguments you entered were incorrect, try again");
            displayMenu();
        }
    }

    /*Purpose: Tell model to switch states, and provide it with the input. */
    public void getTracking() throws InvalidArgException
    {
        try {
            System.out.println("Tracking");
            System.out.println("Please input the route name");
            Scanner sa = new Scanner(System.in);
            String str = sa.nextLine();
            Route routeToTrack = model.findRoute(str);
            //Eror checking later.
            startTracking(routeToTrack);
        }
        catch (InvalidRouteException e)
        {
            throw new InvalidArgException("Arguments are invalid, please try again", e);
        }
        catch (NullPointerException e)
        {
            throw new InvalidArgException("Arguments are invalid, please try again", e);
        }
    }

    private void startTracking(Route r)
    {
        model.switchToTracking(r);
    }

    /*Purpose: Method to display details. Display basic details, get the detailed output, display that
    * then display menu.*/
    private void displayDetails(String st)
    {
        try
        {
            System.out.print(" ");
            System.out.print("Detailed display for route: " + st);
            String detailedOutput = model.findRoute(st).toStringDetailed();
            System.out.println(detailedOutput);
            displayMenu();
        }
        catch (InvalidRouteException e)
        {
            System.out.println("Error");
        }
    }

    public void downloadUpdated()
    {
        model.updateModel();
    }

    /*Purpose: Default display method. Displays the default view as instructed by the specification.*/
    private void displayDataMode()
    {

        //model.getProcessedRoutes();
        model.calcTotalDistance();
        //fix this up later, issue is you cannot call it once, you gotta iterate through it here , you know this shit!!!
        //System.out.println(model.calcTotalDistance());
    }

}
