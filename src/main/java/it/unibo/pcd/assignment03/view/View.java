package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;

public interface View {
    void init(Controller controller);

    Controller getController();

    void display();

    void refresh();
}
