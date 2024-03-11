/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;

import java.util.Random;

/**
 * Class representing a source in the hunt simulation
 */
public class Source extends Place {
    int replenishingSpeed;

    /**
     * Constructor for the source class
     * @param name the name of the source
     * @param sign the sign representing the type of source
     */
    public Source(String name, String sign) {
        super(name, sign, 0, 0);

        Random rand = new Random();
        int speed = rand.nextInt(3,6);
        this.replenishingSpeed = speed;
    }

    /**
     * @return the replenishing speed of the source
     */
    public int replenish(){
        return this.replenishingSpeed*10;
    };
    
}
