/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


/**
 * Class representing a place in hunt simulation
 */
public class Place {
    String name;
    int capacity;
    Point coordinates;
    List<Animal> whosThere;

    /**
     * Constructor for the Place class
     * @param name the name of the place
     * @param sign the sign representing the type of place
     * @param x the x-coordinate of the place
     * @param y the y-coordinate of the place
     */
    public Place(String name, String sign, int x, int y) {
        if (Objects.equals(sign, "C")) {
            this.capacity = 1;
        }
        else {
            Random rand = new Random();
            boolean free = false;
            while (!free) {
                x = rand.nextInt(Simulation.GridSize);
                y = rand.nextInt(Simulation.GridSize);
                if (Objects.equals(HuntProject.World[x][y], "o")) {
                    free = true;
                }
            }
            this.capacity = rand.nextInt(1, 4);
        }

        this.coordinates = new Point(x, y);
        this.whosThere = new ArrayList<>();
        this.name = name;
        setinWorld(sign, x, y);
    }

    /**
     * Method for setting the place on the map
     * @param sign the sign representing the type of place
     * @param x the x-coordinate of the place
     * @param y the y-coordinate of the place
     */
    private void setinWorld(String sign, int x, int y) {
        HuntProject.World[x][y] = sign;
    }

    /**
     * Method for checking if the place is full
     * @return true if the place is full, false otherwise
     */
    public boolean isFull() {
        if (this.whosThere.size() == this.capacity) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method for adding an animal to the place
     * @param animal the animal to add
     */
    public void addAnimal(Animal animal){
        this.whosThere.add(animal);
    };

    /**
     * Method for removing an animal from the place
     * @param animal the animal to remove
     */
    public void removeAnimal(Animal animal){
        this.whosThere.remove(animal);
    };

    /**
     * Method for getting the list of animals in the place
     * @return the list of names of animals in the place
     */
    public List<String> getAnimals() {
        List<String> names = new ArrayList<>();
        for (Animal a : this.whosThere) {
            names.add(a.name);
        }
        return names;
    }
}

