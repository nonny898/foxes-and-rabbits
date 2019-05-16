package io.muzoo.ooc.ecosystems.Animals;

import io.muzoo.ooc.ecosystems.Location;

import java.util.Random;

public class Animal {

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // The animal's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's position
    private Location location;
    // The animal's food level, which is increased by eating it's pray.
    private int foodLevel;

    void incrementAge(int maxAge) {
        this.age++;
        if (this.age > maxAge) {
            this.alive = false;
        }
    }

    int breed(int breedingAge, double breedingProbability, int maxLitterSize) {
        int births = 0;
        if (canBreed(breedingAge) && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    private boolean canBreed(int breedingAge) {
        return this.age >= breedingAge;
    }

    void setAge(int age) {
        this.age = age;
    }

    void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    int getFoodLevel() {
        return foodLevel;
    }

    public boolean getAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setEaten() {
        this.setAlive(false);
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    void setLocation(Location location) {
        this.location = location;
    }

    Location getLocation() {
        return location;
    }
}
