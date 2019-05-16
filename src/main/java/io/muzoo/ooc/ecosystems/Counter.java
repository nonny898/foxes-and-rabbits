package io.muzoo.ooc.ecosystems;

/**
 * Provide a counter for a participant in the simulation.
 * This includes an identifying string and a count of how
 * many participants of this type currently exist within
 * the simulation.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.04.23
 */
class Counter {
    // A name for this type of simulation participant
    private String name;
    // How many of this type exist in the simulation.
    private int count;

    /**
     * Provide a name for one of the simulation types.
     *
     * @param name A name, e.g. "Fox".
     */
    Counter(String name) {
        this.name = name;
        count = 0;
    }

    /**
     * @return The short description of this type.
     */
    String getName() {
        return name;
    }

    /**
     * @return The current count for this type.
     */
    int getCount() {
        return count;
    }

    /**
     * Increment the current count by one.
     */
    void increment() {
        count++;
    }

    /**
     * Reset the current count to zero.
     */
    void reset() {
        count = 0;
    }
}
