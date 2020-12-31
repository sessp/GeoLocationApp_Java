package com.peter.model;

import java.util.Iterator;

public interface Node
{

    /*Purpose: Simple interface for composite pattern, talked about in report.*/
    Point find(String n);
    void printInfo();
    void printOnlySeg();
    void resetVisited();
    Iterator<Node> createIt();
    Node getChild();
    Node getChildSegment();
    boolean isWaypoint();
    boolean isSegment();
    boolean isVisited();
    void markVisited();
}
