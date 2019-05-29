package io.muzoo.ooc.ecosystems.Actors;

import io.muzoo.ooc.ecosystems.Actors.Animals.Animal;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Female;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Male;
import io.muzoo.ooc.ecosystems.Location.Field;
import io.muzoo.ooc.ecosystems.Location.Location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Living implements Actor{

    private int age;
    private boolean alive;
    private Location location;
    private int foodLevel;
    public int breedingAge;
    protected int maxAge;
    public double breedingProbability;
    public int maxLitterSize;
    protected int foodValue;
    protected HashSet<Class> prey;
    private Random random = new Random();

    @Override
    public boolean canBreed(int breedingAge) {
        return this.age >= breedingAge;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getFoodLevel() {
        return foodLevel;
    }

    @Override
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void setEaten() {
        this.setAlive(false);
    }

    @Override
    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void addPrey(Class prey) {
        this.prey.add(prey);
    }

    @Override
    public void incrementAge(int maxAge) {
        this.age++;
        if (this.age > maxAge) {
            this.alive = false;
        }
    }

    @Override
    public void incrementHunger() {
        int foodLevel = this.getFoodLevel();
        foodLevel = foodLevel - 1;
        if (foodLevel <= 0) {
            this.setAlive(false);
        } else {
            this.setFoodLevel(foodLevel);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void breed(Class<? extends Actor> instance, Field updatedField, List newActor, int breedingAge, double breedingProbability, int maxLitterSize) {
        if (this.isAlive()) {
            int births = 0;
            if (canBreed(breedingAge) && random.nextDouble() <= this.breedingProbability) {
                births = random.nextInt(maxLitterSize) + 1;
            }
            for (int b = 0; b < births; b++) {
                try {
                    Living newLiving = (Living) instance.newInstance();
                    newActor.add(newLiving);
                    Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                    newLiving.setLocation(loc);
                    updatedField.place(newLiving, loc);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void move(Location newLocation, Field updatedField) {
        if (newLocation == null) {
            newLocation = updatedField.freeAdjacentLocation(this.getLocation());
        }
        if (newLocation != null) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        } else {
            this.setAlive(false);
        }
    }

    @Override
    public void run(Field updatedField, List newAnimals) {
        incrementAge(this.maxAge);
        if (this.isAlive()) {
            Location newLocation = updatedField.freeAdjacentLocation(this.getLocation());
            this.move(newLocation, updatedField);
        }
    }

    @Override
    public void hunt(Field currentField, Field updatedField, List newActors) {
        incrementAge(this.maxAge);
        incrementHunger();
        if (this.isAlive()) {
            Location newFoodLocation = findFood(currentField, this.getLocation());
            this.move(newFoodLocation, updatedField);
        }
    }

    @Override
    public void mate(Field currentField, Field updatedField, List newActors) {
        incrementAge(this.maxAge);
        incrementHunger();
        if (this.isAlive()) {
            Location newPartnerLocation = findPartner(currentField, updatedField, this.getLocation(), newActors);
            this.move(newPartnerLocation, updatedField);
        }
    }

    @Override
    public Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal != null) {
                if (this.prey.contains(animal.getClass())) {
                    Animal prey = (Animal) animal;
                    if (prey.isAlive()) {
                        prey.setEaten();
                        this.setFoodLevel(this.foodValue);
                        return where;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Location findPartner(Field field, Field updatedField, Location location, List newHumans) {
            Iterator adjacentLocations = field.adjacentLocations(location);
            while (adjacentLocations.hasNext()) {
                Location where = (Location) adjacentLocations.next();
                Object human = field.getObjectAt(where);
                if ((human instanceof Female && this instanceof Male) || (human instanceof Male && this instanceof Female)) {
                        int gender = random.nextInt(2);
                        if (gender == 1) {
                            breed(Male.class, updatedField, newHumans, this.breedingAge, this.breedingProbability, this.maxLitterSize);
                        } else {
                            breed(Female.class, updatedField, newHumans, this.breedingAge, this.breedingProbability, this.maxLitterSize);
                        }
                    return where;
                }
            }
        return null;
    }
}
