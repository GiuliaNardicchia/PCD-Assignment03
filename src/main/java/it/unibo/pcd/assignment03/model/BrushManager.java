package it.unibo.pcd.assignment03.model;


import java.awt.*;
import java.util.List;

public class BrushManager {
    private List<Brush> brushes = new java.util.ArrayList<>();

    public void addBrush(final Brush brush) {
        brushes.add(brush);
    }

    public void removeBrush(final Brush brush) {
        brushes.remove(brush);
    }

}
