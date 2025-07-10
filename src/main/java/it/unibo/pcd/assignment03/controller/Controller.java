package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.view.View;

public interface Controller {
    void init(View view, Model model);

    Model getModel();

    View getView();
}
