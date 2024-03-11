/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *The main class representing the hunt simulation
 */

public class HuntProject {
    public static String[][] World = new String[30][30];
    public static List<Prey> preys;
    public static List<Predator> predators;
    protected static List<WaterSource> wsources;
    protected static List<Plant> fsources;
    protected static List<Hideout> hideouts;
    static List<Crossroad> crossroads;

    /**
     * Startup method for initializing the simulation
     * and creating World matrix with all stationary objects
     */
    public static void startup() {
        preys = new ArrayList<>();
        predators = new ArrayList<>();
        wsources = new ArrayList<>();
        fsources = new ArrayList<>();
        hideouts = new ArrayList<>();
        crossroads = new ArrayList<>();

        for (int a =0; a<Simulation.GridSize; a++) {
            for (int b=0; b<Simulation.GridSize; b++) {
                World[a][b] = "o";
            }
        }

        for (int i = 0; i<5; i++) {
            hideouts.add(new Hideout(i));
            wsources.add(new WaterSource(i));
            fsources.add(new Plant(i));
        }

        for (Hideout h : hideouts) {
            for (WaterSource w : wsources) {
                createpath(h, w);
            }
            for (Plant p: fsources) {
                createpath(h, p);
            }
        }


    }

    /**
     * Method for creating a path between two points
     * @param p1 the first point
     * @param p2 the second point
     */
    private static void createpath(Place p1, Place p2) {
        int x1 = p1.coordinates.x;
        int y1 = p1.coordinates.y;
        int x2 = p2.coordinates.x;
        int y2 = p2.coordinates.y;
        int px = 1;
        int py = 1;

        if (x2<x1) {px = -1;}
        if (y2<y1) {py = -1;}
        boolean cross = false;

        while (x1 != x2 || y1 != y2) {
            int dx = Math.abs(x1 - x2);
            int dy = Math.abs(y1 - y2);
            if (dx>dy) {x1 +=px;}
            else {y1 +=py;}
            if (Objects.equals(World[x1][y1], "P") && !cross) {
                if (x1 != p1.coordinates.x + px && y1 != p1.coordinates.y + py && x1 != p2.coordinates.x - px && y1 != p2.coordinates.y - py &&
                        x1 != p1.coordinates.x - px && y1 != p1.coordinates.y - py && x1 != p2.coordinates.x + px && y1 != p2.coordinates.y + py) {
                    crossroads.add(new Crossroad(x1, y1));
                }
                cross = true;
            }
            else if (Objects.equals(World[x1][y1], "C")) {
                cross = true;
            }
            else if (Objects.equals(World[x1][y1], "o")) {
                World[x1][y1] = "P";
                cross = false;
            }
        }


    }

    /**
     * Method for creating a new prey
     */
    public static void createPrey(){
        Prey prey = new Prey();
        preys.add(prey);
        System.out.println("Added " + prey.name + " at " + prey.coordinates.x  +", "+ prey.coordinates.y);
        new Thread(prey).start();
    }

    /**
     * Method for creating a new predator
     */
    public static void createPredator(){
        Predator predator = new Predator();
        predators.add(predator);
        System.out.println("Added " + predator.name + " at " + predator.coordinates.x  +", "+ predator.coordinates.y);
        new Thread(predator).start();
    }

    /**
     * Method for finding a place in a point
     * @param click point to search
     * @return the place in the point
     */
    public static Place findPlace(Point click) {
        for (Hideout h : hideouts) {
            if (h.coordinates.x == click.x && h.coordinates.y == click.y) {
                Simulation.type = "H";
                return h;
            }
        }
        for (WaterSource w : wsources) {
            if (w.coordinates.x == click.x && w.coordinates.y == click.y) {
                Simulation.type = "W";
                return w;
            }
        }
        for (Plant f : fsources) {
            if (f.coordinates.x == click.x && f.coordinates.y == click.y) {
                Simulation.type = "F";
                return f;
            }
        }
        for (Crossroad c : crossroads) {
            if (c.coordinates.x == click.x && c.coordinates.y == click.y) {
                Simulation.type = "C";
                return c;
            }
        }
        return null;
    }

    
}
