package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

public interface Model {
    void init(Controller controller);

    Controller getController();

    void addBrush(Brush brush);

    void removeBrush(Brush brush);
}
