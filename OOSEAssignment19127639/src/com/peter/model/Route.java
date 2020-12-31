package com.peter.model;

import com.peter.NodeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Route implements Node
{

    /*Purpose: Basic model class for the routes.
    * Errors: Similarly to point, all errors associated with this class are handled by the controllers
    * or the input reader/string reader controller.
    * Coupling: A few bits of aggregation with objects/classes like segment + node but overall
    * moderate coupling.
    * Notes: I know there are ALOT of lists/collections here; for justification look at the report.
    * It may not be apparent as to why there are this many but if you read the report i touch on it alot.*/

    private String s;
    private String description;
    private String name;
    private Iterator<Node> it = null;

    private List<Node> waypoints;
    private Segment currentSeg;
    private Node startW;
    private Node endW;
    private ArrayList<Segment> segList;
    private ArrayList<Segment> segmentList;
    private ArrayList<Segment> finalSList;
    private ArrayList<Segment> remainingSegList;
    private ArrayList<Node> wayList;
    boolean routeVisited = false;

    /*Dependency Injection; All direct dependencies are with standard java classes, which is allowed.*/
    public Route()
    {
        waypoints = new ArrayList<Node>();
        remainingSegList = new ArrayList<Segment>();
        segList = new ArrayList<Segment>();
        wayList = new ArrayList<Node>();
        currentSeg = new Segment("Start","Starting segment");
        segmentList = new ArrayList<Segment>();
        finalSList = new ArrayList<Segment>();
        description = "";
        name = "";
    }

    public Route(String sTest, String n)
    {
        startW = null;
        s = sTest;
        name = n;
        waypoints = new ArrayList<Node>();
        remainingSegList = new ArrayList<Segment>();
        segList = new ArrayList<Segment>();
        segmentList = new ArrayList<Segment>();
        wayList = new ArrayList<Node>();
        finalSList = new ArrayList<Segment>();
    }

    /*Purpose: Similar to point, used for finding point n.*/
    @Override
    public Point find(String n) {
        for(Node w : waypoints)
        {
            Point found = w.find(n);
            if(found != null)
            {
                return found;
            }
        }
        return null;
    }
    
    public Waypoint findWay(String description)
    {
        Waypoint w = new Waypoint("W");
        for (Node n: wayList)
        {

            w = (Waypoint) n;
            if(w.getS().equals(description))
            {
                System.out.println("We got ourselves a match!");
                return w;
            }
            else
            {
                System.out.println("No match :( ");
                //Error/Exception/Message
            }

        }
        return w;
    }
    

    /*Purpose: Mostly used for testing, no real purpose with the task at hand.*/
    @Override
    public void printInfo()
    {
        System.out.println(toString());
        for(Node w : waypoints)
        {
              w.printInfo();

        }
    }

    @Override
    public void printOnlySeg()
    {

        for(Node w : waypoints)
        {

        }
    }

    /*Purpose: Used in the iterate to gather all of the children that are segments.
    * This implementation of the method throws exception because this is not a segment.
    * If you want specific reasoning as to why I throw an exception refer to the report.
    * This is what the OOSE book approach was so i used it when getting all the children
    * and putting them into the appropriate lists.*/
    @Override
    public Node getChildSegment() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void resetVisited()
    {
        for(Node w : waypoints)
        {
               w.resetVisited();
        }
    }

    /*Purpose: Getters and setters for current segment, remaining segment list.*/
    public Segment getCurrentSeg()
    {
        return currentSeg;
    }
    public ArrayList<Segment> getRemainingSegList(){return remainingSegList;}
    public void setCurrentSegment(Segment s)
    {
        currentSeg = s;
    }
    public void updateRemainingSegList(ArrayList<Segment> s)
    {
        remainingSegList = s;
    }

    public void findWaypoints()
    {
        for(Node w : waypoints)
        {
            if(w.isWaypoint() == true)
            {
                Waypoint way = (Waypoint) w;
                way.printInfo();
            }
        }
    }

    /*Purpose: Create an iterator that iterates over all the routes children, including other routes.*/
    @Override
    public Iterator<Node> createIt() {
        /*if(it == null)
        {
            it = new NodeIterator(waypoints.iterator());
        }*/
        it = new NodeIterator(waypoints.iterator());
        return it;
    }

    @Override
    public Node getChild() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean isWaypoint() {
        return false;
    }

    @Override
    public boolean isVisited()
    {
        for (Node n:waypoints)
        {

            if(n.isVisited() == false)
            {
                return false;
            }

        }
        return true;
    }

    @Override
    public void markVisited()
    {
        routeVisited = true;
    }

    /*Purpose: Sets up waylist, a list of waypoint nodes. Why is it public and not private?
    * Because stringreadercontroller needs to call it in order to setup everything. I can't call it from
    * only within the route class as routes not associated with this route may not be initialised properly.*/
    public void setupWayList()
    {
        Iterator<Node> it = this.createIt();


        while(it.hasNext())
        {
            Node n = it.next();
            try
            {
                Node nn = n.getChild();
                wayList.add(nn);
            }
            catch (UnsupportedOperationException uoe){} //For now ignore the exception.
        }
    }


    /*Purpose: Identical to above except it gets segment nodes.*/
    public void setupSegList()
    {
        Iterator<Node> it = this.createIt();
        /*Iterate through nodes*/
        while(it.hasNext())
        {
            Node n = it.next();
            try
            {
                /*Downcasting is bad but refer to report as to why I do it a few times/limitations
                * of my chosen design.*/
                Segment nn = (Segment) n.getChildSegment();

                segmentList.add(nn);
            }
            catch (UnsupportedOperationException uoe){}
        }
        setupProperSegments();
        /*Setup the other end of the segments. As when reading the input you only
        * know the start waypoint for a segment and you don't know the end until you've read
        * the next line.*/
    }

    public ArrayList<Segment> getSegmentList()
    {
        return segmentList;

    }

    /*Purpose: Return the start point, used in tracking.*/
    public Waypoint getStart()
    {
        return (Waypoint)startW;
    }

    //public void printStart(){startW.printInfo();}
    //public void printEnd(){endW.printInfo();}
    public void setDescription(String s){description = s;}
    public void setName(String s){name = s;}
    public void addNode(Node n)
    {
        /*Instanceof bad!!!! Again refer to report for justification*/
        if((n instanceof Waypoint) && (startW == null))
        {
            startW = n;
            endW = n;
            //Would be the start and the end.
        }
        waypoints.add(n);
        if(n instanceof Waypoint)
        {
            endW = n;
        }
    }
    /*Purpose: Both these methods were used mostly for testing purposes.*/
    public void removeNode(Node n)
    {
        waypoints.remove(n);
    }
    public List<Node> getNodes()
    {
        return waypoints;
    }

    /*Purpose: ToString but for simple internal data. Used by view.*/
    public String toStringSimple()
    {
        return "Name: " + name + " " + " \n Route starts at: " + startW.toString() + " " + " and finishes at: " + endW.toString();
    }

    /*Purpose: ToString but for detailed internal data. Used by view.*/
    public String toStringDetailed()
    {
        /*Basically just build a string, go through all the nodes
        * get their detailed to strings and build one giant string and pass to caller,
        * in this case the view.*/
        String detailedString = "";
        detailedString = detailedString + " Description: " + description;
        detailedString = detailedString + "\n Segments:";
        for (Segment s:remainingSegList)
        {
            detailedString = detailedString + " \n " + s.toStringDetailed();
        }
        for (Node n:wayList)
        {
            detailedString = detailedString + " \n " + n.toString();
        }
        return detailedString;
    }

    @Override
    public String toString()
    {
        return name + " " + description;
    }

    /*TODO: remove seglist?!?!*/
    public void addSeg(Segment seg)
    {
        remainingSegList.add(seg);
        //SegList should be there.
    }

    /*TODO remove*/
    public void printSeg()
    {
    }

    /*TODO remove*/
    public void printSegmentList()
    {
        
    }

    /*TODO remove*/
    public void setupSegments()
    {
        System.out.println("Setting up segments in the ROUTE: " + name);
        System.out.println(" ");
        int firstIndex = 0;
        int secondIndex = 1;
        printSeg();
        for(Segment s : segList)
        {
            System.out.println("How many segments in segList: " + segList.size());
            if((firstIndex < wayList.size()) && (secondIndex < wayList.size()))
            {
                //issue is waypoints is segments and way, we just want way.
                System.out.println(wayList.get(firstIndex).toString());
                System.out.println(wayList.get(secondIndex).toString());
                s.setStartW(wayList.get(firstIndex));
                s.setEndW(wayList.get(secondIndex));
                firstIndex++;
                secondIndex++;
                s.printInfo();

            }
            System.out.println("Conditions firstIndex < wayList.size" + firstIndex + " < " + wayList.size());
            System.out.println("Conditions secondIndex < wayList.size" + secondIndex + " < " + wayList.size());
        }
    }


    /*Purpose: Setup the other end of a segment; as mentioned above because of the way
    * i did the reading of the downloaded string when reading we know the start waypoint
    * for a segment but not the end waypoint. This method figures out the end waypoint
    * and then updates the segment w/ that waypoint. */
    public void setupProperSegments()
    {
        int firstIndex = 0;
        int secondIndex = 1;

        Segment previousSegment = new Segment("Start","");
        for(Segment s : segmentList)
        {



            if(s.getEndW() == null)
            {
                if((firstIndex < wayList.size()) && (secondIndex < wayList.size()))
                {
                    //issue is waypoints is segments and way, we just want way.
                    //System.out.println(wayList.get(firstIndex).toString());
                    //System.out.println(wayList.get(secondIndex).toString());
                    //s.setStartW(wayList.get(firstIndex));
                    s.setEndW(wayList.get(secondIndex));
                    firstIndex++;
                    secondIndex++;
                    //s.printInfo();

                }
            }
            else
            {
                firstIndex++;
                secondIndex++;
                //System.out.println("index1: " + firstIndex);
                //System.out.println("index2: " + secondIndex);
            }
            if(s.getStartW() == null)
            {
                s.setStartW(previousSegment.getEndW());
            }
            /*
            if(s.getStartW() == null)
            {
                if((firstIndex < wayList.size()) && (secondIndex < wayList.size()))
                {
                    //issue is waypoints is segments and way, we just want way.
                    System.out.println(wayList.get(firstIndex).toString());
                    System.out.println(wayList.get(secondIndex).toString());
                    s.setStartW(wayList.get(firstIndex));
                    s.setEndW(wayList.get(secondIndex));
                    firstIndex++;
                    secondIndex++;
                    //s.printInfo();

                }
            }*/
            s.printInfo();
            previousSegment = s;
            /*
            if((firstIndex < wayList.size()) && (secondIndex < wayList.size()))
            {
                //issue is waypoints is segments and way, we just want way.
                System.out.println(wayList.get(firstIndex).toString());
                System.out.println(wayList.get(secondIndex).toString());
                s.setStartW(wayList.get(firstIndex));
                s.setEndW(wayList.get(secondIndex));
                firstIndex++;
                secondIndex++;
                s.printInfo();

            }*/
        }

        printSegmentList();
    }

    /*TODO remove*/
    public ArrayList<Segment> getSegList()
    {
        return segList;

    }

    @Override
    public boolean isSegment() {
        return false;
    }
}
