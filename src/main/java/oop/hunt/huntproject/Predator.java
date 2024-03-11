/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;

import java.util.*;

/**
 * Class representing a predator (shark) in the hunt simulation
 */
public class Predator extends Animal {
    boolean hunt;
    int range;

    /**
     * Constructor for the predator class
     */
    public Predator() {
        super("P");
        this.hunt = true;
        this.range = 60;
    }

    /**
     * Method for the predator to die and get removed from simulation
     */
    @Override
    void die() {
        HuntProject.predators.remove(this);
        Simulation.cleanUp(new Point(this.coordinates.x, this.coordinates.y));
    }

    /**
     * Method for the predator to move
     * @param x the x-coordinate of the current position of predator
     * @param y the y-coordinate of the current position of predator
     * @param p the point of the goal object
     */
    public void move(int x, int y, Point p) {
        List<Point> possib = new ArrayList<>();
        if (x+1<Simulation.GridSize && (Objects.equals(World[x + 1][y], "P") || Objects.equals(World[x + 1][y], "o"))) {
            possib.add(new Point(x + 1, y));
        }
        if (x-1 >= 0 && (Objects.equals(World[x - 1][y], "P") || Objects.equals(World[x - 1][y], "o"))) {
            possib.add(new Point(x-1, y));
        }
        if (y+1<Simulation.GridSize && (Objects.equals(World[x][y + 1], "P") || Objects.equals(World[x][y + 1], "o"))) {
            possib.add(new Point(x, y + 1));
        }
        if (y-1 >= 0 && (Objects.equals(World[x][y - 1], "P") || Objects.equals(World[x][y - 1], "o"))) {
            possib.add(new Point(x, y - 1));
        }
        possib.sort(Comparator.comparing(m -> (m.x- p.x)*(m.x - p.x) + (m.y - p.y)*(m.y - p.y)));
        this.prevcoord = new Point(this.coordinates.x, this.coordinates.y);
        this.coordinates = possib.get(0);
    }

    /**
     * Override of the run method from the Runnable interface
     */
    @Override
    public void run() {
        while (this.living) {
            int x = this.coordinates.x;
            int y = this.coordinates.y;
            Prey closest = null;
            int dist = Integer.MAX_VALUE;
            for (Prey p : preys) {
                int newdist = Point.distance(this.coordinates.x, this.coordinates.y,  p.coordinates.x, p.coordinates.y);
                if (newdist<dist && !p.safe && newdist < this.range) {
                    dist = newdist;
                    closest = p;
                }
            }

            if (dist <= 1) {
                int attack = (this.strength - closest.strength);
                if (attack < 0) {
                    this.health -= 1;
                    if (this.health == 0) {
                        this.living = false;
                        this.die();
                    }
                }
                else {
                    closest.health -= attack*2;
                    if (closest.health <= 0) {
                        closest.living = false;
                        closest.die();
                        if (Objects.equals(HuntProject.World[closest.coordinates.x][closest.coordinates.y], "P") || Objects.equals(HuntProject.World[closest.coordinates.x][closest.coordinates.y], "o")) {
                            Simulation.cleanUp(this.coordinates);
                            this.coordinates = new Point(closest.coordinates.x, closest.coordinates.y);
                        }
                        try {
                            this.hunt = false;
                            Thread.sleep(2000);
                            this.hunt = true;
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }

            }
            else {
                if (closest != null) {
                    move(x, y, closest.coordinates);
                } else {
                    Random rand = new Random();
                    int goalx = rand.nextInt(0, 31);
                    int goaly = rand.nextInt(0, 31);
                    move(x, y, new Point(goalx, goaly));
                }
            }

            try {
                Thread.sleep(1000/this.speed+200);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

    /**
     * @return the state of hunting of the predator (True if hunting, False if not)
     */
    public String getState() {
        if (this.hunt) {
            return "True";
        }
        else {
            return "False";
        }
    }
}