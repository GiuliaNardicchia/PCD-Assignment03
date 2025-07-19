package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.BrushManager;
import it.unibo.pcd.assignment03.model.PixelGrid;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ControllerRemoteImpl implements ControllerRemote, Serializable {

    private final List<ControllerRemote> controllersRemote = new ArrayList<>();
    private int x;

    @Override
    public void addPeer(ControllerRemote peer) throws RemoteException {
        this.controllersRemote.add(peer);
    }

    @Override
    public List<ControllerRemote> getPeers() throws RemoteException {
        return new ArrayList<>(this.controllersRemote);
    }

    @Override
    public void printHello() throws RemoteException {
        System.out.println(updateHelloMessage());
    }

    private int updateHelloMessage() {
        return this.x = this.x + 1;
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
