package io.muzoo.ooc.ecosystems.Animals;

import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Rabbit extends Herbivore {
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean randomAge) {
        super(Rabbit.BREEDING_AGE,Rabbit.MAX_AGE,Rabbit.BREEDING_PROBABILITY, Rabbit.MAX_LITTER_SIZE);
        this.setAge(0);
        this.setAlive(true);
        if (randomAge) {
            this.setAge(rand.nextInt(MAX_AGE));
        }
    }
}
