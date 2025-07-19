package it.unibo.pcd.assignment03.model;

import java.io.Serializable;
import java.util.function.Consumer;

public class SerializableConsumer<T> implements Consumer<T>, Serializable {
    @Override
    public void accept(T value) {
        System.out.println("Received: " + value);
    }
}