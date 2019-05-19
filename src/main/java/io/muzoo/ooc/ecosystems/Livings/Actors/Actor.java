package io.muzoo.ooc.ecosystems.Livings.Actors;

import io.muzoo.ooc.ecosystems.Livings.Animals.*;
import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Livings.Living;
import io.muzoo.ooc.ecosystems.Location;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Actor extends Living {

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

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    Actor(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize, int foodValue) {
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.foodValue = foodValue;
    }

    public void hunt(Field currentField, Field updatedField, List newActors) {
        incrementAge(this.maxAge);
        incrementHunger();
        if (this.isAlive()) {
            Location newFoodLocation = findFood(currentField, this.getLocation());
            Location newPartnerLocation = findPartner(currentField, updatedField, this.getLocation(), newActors);
            this.move(newFoodLocation, updatedField);
            this.move(newPartnerLocation, updatedField);
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

    @SuppressWarnings("unchecked")
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
                        Male newMale = new Male();
                        newHumans.add(newMale);
                        Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                        assert newMale != null;
                        newMale.setLocation(loc);
                        updatedField.place(newMale, loc);
                    } else {
                        Female newFemale = new Female();
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
}

