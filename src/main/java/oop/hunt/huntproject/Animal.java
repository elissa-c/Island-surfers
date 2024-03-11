/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;

import java.util.Objects;
import java.util.Random;

/**
 * Abstract class representing an animal in the hunt simulation
 */

abstract class Animal extends HuntProject implements Runnable{
    String name;
    int speed;
    int strength;
    int health;
    String speciesName;
    Point coordinates;
    Point prevcoord = null;
    boolean living = true;

    /**
     * Constructor for the animal class
     * @param sign the sign for the animal
     */
    public Animal(String sign) {
        Random rand = new Random();
        if (Objects.equals(sign, "p")) {
            int place = rand.nextInt(hideouts.size());
            this.coordinates = new Point(hideouts.get(place).coordinates.x, hideouts.get(place).coordinates.y);
            this.speciesName = "surfer";
            this.health = 100;
            this.speed = rand.nextInt(1,2);
            this.strength = rand.nextInt(10, 61);
            int roll = rand.nextInt(1, 3);
            if (roll == 1) {
                this.name = "Long haired surfer";
            }
            else {
                this.name = "Short haired surfer";
            }
        }
        else {
            this.speciesName = "shark";
            this.speed = rand.nextInt(1,3);
            this.strength = rand.nextInt(20, 81);
            this.name = "Very scary shark";
            this.health = 5;
            boolean place = false;
            while (!place) {
                int x = rand.nextInt(Simulation.GridSize);
                int y = rand.nextInt(Simulation.GridSize);
                if (Objects.equals(HuntProject.World[x][y], "o") || Objects.equals(HuntProject.World[x][y], "P")) {
                    this.coordinates = new Point(x, y);
                    place = true;
                }
            }
        }
    }

    /**
     * @return  the name of the animal
     */
    public String Name() {
        return name;
    }

    /**
     * @return  the speed of the animal
     */
    public int Speed() {
        return speed;
    }

    /**
     * @return the strength of the animal
     */
    public int Strength() {return strength;}

    /**
     * @return the species of the animal
     */
    public String Species() {
        return speciesName;
    }


    /**
    Abstract method representing death of the animal
    */
    abstract void die();

    /**
     * Abstract method representing the movement of the animal
     * @param x the x-coordinate of the new position
     * @param y the y-coordinate of the new position
     * @param p the point of the goal object
     */
    abstract void move(int x, int y, Point p);


}