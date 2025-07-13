package it.unibo.pcd.assignment03.model;

import java.util.HashSet;
import java.util.Set;

public class BrushManagerImpl implements BrushManager {
    private Set<Brush> brushes = new HashSet<>();

    @Override
    public void addBrush(Brush brush) {
        this.brushes.add(brush);
    }

    @Override
    public void removeBrush(Brush brush) {
        this.brushes.remove(brush);
    }

    @Override
    public Set<Brush> getBrushes() {
        return this.brushes;
    }

    @Override
    public void setBrushes(Set<Brush> brushes) {
        this.brushes = brushes;
    }
}
