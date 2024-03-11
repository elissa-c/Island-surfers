/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;


/**
 * Class representing a hideout in the hunt simulation
 */
public class Hideout extends Place {

    /**
     * Constructor for the hideout class
     * @param i the number of the hideout
     */
    public Hideout(int i) {
        super("Island no." +i, "H", 0, 0);
    }
}
