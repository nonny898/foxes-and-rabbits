package io.muzoo.ooc.ecosystems.Actors;

import io.muzoo.ooc.ecosystems.Actors.Animals.Fox;
import io.muzoo.ooc.ecosystems.Actors.Animals.Rabbit;
import io.muzoo.ooc.ecosystems.Actors.Animals.Tiger;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Female;
import io.muzoo.ooc.ecosystems.Actors.Hunters.Male;
import io.muzoo.ooc.ecosystems.Field;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ActorFactory {

    private static Map<Class, Double> probabilities = new LinkedHashMap<Class, Double>() {{
        put(Fox.class, 0.04);
        put(Rabbit.class, 0.08);
        put(Tiger.class, 0.03);
        put(Male.class, 0.01);
        put(Female.class, 0.02);
    }};

    @SuppressWarnings("unchecked")
    public static void createActor(List actors, int row, int col, Field field) {
        Random random = new Random();
        for (Map.Entry<Class, Double> entry : probabilities.entrySet()) {
            Class type = entry.getKey();
            Double prob = entry.getValue();
            if (random.nextDouble() <= prob) {
                try {
                    Living living = (Living) type.newInstance();
                    actors.add(living);
                    living.setLocation(row, col);
                    field.place(living, row, col);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
