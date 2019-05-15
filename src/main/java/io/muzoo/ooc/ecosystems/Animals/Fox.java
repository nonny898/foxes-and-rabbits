package io.muzoo.ooc.ecosystems.Animals;

import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
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
    private static final int MAX_LITTER_SIZE = 10;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int FOOD_VALUE = 3;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge) {
        super(Fox.BREEDING_AGE,Fox.MAX_AGE,Fox.BREEDING_PROBABILITY,Fox.MAX_LITTER_SIZE,Fox.FOOD_VALUE);
        this.setAge(0);
        this.setAlive(true);
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
    void setEaten() {
        this.setAlive(false);
    }
}
