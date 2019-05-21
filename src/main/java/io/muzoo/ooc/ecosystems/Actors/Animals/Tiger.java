package io.muzoo.ooc.ecosystems.Actors.Animals;

public class Tiger extends Carnivore {

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // In effect, this is the number of steps before it has to eat again.
    private static final int FOOD_VALUE = 5;

    public Tiger() {
        super(BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, FOOD_VALUE);
            this.setAlive(true);
            this.setAge(0);
            this.setFoodLevel(FOOD_VALUE);
            this.addPrey(Fox.class);
            this.addPrey(Rabbit.class);
    }
}
