package it.unibo.pcd.assignment03;

import it.unibo.pcd.assignment03.controller.Controller;
import it.unibo.pcd.assignment03.controller.ControllerImpl;
import it.unibo.pcd.assignment03.model.Brush;
import it.unibo.pcd.assignment03.model.BrushImpl;
import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.model.ModelImpl;
import it.unibo.pcd.assignment03.view.View;
import it.unibo.pcd.assignment03.view.ViewImpl;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class Launcher {
    private final static int NUM_ROWS = 40;
    private final static int NUM_COLS = 40;
    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        Model model = new ModelImpl(NUM_ROWS, NUM_COLS);
        View view = new ViewImpl();

        controller.init(view, model);
        model.init(controller);
        view.init(controller);
        controller.start();
    }
}
