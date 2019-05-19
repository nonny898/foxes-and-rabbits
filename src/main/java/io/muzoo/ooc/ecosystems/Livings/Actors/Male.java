package io.muzoo.ooc.ecosystems.Livings.Actors;

import java.util.Random;

public class Male extends Human {

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 20000;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 10;
    // The food value of a single rabbit.
    private static final int FOOD_VALUE = 4;

    public Male(boolean randomAge) {
        super(BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, FOOD_VALUE);
        this.setAlive(true);
        this.setAge(0);
        if (randomAge) {
            Random random = new Random();
            this.setAge(random.nextInt(MAX_AGE));
            this.setFoodLevel(random.nextInt(FOOD_VALUE));
        } else {
            this.setFoodLevel(FOOD_VALUE);
        }
    }
}
