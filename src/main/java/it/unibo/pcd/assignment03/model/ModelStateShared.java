package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;
import it.unibo.pcd.assignment03.controller.PeerInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

public interface ModelStateShared extends Remote {

    void setGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException;

    PixelGrid getPixelGrid() throws RemoteException;

    BrushManager getBrushManager() throws RemoteException;

    void addBrush(Brush localBrush) throws RemoteException;

    Set<Brush> getBrushes() throws RemoteException;

    void updateBrushPosition(Brush localBrush, int x, int y) throws RemoteException;
}
