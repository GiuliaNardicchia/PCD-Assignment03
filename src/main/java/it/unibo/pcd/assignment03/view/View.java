package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;

import java.rmi.RemoteException;

public interface View {
    void init(Controller controller) throws RemoteException;

    Controller getController();

    void setPixelGridView(PixelGridView pixelGridView);

    void display();

    void refresh();

    void changeFrame();
}
