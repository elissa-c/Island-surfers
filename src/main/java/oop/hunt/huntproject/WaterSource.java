/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;

/**
 * Class representing a water source (wave) in the hunt simulation
 */
public class WaterSource extends Source {

    /**
     * Constructor for the water source class
     * @param i the number of the water source
     */
    public WaterSource(int i) {
        super("Cool wave no." + i, "W");
    }
}
