package it.unibo.pcd.assignment03.controller;

public enum Channels {
    BRUSH_EXCHANGE("brush", "info"),
    GRID_CELL_EXCHANGE("grid", "cell"),
    WELCOME_EXCHANGE("welcome_sharing", "info"),
    WELCOME_GRID_EXCHANGE("welcome_sharing", "grid"),
    WELCOME_BRUSHES_EXCHANGE("welcome_sharing", "brushes"),
    GOODBYE_EXCHANGE("goodbye_sharing", "info"),
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
