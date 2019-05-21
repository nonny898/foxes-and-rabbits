package io.muzoo.ooc.ecosystems;

import io.muzoo.ooc.ecosystems.Actors.Hunters.Female;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Hunter;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Male;
import io.muzoo.ooc.ecosystems.Actors.Animals.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
class Simulator {
    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.0;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a tiger will be created in any given grid position.
    private static final double TIGER_CREATION_PROBABILITY = 0.06;
    // The probability that a male will be created in any given grid position.
    private static final double MALE_CREATION_PROBABILITY = 0.01;
    // The probability that a female will be created in any given grid position.
    private static final double FEMALE_CREATION_PROBABILITY = 0.01;

    // The list of animals in the field
    private List animals;
    // The list of animals just born
    private List newAnimals;
    // The list of humans in the field
    private List humans;
    // The list of human just born
    private List newHumans;
    // The current state of the field.
    private Field field;
    // A second field, used to build the next stage of the simulation.
    private Field updatedField;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList();
        newAnimals = new ArrayList();
        humans = new ArrayList();
        newHumans = new ArrayList();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Tiger.class, Color.green);
        view.setColor(Male.class, Color.black);
        view.setColor(Female.class, Color.red);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     */
    @SuppressWarnings("SameParameterValue")
    void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    @SuppressWarnings("unchecked")
    private void simulateOneStep() {
        step++;
        newAnimals.clear();
        newHumans.clear();
        for (Object object : animals) {
            if (object instanceof Herbivore) {
                Herbivore herbivore = (Herbivore) object;
                herbivore.run(updatedField, newAnimals);
                herbivore.breed(herbivore.getClass(), updatedField, newAnimals, herbivore.breedingAge, herbivore.breedingProbability, herbivore.maxLitterSize);
            } else if (object instanceof Carnivore) {
                Carnivore carnivore = (Carnivore) object;
                carnivore.hunt(field, updatedField, newAnimals);

                carnivore.breed(carnivore.getClass(), updatedField, newAnimals, carnivore.breedingAge, carnivore.breedingProbability, carnivore.maxLitterSize);
            } else {
                System.out.println("found unknown animal");
            }
        }
        for (Object object : humans) {
            if (object instanceof Hunter) {
                Hunter hunter = (Hunter) object;
                hunter.hunt(field, updatedField,newHumans);
                hunter.mate(field,updatedField,newHumans);
            } else {
                System.out.println("found unknown hunter");
            }
        }

        // add new born animals to the list of animals
        animals.addAll(newAnimals);
        humans.addAll(newHumans);

        // Swap the field and updatedField at the end of the step.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // display the new field on screen
        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    private void reset() {
        step = 0;
        animals.clear();
        humans.clear();
        field.clear();
        updatedField.clear();
        populate(field);

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Populate a field with foxes and rabbits.
     *
     * @param field The field to be populated.
     */
    @SuppressWarnings("unchecked")
    private void populate(Field field) {
        Random rand = new Random();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox();
                    animals.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                } else if (rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit();
                    animals.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                } else if (rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Tiger tiger = new Tiger();
                    animals.add(tiger);
                    tiger.setLocation(row, col);
                    field.place(tiger, row, col);
                } else if (rand.nextDouble() <= MALE_CREATION_PROBABILITY) {
                    Male male = new Male();
                    humans.add(male);
                    male.setLocation(row, col);
                    field.place(male, row, col);
                } else if (rand.nextDouble() <= FEMALE_CREATION_PROBABILITY) {
                    Female female = new Female();
                    humans.add(female);
                    female.setLocation(row, col);
                    field.place(female, row, col);
                }
            }
        }
        Collections.shuffle(animals);
        Collections.shuffle(humans);
    }
}
