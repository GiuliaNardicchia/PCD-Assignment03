package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;
import it.unibo.pcd.assignment03.model.PixelGridImpl;

import java.rmi.RemoteException;

public class ViewImpl implements View {
    private Controller controller;
    private SessionView sessionView;
    private PixelGridView pixelGridView;


    @Override
    public void init(Controller controller) throws RemoteException {
        this.controller = controller;
        this.sessionView = new SessionView(this.controller);
        this.pixelGridView = new PixelGridView(new PixelGridImpl(40,40), new BrushDrawerImpl(this.controller.getModel().getBrushManager()), 600, 600, this);
//        this.pixelGridView = new PixelGridView(this.controller.getModel().getStateShared().getPixelGrid(), new BrushDrawerImpl(this.controller.getModel().getBrushManager()), 600, 600, this);
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setPixelGridView(PixelGridView pixelGridView) {
        this.pixelGridView = pixelGridView;
    }

    @Override
    public void display() {
        this.sessionView.display();
        pixelGridView.addMouseMovedListener((x, y) -> {
            controller.updateLocalBrush(x, y);
            this.refresh();
        });

        pixelGridView.addPixelGridEventListener((x, y) -> {
            controller.updatePixelGrid(x, y);
            this.refresh();
        });
        pixelGridView.addColorChangedListener(controller::updateLocalBrushColor);
    }

    @Override
    public void refresh() {
        this.pixelGridView.refresh();
    }

    @Override
    public void changeFrame() {
        this.sessionView.setVisible(false);
        this.pixelGridView.setVisible(true);
        this.pixelGridView.display();
    }
}
