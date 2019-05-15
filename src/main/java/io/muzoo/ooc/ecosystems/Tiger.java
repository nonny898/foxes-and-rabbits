package io.muzoo.ooc.ecosystems;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Animal {
    // Characteristics shared by all tigers (static fields).

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 120;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single foxes. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int TIGER_FOOD_VALUE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // Individual characteristics (instance fields).

    // The tiger's food level, which is increased by eating foxes.
    private int foodLevel;

    Tiger(boolean randomAge) {
        super();
        this.setAge(0);
        this.setAlive(true);
        if (randomAge) {
            this.setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(TIGER_FOOD_VALUE);
        } else {
            // leave age at 0
            foodLevel = TIGER_FOOD_VALUE;
        }
    }

    @SuppressWarnings("unchecked")
    void hunt(Field currentField, Field updatedField, List newFoxes) {
        incrementAge(MAX_AGE);
        incrementHunger();
        if (this.getAlive()) {
            // New foxes are born into adjacent locations.
            int births = breed(BREEDING_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE);
            for (int b = 0; b < births; b++) {
                Tiger newTiger = new Tiger(false);
                newFoxes.add(newTiger);
                Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                newTiger.setLocation(loc);
                updatedField.place(newTiger, loc);
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
        foodLevel--;
        if (foodLevel <= 0) {
            this.setAlive(false);
        }
    }

    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.getAlive()) {
                    fox.setEaten();
                    foodLevel = TIGER_FOOD_VALUE;
                    return where;
                }
            } else if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.getAlive()) {
                    rabbit.setEaten();
                    foodLevel = TIGER_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
}
