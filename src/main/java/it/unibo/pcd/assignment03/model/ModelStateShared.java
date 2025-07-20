package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface ModelStateShared extends Remote {

    void setGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException;

    PixelGrid getPixelGrid() throws RemoteException;

    BrushManager getBrushManager() throws RemoteException;

    void addBrush(Brush localBrush) throws RemoteException;

    Set<Brush> getBrushes() throws RemoteException;

    void updateBrush(Brush localBrush, int x, int y, int color) throws RemoteException;

    void addListeners(RemoteUpdateObserver remoteUpdateObserver) throws RemoteException;

    void removeListeners(RemoteUpdateObserver remoteUpdateObserver) throws RemoteException;
}
