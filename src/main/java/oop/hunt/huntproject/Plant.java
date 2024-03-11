/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;


/**
 * Class representing a Plant (food source) in the hunt simulation
 */
public class Plant extends Source {

    /**
     * Constructor for the Plant class
     * @param i the number of the plant
     */
    public Plant(int i) {
        super("Village no." +i, "V");
    }
}
