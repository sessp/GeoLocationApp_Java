/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.view;

import java.util.Scanner;

/**
 *
 * @author 19127639
 */
public class TrackingView 
{
    
    public void displayString(String output)
    {
        System.out.println(" ");
        System.out.println(output);
        System.out.println(" ");
    }
    
    public String getModeForGPS()
    {
    
        System.out.println("Enter: 'auto' to automatically update your location OR Enter: 'man' to Manually update your position");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        
        return input;
        
    }
    
    public double getNumber()
    {
    
        Scanner s = new Scanner(System.in);
        double num = s.nextDouble();
        
        return num;
    }
    
}
