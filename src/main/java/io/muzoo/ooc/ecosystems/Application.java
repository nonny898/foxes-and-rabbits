package io.muzoo.ooc.ecosystems;

public class Application {
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 100);
        sim.simulate(500);
        System.exit(0);
    }

}
