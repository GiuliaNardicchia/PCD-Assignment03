package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.util.HashSet;
import java.util.Set;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class ModelImpl implements Model {
    private Controller controller;
    private final Set<Brush> brushes = new HashSet<>();
    private final Brush localBrush;

    public ModelImpl() {
        Brush localBrush = new BrushImpl(0, 0, randomColor());
        this.brushes.add(localBrush);
        this.localBrush = localBrush;
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
    public void addBrush(final Brush brush) {
        brushes.add(brush);
    }

    @Override
    public void removeBrush(final Brush brush) {
        brushes.remove(brush);
    }
}
