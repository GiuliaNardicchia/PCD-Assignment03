package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.BrushManager;
import it.unibo.pcd.assignment03.model.PixelGrid;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ControllerRemote extends Remote {
    void addPeer(ControllerRemote peer) throws RemoteException;
    List<ControllerRemote> getPeers() throws RemoteException;
    void printHello() throws RemoteException;
//    void removePeer(ControllerRemote peer) throws RemoteException;
//    void updateGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException;
//    void updateBrushManager(BrushManager brushManager) throws RemoteException;
//    void updatePixelGrid(PixelGrid pixelGrid) throws RemoteException;
}
