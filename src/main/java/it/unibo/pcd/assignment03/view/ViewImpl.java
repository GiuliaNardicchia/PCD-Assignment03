package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;
import it.unibo.pcd.assignment03.model.PixelGrid;

public class ViewImpl implements View {
    private Controller controller;
    private final PixelGridView pixelGridView;


    public ViewImpl(int nRows, int nColumns) {
        PixelGrid grid = new PixelGrid(nRows, nColumns);
        BrushDrawer brushDrawer = new BrushDrawerImpl();
        this.pixelGridView = new PixelGridView(grid, brushDrawer, 600, 600);
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
    public void display() {
        pixelGridView.addMouseMovedListener((x, y) -> {
            controller.updateLocalBrushPosition(x, y);
            localBrush.updatePosition(x, y);
            pixelGridView.refresh();
        });

        pixelGridView.addPixelGridEventListener((x, y) -> {
            grid.set(x, y, localBrush.getColor());
            pixelGridView.refresh();
        });

        pixelGridView.addColorChangedListener(localBrush::setColor);
        pixelGridView.display();
    }
}
