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
        // new BrushDrawerImpl(this.controller.getModel().getBrushManager())
        this.pixelGridView = new PixelGridView(this, 600, 600);
//        this.pixelGridView = new PixelGridView(this.controller.getModel().getStateShared().getPixelGrid(), new BrushDrawerImpl(this.controller.getModel().getBrushManager()), 600, 600, this);
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setPixelGridView(PixelGridView pixelGridView) {
        System.out.println("setPixelGridView " + pixelGridView);
        this.pixelGridView = pixelGridView;
    }

    @Override
    public void display() {
        this.sessionView.display();

        this.pixelGridView.addMouseMovedListener((x, y) -> {
            controller.updateLocalBrush(x, y);
            this.refresh();
        });

        this.pixelGridView.addPixelGridEventListener((x, y) -> {
            controller.updatePixelGrid(x, y);
            System.out.println("Update pixel cell to: " + x + ", " + y);
            this.refresh();
        });
        this.pixelGridView.addColorChangedListener(controller::updateLocalBrushColor);
    }

    @Override
    public void refresh() {
//        System.out.println(pixelGridView);
        this.pixelGridView.refresh();
    }

    @Override
    public void changeFrame() {
        this.sessionView.setVisible(false);

        System.out.println("changeFrame " + pixelGridView);
        this.pixelGridView.setVisible(true);
        this.pixelGridView.display();
    }
}
