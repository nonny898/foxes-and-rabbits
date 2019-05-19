package io.muzoo.ooc.ecosystems.Livings.Animals;

import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Rabbit extends Herbivore {

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * @param randomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean randomAge) {
        super(Rabbit.BREEDING_AGE,Rabbit.MAX_AGE,Rabbit.BREEDING_PROBABILITY, Rabbit.MAX_LITTER_SIZE);
        this.setAge(0);
        this.setAlive(true);
        if (randomAge) {
            Random random = new Random();
            this.setAge(random.nextInt(MAX_AGE));
        }
    }
}
