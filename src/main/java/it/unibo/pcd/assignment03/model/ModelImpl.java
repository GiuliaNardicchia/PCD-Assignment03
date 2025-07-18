package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.util.Objects;
import java.util.Set;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class ModelImpl implements Model {
    private Controller controller;

    private final BrushManager brushManager = new BrushManagerImpl();

    private PixelGrid grid;
    private final Brush localBrush;

    public ModelImpl(int numRows, int numCols) {
        Brush localBrush = new BrushImpl(0, 0, randomColor());
        this.brushManager.addBrush(localBrush);
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
    public BrushManager getBrushManager() {
        return this.brushManager;
    }

    @Override
    public PixelGrid getGrid() {
        return this.grid;
    }

    @Override
    public void updateLocalBrush(int x, int y) {
        this.localBrush.updatePosition(x, y);
    }

    @Override
    public void updatePixelGrid(int x, int y, int color) {
        this.grid.set(x, y, color);
    }

    @Override
    public void updateLocalBrushColor(int color) {

        this.localBrush.setColor(color);
    }

    @Override
    public void setGrid(PixelGrid grid){
        this.grid = grid;
    }

    @Override
    public void updateGridFromSource(PixelGrid sourceGrid) {
        this.grid.setGrid(sourceGrid.getGrid());
    }

    @Override
    public void setBrushes(Set<Brush> brushes) {
        brushes.add(this.localBrush);
        this.brushManager.setBrushes(brushes);
    }

    @Override
    public void updateBrushes(Brush brush) {
        this.brushManager.getBrushes().stream()
                .filter(b -> Objects.equals(b.getId(), brush.getId()))
                .findFirst()
                .ifPresentOrElse(
                        b -> {
                            b.updatePosition(brush.getX(), brush.getY());
                            b.setColor(brush.getColor());
                        },
                        () -> this.brushManager.getBrushes().add(brush)
                );
    }
}
