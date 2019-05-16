package io.muzoo.ooc.ecosystems.Actors;

import io.muzoo.ooc.ecosystems.Animals.*;
import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.Iterator;

public class Actor {

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void hunt(Field currentField, Field updatedField) {
        // Move towards the source of food if found.
        Location newLocation = findFood(currentField, this.getLocation());
        if (newLocation == null) {  // no food found - move randomly
            newLocation = updatedField.freeAdjacentLocation(this.getLocation());
        }
        if (newLocation != null) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        }
    }

    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Animal) {
                Animal animal1 = (Animal) animal;
                if (animal1.getAlive()) {
                    animal1.setEaten();
                    return where;
                }
            }
        }
        return null;
    }
}

