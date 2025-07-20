package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.rmi.RemoteException;
import java.util.Set;

public interface Model {
    void init(Controller controller) throws RemoteException;

    Controller getController() throws RemoteException;

    Brush getLocalBrush() throws RemoteException;

    PixelGrid getGrid() throws RemoteException;

    ModelStateShared getStateShared();

    int getNumCols();

    int getNumRows();

    void updateLocalBrush(int x, int y) throws RemoteException;

    void updatePixelGrid(int x, int y, int color) throws RemoteException;

    void updateLocalBrushColor(int color) throws RemoteException;

    void setStateShared(ModelStateShared stateShared) throws RemoteException;

    void leaveSession() throws RemoteException;
}
