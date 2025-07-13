package it.unibo.pcd.assignment03.controller;

public enum Channels {
    BRUSH_POSITION_EXCHANGE("brush", "position"),
    BRUSH_COLOR_EXCHANGE("brush", "color"),
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
