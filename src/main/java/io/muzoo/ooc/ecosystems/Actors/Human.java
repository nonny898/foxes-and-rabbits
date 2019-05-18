package io.muzoo.ooc.ecosystems.Actors;

import io.muzoo.ooc.ecosystems.Animals.Animal;
import io.muzoo.ooc.ecosystems.Field;
import io.muzoo.ooc.ecosystems.Location;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Human extends Actor {

    public Human() {
        super();
    }

    public void mate(Field currentField, Field updatedField, List newHumans) {
        Location newLocation = findPartner(currentField, updatedField, this.getLocation(), newHumans);
        if (newLocation == null) {
            newLocation = updatedField.freeAdjacentLocation(this.getLocation());
        }
        if (newLocation != null) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        }
    }

    private Location findPartner(Field field, Field updatedField, Location location, List newHumans) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object human = field.getObjectAt(where);
            if (human != null){
                Random random = new Random();
                int birth = random.nextInt(2);
                if (birth == 1) {
                    int gender = random.nextInt(2);
                    if (gender == 1) {
                        Male newMale = new Male();
                        newHumans.add(newMale);
                        Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                        assert newMale != null;
                        newMale .setLocation(loc);
                        updatedField.place(newMale, loc);
                    }
                    else  {
                        Female newFemale = new Female();
                        newHumans.add(newFemale);
                        Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                        assert newFemale != null;
                        newFemale .setLocation(loc);
                        updatedField.place(newFemale, loc);
                    }
                }
                return where;
            }
        }
        return null;
    }
}
