package io.muzoo.ooc.ecosystems.Animals;

import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.List;

public class Herbivore extends Animal {

    // The age at which can start to breed.
    private int breedingAge;
    // The age to live.
    private int maxAge;
    // The likelihood of breeding.
    private double breedingProbability;
    // The maximum number of births.
    private int maxLitterSize;

    Herbivore(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize) {
        super();
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
    }

    @SuppressWarnings("unchecked")
    public void run(Field updatedField, List newRabbits) {
        incrementAge(this.maxAge);
        if (this.isAlive()) {
            int births = breed(this.breedingAge, this.breedingProbability, this.maxLitterSize);
            for (int b = 0; b < births; b++) {
                Rabbit newRabbit = new Rabbit(false);
                newRabbits.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                newRabbit.setLocation(loc);
                updatedField.place(newRabbit, loc);
            }
            Location newLocation = updatedField.freeAdjacentLocation(this.getLocation());
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                this.setAlive(false);
            }
        }
    }
}
