package io.muzoo.ooc.ecosystems.Animals;

import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Fox extends Carnivore{
    // Characteristics shared by all foxes (static fields).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit.
    private static final int FOOD_VALUE = 4;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with random age.
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge) {
        super(Fox.BREEDING_AGE,Fox.MAX_AGE,Fox.BREEDING_PROBABILITY,Fox.MAX_LITTER_SIZE,Fox.FOOD_VALUE);
        this.setAge(0);
        this.setAlive(true);
        this.addPrey(Rabbit.class);
        if (randomAge) {
            Random random = new Random();
            this.setAge(random.nextInt(MAX_AGE));
            this.setFoodLevel(random.nextInt(FOOD_VALUE));
        } else {
            this.setFoodLevel(FOOD_VALUE);
        }
    }
}
