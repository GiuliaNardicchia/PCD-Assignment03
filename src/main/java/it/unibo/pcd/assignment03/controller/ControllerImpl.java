package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.view.View;

public class ControllerImpl implements Controller {
    private Model model;
    private View view;

    @Override
    public void init(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public View getView() {
        return view;
    }
}
