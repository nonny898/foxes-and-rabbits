package io.muzoo.ooc.ecosystems.Actors.Hunters;

import io.muzoo.ooc.ecosystems.Actors.Animals.Rabbit;
import io.muzoo.ooc.ecosystems.Actors.Animals.Tiger;

public class Female extends Hunter {

    // The age at which a female can start to breed.
    private static final int BREEDING_AGE = 18;
    // The age to which a female can live.
    private static final int MAX_AGE = 70;
    // The likelihood of a female breeding.
    private static final double BREEDING_PROBABILITY = 1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single animal.
    private static final int FOOD_VALUE = 10;

    public Female() {
        super(BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, FOOD_VALUE);
        this.setAlive(true);
        this.setAge(0);
        this.setFoodLevel(FOOD_VALUE);
        this.addPrey(Tiger.class);
        this.addPrey(Rabbit.class);
    }
}
