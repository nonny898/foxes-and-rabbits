package io.muzoo.ooc.ecosystems.Actors;

import io.muzoo.ooc.ecosystems.Location.Field;
import io.muzoo.ooc.ecosystems.Location.Location;

import java.util.List;

public interface Actor {

    boolean canBreed(int breedingAge);
    void setAge(int age);
    int getFoodLevel();
    void setFoodLevel(int foodLevel);
    boolean isAlive();
    void setAlive(boolean alive);
    void setEaten();
    void setLocation(int row, int col);
    void setLocation(Location location);
    Location getLocation();
    void addPrey(Class prey);
    void incrementAge(int maxAge);
    void incrementHunger() ;
    void move(Location newLocation, Field updatedField) ;
    void breed(Class<? extends Actor> instance, Field updatedField, List newRabbits, int breedingAge, double breedingProbability, int maxLitterSize);
    void run(Field updatedField, List newAnimals);
    void hunt(Field currentField, Field updatedField, List newActors);
    void mate(Field currentField, Field updatedField, List newActors);
    Location findFood(Field field, Location location);
    Location findPartner(Field field, Field updatedField, Location location, List newHumans);
}
