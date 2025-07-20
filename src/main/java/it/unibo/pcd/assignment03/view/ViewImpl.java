package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;
import it.unibo.pcd.assignment03.model.PixelGridImpl;

import java.rmi.RemoteException;

public class ViewImpl implements View {
    private Controller controller;
    private SessionView sessionView;
    private PixelGridView pixelGridView;

    @Override
    public void init(Controller controller) {
        this.controller = controller;
        this.sessionView = new SessionView(this.controller);
        this.pixelGridView = new PixelGridView(this, 600, 600);
    }

    @Override
    public Controller getController() {
        return this.controller;
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
        this.pixelGridView.refresh();
    }

    @Override
    public void changeFrame() {
        this.sessionView.setVisible(false);
        this.sessionView.close();
        this.pixelGridView.setVisible(true);
        this.pixelGridView.display();
    }
}
