package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ModelStateShared extends Remote {

//    void setCounter(int counter, SerializableConsumer<Integer> andThen) throws RemoteException;
//    int getCounter() throws RemoteException;
//    void printCounter() throws RemoteException;

    void setGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException;

    PixelGrid getPixelGrid() throws RemoteException;

    void setPixelGrid(PixelGridImpl pixelGrid) throws RemoteException;

    BrushManager getBrushManager() throws RemoteException;

    void setBrushManager(BrushManager brushManager) throws RemoteException;
}
