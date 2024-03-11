/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;


/**
 * Class representing coordinates in the simulation
 */
public class Point {
    public int x;
    public int y;

    /**
     * Constructor for the Point class
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to check the distance between two points
     * @param x1 the x coordinate of the first point
     * @param y1 the y coordinate of the first point
     * @param x2 the x coordinate of the second point
     * @param y2 the y coordinate of the second point
     * @return the sqared distance between the two points
     */
    public static int distance(int x1, int y1, int x2, int y2) {
        return ((x1- x2)*(x1-x2) + (y1 - y2)*(y1-y2));
    }
}
