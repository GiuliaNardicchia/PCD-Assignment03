package it.unibo.pcd.assignment03.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BrushManagerImpl implements BrushManager, Serializable {
    private Set<Brush> brushes = new HashSet<>();

    @Override
    public void addBrush(Brush brush) {
        this.brushes.add(brush);
    }

    @Override
    public void removeBrush(Brush brush) {
        this.brushes.removeIf(other -> {
            // TODO
            try {
                return Objects.equals(other.getId(), brush.getId());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
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
