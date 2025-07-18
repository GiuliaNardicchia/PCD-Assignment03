package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.BrushManager;
import it.unibo.pcd.assignment03.model.PixelGrid;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ControllerRemoteImpl implements ControllerRemote, Serializable {

    private final List<ControllerRemote> controllersRemote = new ArrayList<>();

    @Override
    public void addPeer(ControllerRemote peer) throws RemoteException {
        this.controllersRemote.add(peer);
    }
//
//    @Override
//    public void removePeer(ControllerRemote peer) throws RemoteException {
//        this.controllersRemote.remove(peer);
//    }
//
//    @Override
//    public void updateGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException {
//
//    }
//
//    @Override
//    public void updateBrushManager(BrushManager brushManager) throws RemoteException {
//
//    }
//
//    @Override
//    public void updatePixelGrid(PixelGrid pixelGrid) throws RemoteException {
//
//    }
}
