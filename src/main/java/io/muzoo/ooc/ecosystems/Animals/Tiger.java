package io.muzoo.ooc.ecosystems.Animals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Tiger extends Carnivore {
    // Characteristics shared by all tigers (static fields).

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single foxes. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int FOOD_VALUE = 5;

    public Tiger(boolean randomAge) {
        super(Tiger.BREEDING_AGE,Tiger.MAX_AGE,Tiger.BREEDING_PROBABILITY,Tiger.MAX_LITTER_SIZE,Tiger.FOOD_VALUE);
        this.setAge(0);
        this.setAlive(true);
        this.addPrey(Fox.class);
        this.addPrey(Rabbit.class);
        if (randomAge) {
            // A shared random number generator to control breeding.
            Random random = new Random();
            this.setAge(random.nextInt(MAX_AGE));
            this.setFoodLevel(random.nextInt(FOOD_VALUE));
        } else {
            // leave age at 0
            this.setFoodLevel(FOOD_VALUE);
        }
    }
}
