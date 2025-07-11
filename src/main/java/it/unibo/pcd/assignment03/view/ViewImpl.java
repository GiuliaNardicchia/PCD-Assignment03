package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;

public class ViewImpl implements View {
    private Controller controller;
    private PixelGridView pixelGridView;


    @Override
    public void init(Controller controller) {
        this.controller = controller;
        this.pixelGridView = new PixelGridView(this.controller.getModel().getGrid(), new BrushDrawerImpl(this.controller.getModel().getBrushes()), 600, 600);
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void display() {
        pixelGridView.addMouseMovedListener((x, y) -> {
            controller.updateLocalBrushPosition(x, y);
            pixelGridView.refresh();
        });

        pixelGridView.addPixelGridEventListener((x, y) -> {
            controller.updatePixelGrid(x, y);
            pixelGridView.refresh();
        });

        pixelGridView.addColorChangedListener(controller::updateLocalBrushColor);
        pixelGridView.display();
    }
}
