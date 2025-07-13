package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.util.Set;

public interface Model {
    void init(Controller controller);

    Controller getController();

    Brush getLocalBrush();

    Set<Brush> getBrushes();

    PixelGrid getGrid();

    void addBrush(Brush brush);

    void removeBrush(Brush brush);

    void updateLocalBrushPosition(int x, int y);

    void updatePixelGrid(int x, int y,  int color);

    void updateLocalBrushColor(int color);

    void updateBrushes(Brush brush);
}
