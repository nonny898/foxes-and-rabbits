package io.muzoo.ooc.ecosystems.Animals;

import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Carnivore extends Animal {

    // The age at which can start to breed.
    private int breedingAge;
    // The age to live.
    private int maxAge;
    // The likelihood of a breeding.
    private double breedingProbability;
    // The maximum number of births.
    private int maxLitterSize;
    // In effect, this is the number of steps before it has to eat again.
    private int foodValue;
    //  Set of  prey
    private HashSet<Class> prey;

    Carnivore(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize, int foodValue) {
        super();
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.foodValue = foodValue;
        this.prey = new HashSet<>();
    }

    void addPrey(Class prey) {
        this.prey.add(prey);
    }

    @SuppressWarnings("unchecked")
    public void hunt(Field currentField, Field updatedField, List newAnimals) {
        incrementAge(this.maxAge);
        incrementHunger();
        if (this.isAlive()) {
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
                assert newCarnivore != null;
                newCarnivore.setLocation(loc);
                updatedField.place(newCarnivore, loc);
            }
            Location newLocation = findFood(currentField, this.getLocation());
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
            if (animal != null) {
                if (this.prey.contains(animal.getClass())) {
                    Animal animal1 = (Animal) animal;
                    if (animal1.isAlive()) {
                        animal1.setEaten();
                        this.setFoodLevel(this.foodValue);
                        return where;
                    }
                }
            }
        }
        return null;
    }
}
