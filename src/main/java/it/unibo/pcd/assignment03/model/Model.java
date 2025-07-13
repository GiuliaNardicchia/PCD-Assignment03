package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.util.Set;

public interface Model {
    void init(Controller controller);

    Controller getController();

    Brush getLocalBrush();

    BrushManager getBrushManager();

    PixelGrid getGrid();

    void updateLocalBrushPosition(int x, int y);

    void updatePixelGrid(int x, int y,  int color);

    void updateLocalBrushColor(int color);

    void setGrid(PixelGrid grid);

    void setBrushes(Set<Brush> brushes);

    void updateBrushes(Brush brush);
}
