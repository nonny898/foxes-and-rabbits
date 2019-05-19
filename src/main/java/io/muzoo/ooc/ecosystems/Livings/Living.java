package io.muzoo.ooc.ecosystems.Livings;

import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.Random;

public class Living {

    // The animal's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's position
    private Location location;
    // The animal's food level, which is increased by eating it's pray.
    private int foodLevel;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    public boolean canBreed(int breedingAge) {
        return this.age >= breedingAge;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setEaten() {
        this.setAlive(false);
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void incrementAge(int maxAge) {
        this.age++;
        if (this.age > maxAge) {
            this.alive = false;
        }
    }

    public void incrementHunger() {
        int foodLevel = this.getFoodLevel();
        foodLevel = foodLevel - 1;
        if (foodLevel <= 0) {
            this.setAlive(false);
        } else {
            this.setFoodLevel(foodLevel);
        }
    }

    public int breed(int breedingAge, double breedingProbability, int maxLitterSize) {
        int births = 0;
        if (canBreed(breedingAge) && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    public void move(Location newLocation, Field updatedField) {
        if (newLocation == null) {
            newLocation = updatedField.freeAdjacentLocation(this.getLocation());
        }
        if (newLocation != null) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        } else {
            this.setAlive(false);
        }
    }
}
