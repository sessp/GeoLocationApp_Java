package com.peter;

import java.io.IOException;

public class GeoUtilsStub
{
    /*Purpose: GeoUtils Stub, acts as a stub and means the GeoUtils class can replace this
    * and the program still remains functional. Reason the test strings/test cases are here is because
    * they are returned by the retrieveRouteData. In reality they would not be here.*/
    String download = "theClimb Amazing views!\n" +
            "-31.94,115.75,47.1,Easy start\n" +
            "-31.94,115.75,55.3,Tricky, watch for drop bears.\n" +
            "-31.94,115.75,71.0,I*feel,like.over-punctuating!@#$%^&*()[]{}<>.?_+\n" +
            "-31.93,115.75,108.0,Getting there\n" +
            "-31.93,115.75,131.9\n" +
            "mainRoute Since I was young\n" +
            "-31.96,115.80,63.0,I knew\n" +
            "-31.95,115.78,45.3,I'd find you\n" +
            "-31.95,115.77,44.8,*theStroll\n" +
            "-31.94,115.75,47.1,But our love\n" +
            "-31.93,115.72,40.1,Was a song\n" +
            "-31.94,115.75,47.1,*theClimb\n" +
            "-31.93,115.75,131.9,Sung by a dying swan\n" +
            "-31.92,115.74,128.1\n" +
            "theStroll Breathe in the light\n" +
            "-31.95,115.77,44.8,I'll stay here\n" +
            "-32.006542,115.76,43.0,In the shadow\n" +
            "-31.94,115.75,47.1\n";


    String testString1NoSubR2 = "curtin Bad route!\n" +
            "-32.007252,115.895919,47.1,Easy start\n" +
            "-32.006975,115.895554,55.3,Tricky, watch for drop bears.\n" +
            "-32.006586,115.895054,71.0,I*feel,like.over-punctuating!@#$%^&*()[]{}<>.?_+\n" +
            "-32.006542,115.894995,108.0,Getting there\n" +
            "-32.006519,115.894961,100.0\n" +
            "still Amazing views!\n" +
            "-32.005499,115.894965,1.0,I\n" +
            "-32.004826,115.894866,2.0,*theSub\n" +
            "-32.004119,115.894853,3.0\n" +
            "theSub subRoute\n" +
            "-32.004828,115.894866,4.0,We\n" +
            "-32.006928,115.894913,5.0,Love Walking\n" +
            "-32.007189,115.894873,6.0\n";

/*
    String testString1NoSubR2 = "curtin Bad route!\n" +
            "-32.007252,115.895919,47.1,Easy start\n" +
            "-32.006975,115.895554,55.3,Tricky, watch for drop bears.\n" +
            "-32.006586,115.895054,71.0,I*feel,like.over-punctuating!@#$%^&*()[]{}<>.?_+\n" +
            "-32.006542,115.894995,108.0,Getting there\n" +
            "-32.006519,115.894961,100.0\n" +
            "still Amazing views!\n" +
            "-32.005499,115.894965,1.0,I\n" +
            "-32.004826,115.894866,2.0,Hate Walking\n" +
            "-32.004119,115.894853,3.0\n";*/
    /*
    String testString1NoSubR2 = "curtin Bad route!\n" +
            "-32.007252,115.895919,47.1,Easy start\n" +
            "-32.006975,115.895554,55.3,Tricky, watch for drop bears.\n" +
            "-32.006586,115.895054,71.0,I*feel,like.over-punctuating!@#$%^&*()[]{}<>.?_+\n" +
            "-32.006542,115.894995,108.0,Getting there\n" +
            "-32.006519,115.894961,100.0\n";
      */

    /*Purpose: Stub method that takes in latitude + longitude of 2 points and calculates
    * the horizontal distance between the two points. Method of calculation used is the one
    * Dave instructed in the specification. */
    /*TODO hardrive erasure, validate/throw exceptions :D*/
    public double calcMetresDistance(double lat1, double long1, double lat2, double long2)
    {
        double d = 0.0;

        double lat1Sin = Math.PI * lat1 / 180;
        double lat2Sin = Math.PI * lat2 / 180;
        double longCos = Math.PI * Math.abs(long1 - long2) / 180;
        d = 6371000.0 * Math.acos((Math.sin(lat1Sin) * Math.sin(lat2Sin) + Math.cos(lat1Sin) * Math.cos(lat2Sin) * Math.cos(longCos)));




        return d;
    }

    /*Purpose: Another stub method used to retrieve/download the string. In reality this stub just
    * returns whatever test case is active/uncommented at compile time. Change the return value
    * to change the test case that is used by the system. */
    public String retrieveRouteData() throws IOException
    {
        return testString1NoSubR2;
    }




}
