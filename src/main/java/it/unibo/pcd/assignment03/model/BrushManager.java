package it.unibo.pcd.assignment03.model;

import java.util.Set;

public interface BrushManager {
    void addBrush(Brush brush);

    void removeBrush(Brush brush);

    Set<Brush> getBrushes();

    void setBrushes(Set<Brush> brushes);
}
