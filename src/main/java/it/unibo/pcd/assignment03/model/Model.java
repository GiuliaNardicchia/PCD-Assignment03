package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.rmi.RemoteException;
import java.util.Set;

public interface Model {
    void init(Controller controller) throws RemoteException;

    Controller getController() throws RemoteException;

    Brush getLocalBrush() throws RemoteException;

    BrushManager getBrushManager() throws RemoteException;

    PixelGrid getGrid() throws RemoteException;

    void updateLocalBrush(int x, int y) throws RemoteException;

    void updatePixelGrid(int x, int y,  int color) throws RemoteException;

    void updateLocalBrushColor(int color) throws RemoteException;

    void setGrid(PixelGrid grid) throws RemoteException;

    void updateGridFromSource(PixelGrid sourceGrid) throws RemoteException;

    void setBrushes(Set<Brush> brushes) throws RemoteException;

    void updateBrushes(Brush brush) throws RemoteException;
}
