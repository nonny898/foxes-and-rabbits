package io.muzoo.ooc.ecosystems.Actors.Animals;

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
     */
    public Fox() {
        super(BREEDING_AGE,MAX_AGE,BREEDING_PROBABILITY,MAX_LITTER_SIZE,FOOD_VALUE);
        this.setAlive(true);
        this.setAge(0);
        this.setFoodLevel(FOOD_VALUE);
        this.addPrey(Rabbit.class);
    }
}
