/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.hunt.huntproject;

import java.util.*;

/**
 * Class representing a prey (surfer) in the hunt simulation
 */
public class Prey extends Animal {
    int waterLevel;
    int foodLevel;
    boolean safe = true;

    /**
     * Constructor for the prey class
     */
    public Prey() {
        super("p");
        this.waterLevel = 60;
        this.foodLevel = 65;

    }

    /**
     * @return the health level of the prey
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Method for the prey to reproduce
     * @param p point of the reproduction place
     */
    public void reproduce(Point p) {
        Prey prey = new Prey();
        prey.coordinates = new Point(p.x, p.y);
        preys.add(prey);
        System.out.println("Reproduced by division " + prey.name + " at " + prey.coordinates.x  +", "+ prey.coordinates.y);
        new Thread(prey).start();
    }

    /**
     * Method for the prey to die and get removed from simulation
     */
    @Override
    public void die() {
        HuntProject.preys.remove(this);
        Simulation.cleanUp(new Point(this.coordinates.x, this.coordinates.y));
    }

    /**
     * Method for the prey to move
     * @param x the x-coordinate of the current position of prey
     * @param y the y-coordinate of the current position of prey
     * @param p the point of the goal object
     */
    public void move(int x, int y, Point p) {
        List<Point> possib = new ArrayList<>();
        if (x+1<Simulation.GridSize && (Objects.equals(World[x + 1][y], "P") || Objects.equals(World[x + 1][y], "C") || Objects.equals(World[x + 1][y], "H"))) {
            possib.add(new Point(x + 1, y));
        }
        if (x-1 >= 0 && (Objects.equals(World[x - 1][y], "P") || Objects.equals(World[x - 1][y], "C") || Objects.equals(World[x - 1][y], "H"))) {
            possib.add(new Point(x-1, y));
        }
        if (y+1<Simulation.GridSize && (Objects.equals(World[x][y + 1], "P") || Objects.equals(World[x][y + 1], "C") || Objects.equals(World[x][y + 1], "H"))) {
            possib.add(new Point(x, y + 1));
        }
        if (y-1 >= 0 && (Objects.equals(World[x][y - 1], "P") || Objects.equals(World[x][y - 1], "C")) || Objects.equals(World[x][y - 1], "H")) {
            possib.add(new Point(x, y - 1));
        }
        possib.sort(Comparator.comparing(m -> (m.x- p.x)*(m.x - p.x) + (m.y - p.y)*(m.y - p.y)));

        if (HuntProject.World[possib.get(0).x][possib.get(0).y] == "H") {
            this.safe = true;
        }
        else {
            this.safe = false;
        }

        if (Objects.equals(HuntProject.World[possib.get(0).x][possib.get(0).y], "C")) {
            for (Crossroad c : HuntProject.crossroads) {
                if (c.coordinates.x == possib.get(0).x && c.coordinates.y == possib.get(0).y) {
                    if (!c.isFull()) {
                        c.addAnimal(this);
                        this.prevcoord = new Point(this.coordinates.x, this.coordinates.y);
                        this.coordinates.x = possib.get(0).x;
                        this.coordinates.y = possib.get(0).y;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        c.removeAnimal(this);
                    }
                    break;
                }
            }
        }
        else {
            this.prevcoord = new Point(this.coordinates.x, this.coordinates.y);
            this.coordinates.x = possib.get(0).x;
            this.coordinates.y = possib.get(0).y;
        }

    }

    /**
     * Override of the run method from the Runnable interface
     */
    @Override
    public void run() {
        while (this.living) {
            int x = this.coordinates.x;
            int y = this.coordinates.y;

            if (this.foodLevel > 70 && this.waterLevel > 70) {
                Hideout closest =null;
                int dist = Integer.MAX_VALUE;
                for (Hideout h : hideouts) {
                    int newdist = Point.distance(this.coordinates.x, this.coordinates.y,  h.coordinates.x, h.coordinates.y);
                    if (newdist < dist) {
                        dist = newdist;
                        closest = h;
                    }
                }
                if (dist == 0) {
                    if (!closest.isFull()) {
                        closest.addAnimal(this);
                        reproduce(this.coordinates);
                        this.waterLevel -= 15;
                        this.foodLevel -= 15;
                        this.health += 15;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        closest.removeAnimal(this);
                    }
                }
                else {
                    assert closest != null;
                    move(x, y, closest.coordinates);
                };
            }

            else if (this.foodLevel > this.waterLevel) {
                WaterSource closest = null;
                int dist = Integer.MAX_VALUE;
                for (WaterSource w : wsources) {
                    int newdist = Point.distance(this.coordinates.x, this.coordinates.y,  w.coordinates.x, w.coordinates.y);
                    if (newdist < dist) {
                        dist = newdist;
                        closest = w;
                    }
                }
                if (dist <= 1) {
                    if (!closest.isFull()) {
                        closest.addAnimal(this);
                        this.waterLevel += closest.replenish();
                        if (this.waterLevel>100) {
                            this.waterLevel = 100;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        closest.removeAnimal(this);
                    }
                }
                else {
                    assert closest != null;
                    move(x, y, closest.coordinates);
                }
            }
            else {
                Plant closest = null;
                int dist = Integer.MAX_VALUE;
                for (Plant f : fsources) {
                    int newdist = Point.distance(this.coordinates.x, this.coordinates.y,  f.coordinates.x, f.coordinates.y);
                    if (newdist < dist) {
                        dist = newdist;
                        closest = f;
                    }
                }
                if (dist <= 1) {
                    if (!closest.isFull()) {
                        closest.addAnimal(this);
                        this.foodLevel += closest.replenish();
                        if (this.foodLevel>100) {
                            this.foodLevel = 100;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        closest.removeAnimal(this);
                    }

                }
                else {
                    assert closest != null;
                    move(x, y, closest.coordinates);
                }
            }



            this.waterLevel -= 2;
            this.foodLevel -= 2;

            if (this.waterLevel <= 0 || this.foodLevel <= 0) {
                this.living = false;
                this.die();
            }

            try {
                Thread.sleep(1000/this.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}