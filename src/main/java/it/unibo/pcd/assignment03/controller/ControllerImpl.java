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
        return this.model;
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public void updateLocalBrushPosition(int x, int y) {
        this.model.updateLocalBrushPosition(x, y);
    }

    @Override
    public void setGridPixel(int x, int y) {
        this.model.setGridPixel(x, y);

    }

    @Override
    public void setLocalBrushColor(int color) {
        this.model.setLocalBrushColor(color);

    }

    @Override
    public void start() {
        this.view.display();
        this.view.refresh();
    }
}
