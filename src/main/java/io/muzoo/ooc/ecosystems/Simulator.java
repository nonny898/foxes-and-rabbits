package io.muzoo.ooc.ecosystems;

import io.muzoo.ooc.ecosystems.Actors.ActorFactory;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Female;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Hunter;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Male;
import io.muzoo.ooc.ecosystems.Actors.Animals.*;
import io.muzoo.ooc.ecosystems.Actors.Living;

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

    // The list of actors in the field
    private List actors;
    // The list of human just born
    private List newActors;
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
        actors = new ArrayList();
        newActors = new ArrayList();
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
        newActors.clear();
        for (Object object : actors) {
            if (object instanceof Herbivore) {
                Herbivore herbivore = (Herbivore) object;
                herbivore.run(updatedField, newActors);
                herbivore.breed(herbivore.getClass(), updatedField, newActors, herbivore.breedingAge, herbivore.breedingProbability, herbivore.maxLitterSize);
            } else if (object instanceof Carnivore) {
                Carnivore carnivore = (Carnivore) object;
                carnivore.hunt(field, updatedField, newActors);
                carnivore.breed(carnivore.getClass(), updatedField, newActors, carnivore.breedingAge, carnivore.breedingProbability, carnivore.maxLitterSize);
            } else if (object instanceof Hunter) {
                Hunter hunter = (Hunter) object;
                hunter.hunt(field, updatedField, newActors);
                hunter.mate(field, updatedField, newActors);
            } else {
                System.out.println("found unknown hunter");
            }
        }

        actors.addAll(newActors);

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
        actors.clear();
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
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                ActorFactory.createActor(actors, row, col, field);
            }
        }
        Collections.shuffle(actors);
    }
}
