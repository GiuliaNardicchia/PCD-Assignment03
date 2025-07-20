package it.unibo.pcd.assignment03.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BrushManagerImpl implements BrushManager, Serializable {
    private Set<Brush> brushes = new HashSet<>();

    @Override
    public synchronized void addBrush(Brush brush) throws RemoteException {
        System.out.println("Brushes before adding: " + this.brushes);
        System.out.println("Adding brush: " + brush);
        this.brushes.add(new BrushImpl(brush.getId(), brush.getX(), brush.getY(), brush.getColor()));
        System.out.println("Brushes after adding: " + this.brushes);
    }

    @Override
    public synchronized void removeBrush(Brush brush) throws RemoteException {
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
    public synchronized Set<Brush> getBrushes() throws RemoteException {
        return this.brushes;
    }

    @Override
    public void updateBrush(Brush localBrush, int x, int y, int color) throws RemoteException {
        this.brushes.stream()
                .filter(b -> {
                    try {
                        return Objects.equals(b.getId(), localBrush.getId());
                    } catch (RemoteException ignored) {
                    }
                    return false;
                })
                .findFirst()
                .ifPresentOrElse(
                        b -> {
                            try {
                                b.updatePosition(x, y);
                                b.setColor(color);
                            } catch (RemoteException ignored) {
                            }
                        },
                        () -> System.out.println("Brush not found: " + localBrush)
                );
    }
}
