package io.muzoo.ooc.ecosystems.Actors;

import io.muzoo.ooc.ecosystems.Animals.*;
import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Actor {

    // The animal's age.
    int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's position
    private Location location;
    // The animal's food level, which is increased by eating it's pray.
    private int foodLevel;
    // The age at which can start to breed.
    int breedingAge;
    // The age to live.
    private int maxAge;
    // The likelihood of a breeding.
    double breedingProbability;
    // The maximum number of births.
    int maxLitterSize;
    // In effect, this is the number of steps before it has to eat again.
    private int foodValue;

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    public Actor(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize, int foodValue) {
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.foodValue = foodValue;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setAge(int age) {
        this.age = age;
    }

    Location getLocation() {
        return location;
    }

    void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    void incrementAge(int maxAge) {
        this.age++;
        if (this.age > maxAge) {
            this.setAlive(false);
        }
    }

    void incrementHunger() {
        int foodLevel = this.getFoodLevel();
        foodLevel = foodLevel - 1;
        if (foodLevel <= 0) {
            this.setAlive(false);
        } else {
            this.setFoodLevel(foodLevel);
        }
    }

    public void hunt(Field currentField, Field updatedField, List newActors) {
        incrementAge(this.maxAge);
        incrementHunger();
        if (this.isAlive()) {
            Location newFoodLocation = findFood(currentField, this.getLocation());
            Location newPartnerLocation = findPartner(currentField, updatedField, this.getLocation(), newActors);
            if (newFoodLocation == null) {
                newFoodLocation = updatedField.freeAdjacentLocation(this.getLocation());
            }
            if (newFoodLocation != null) {
                setLocation(newFoodLocation);
                updatedField.place(this, newFoodLocation);
            }
            if (newPartnerLocation == null) {
                newPartnerLocation = updatedField.freeAdjacentLocation(this.getLocation());
            }
            if (newPartnerLocation != null) {
                setLocation(newPartnerLocation);
                updatedField.place(this, newPartnerLocation);
            } else {
                this.setAlive(false);
            }
        }
    }

    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Animal) {
                Animal animal1 = (Animal) animal;
                if (animal1.isAlive()) {
                    animal1.setEaten();
                    this.setFoodLevel(this.foodValue);
                    return where;
                }
            }
        }
        return null;
    }

    private Location findPartner(Field field, Field updatedField, Location location, List newHumans) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object human = field.getObjectAt(where);
            if ((human instanceof Female && this instanceof  Male) || (human instanceof Male && this instanceof  Female))  {
                int birth = breed(this.breedingAge, this.breedingProbability, this.maxLitterSize);
                for (int i = 0; i < birth; i++) {
                    int gender = rand.nextInt(2);
                    if (gender == 1) {
                        Male newMale = new Male(false);
                        newHumans.add(newMale);
                        Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                        assert newMale != null;
                        newMale.setLocation(loc);
                        updatedField.place(newMale, loc);
                    } else {
                        Female newFemale = new Female(false);
                        newHumans.add(newFemale);
                        Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                        assert newFemale != null;
                        newFemale.setLocation(loc);
                        updatedField.place(newFemale, loc);
                    }
                }
                return where;
            }
        }
        return null;
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

}

