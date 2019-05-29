package io.muzoo.ooc.ecosystems.Application;

import io.muzoo.ooc.ecosystems.Simulations.Simulator;

public class Application {
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 180);
        sim.simulate(500);
        System.exit(0);
    }

}
