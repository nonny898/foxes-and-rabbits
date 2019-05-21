package io.muzoo.ooc.ecosystems.Actors.Hunters;

import io.muzoo.ooc.ecosystems.Actors.Living;

import java.util.HashSet;

public class Hunter extends Living {

    Hunter(int breedingAge, int maxAge, double breedingProbability, int maxLitterSize, int foodValue) {
        super();
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.foodValue = foodValue;
        this.prey = new HashSet<>();
    }
}
