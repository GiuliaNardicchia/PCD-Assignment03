package it.unibo.pcd.assignment03.controller;

public enum Channels {
    BRUSH_POSITION_EXCHANGE("brush", "info"),
    GRID_CELL_EXCHANGE("grid", "cell"),
    ;
    private final String name;
    private final String key;

    Channels(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
