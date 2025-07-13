package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class ModelImpl implements Model {
    private Controller controller;
    private PixelGrid grid;
    private final Set<Brush> brushes = new HashSet<>();
    private final Brush localBrush;

    public ModelImpl(int numRows, int numCols) {
        Brush localBrush = new BrushImpl(0, 0, randomColor());
        this.brushes.add(localBrush);
        this.localBrush = localBrush;
        this.grid = new PixelGrid(numRows, numCols);
    }

    @Override
    public void init(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public Brush getLocalBrush() {
        return localBrush;
    }

    @Override
    public Set<Brush> getBrushes() {
        return this.brushes;
    }

    @Override
    public PixelGrid getGrid() {
        return this.grid;
    }

    @Override
    public void addBrush(final Brush brush) {
        brushes.add(brush);
    }

    @Override
    public void removeBrush(final Brush brush) {
        brushes.remove(brush);
    }

    @Override
    public void updateLocalBrushPosition(int x, int y) {
        this.localBrush.updatePosition(x, y);
    }

    @Override
    public void updatePixelGrid(int x, int y) {
        this.grid.set(x, y, localBrush.getColor());
    }

    @Override
    public void updateLocalBrushColor(int color) {
        this.localBrush.setColor(color);
    }

    @Override
    public void updateBrushes(Brush brush) {
        this.brushes.stream()
                .filter(b -> Objects.equals(b.getId(), brush.getId()))
                .findFirst()
                .ifPresentOrElse(
                        b -> {
                            b.updatePosition(brush.getX(), brush.getY());
                            b.setColor(brush.getColor());
                            },
                        () -> this.brushes.add(brush)
                );
    }
}
