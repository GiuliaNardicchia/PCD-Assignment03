package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.function.Consumer;

public interface ModelStateShared extends Remote {

    void setCounter(int counter, SerializableConsumer<Integer> andThen) throws RemoteException;
    int getCounter() throws RemoteException;
    void printCounter() throws RemoteException;
    void updateGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException;

    PixelGrid getPixelGrid() throws RemoteException;
//    void updateBrushManager(BrushManager brushManager) throws RemoteException;
//    void updatePixelGrid(PixelGrid pixelGrid) throws RemoteException;
}
