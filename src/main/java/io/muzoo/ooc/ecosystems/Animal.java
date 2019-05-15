package io.muzoo.ooc.ecosystems;

import java.util.Random;

class Animal {

    // Characteristics shared by all animals (static fields).

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // Individual characteristics (instance fields).

    // The animal's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's position
    private Location location;

    /**
     * Increase the age. This could result in the animal's death.
     */
    void incrementAge(int maxAge) {
        this.age++;
        if (this.age > maxAge) {
            this.alive = false;
        }
    }
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    int breed(int breedingAge, double breedingProbability, int maxLitterSize) {
        int births = 0;
        if (canBreed(breedingAge) && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * A animal can breed if it has reached the breeding age.
     *
     * @return true if the animal can breed, false otherwise.
     */
    private boolean canBreed(int breedingAge) {
        return this.age >= breedingAge;
    }

    void setAge(int age) {
        this.age = age;
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    boolean getAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Set the animal's location.
     *
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     * Set the animal's location.
     *
     * @param location The animal's location.
     */
    void setLocation(Location location) {
        this.location = location;
    }

    Location getLocation() {
        return location;
    }
}
