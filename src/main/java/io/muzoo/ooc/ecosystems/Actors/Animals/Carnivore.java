package io.muzoo.ooc.ecosystems.Actors.Animals;

import java.util.HashSet;

public class Carnivore extends Animal{

    Carnivore(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize, int foodValue) {
        super();
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.foodValue = foodValue;
        this.prey = new HashSet<>();
    }
}
