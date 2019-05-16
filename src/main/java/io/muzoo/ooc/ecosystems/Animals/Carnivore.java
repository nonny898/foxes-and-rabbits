package io.muzoo.ooc.ecosystems.Animals;

import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.Iterator;
import java.util.List;

public class Carnivore extends Animal {

    // The age at which a fox can start to breed.
    private int breedingAge;
    // The age to which a fox can live.
    private int maxAge;
    // The likelihood of a fox breeding.
    private double breedingProbability;
    // The maximum number of births.
    private int maxLitterSize;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private int foodValue;

    Carnivore(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize, int foodValue) {
        super();
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.foodValue = foodValue;
    }

    @SuppressWarnings("unchecked")
    public void hunt(Field currentField, Field updatedField, List newAnimals) {
        incrementAge(this.maxAge);
        incrementHunger();
        if (this.getAlive()) {
            // New foxes are born into adjacent locations.
            int births = breed(this.breedingAge, this.breedingProbability, this.maxLitterSize);
            for (int b = 0; b < births; b++) {
                Carnivore newCarnivore = null;
                if (this instanceof Fox) {
                    newCarnivore = new Fox(false);
                }
                if (this instanceof Tiger) {
                    newCarnivore = new Tiger(false);
                }
                newAnimals.add(newCarnivore);
                Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                newCarnivore.setLocation(loc);
                updatedField.place(newCarnivore, loc);
            }
            // Move towards the source of food if found.
            Location newLocation = findFood(currentField, this.getLocation());
            if (newLocation == null) {  // no food found - move randomly
                newLocation = updatedField.freeAdjacentLocation(this.getLocation());
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                this.setAlive(false);
            }
        }
    }

    private void incrementHunger() {
        int foodLevel = this.getFoodLevel();
        foodLevel= foodLevel- 1;
        if (foodLevel <= 0) {
            this.setAlive(false);
        }
        else {
            this.setFoodLevel(foodLevel);
        }
    }

    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (this instanceof Fox) {
                if (animal instanceof Rabbit) {
                    Rabbit rabbit = (Rabbit) animal;
                    if (rabbit.getAlive()) {
                        rabbit.setEaten();
                        this.setFoodLevel(this.foodValue);
                        return where;
                    }
                }
            } else if (this instanceof Tiger) {
                    if (animal instanceof Fox) {
                        Fox fox = (Fox) animal;
                        if (fox.getAlive()) {
                            fox.setEaten();
                            this.setFoodLevel(this.foodValue);
                            return where;
                        }
                    } else if (animal instanceof Rabbit) {
                        Rabbit rabbit = (Rabbit) animal;
                        if (rabbit.getAlive()) {
                            rabbit.setEaten();
                            this.setFoodLevel(this.foodValue);
                            return where;
                        }
                    }
            }
        }
        return null;
    }
}
