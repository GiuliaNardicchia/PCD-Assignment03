package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.BrushManager;
import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.model.PixelGrid;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Controller extends Remote {
    void init(View view, Model model) throws IOException, RemoteException;

    Model getModel() throws RemoteException;

    View getView() throws RemoteException;

    void updateLocalBrush(int x, int y) throws RemoteException;

    void updatePixelGrid(int x, int y) throws RemoteException;

    void updateLocalBrushColor(int color) throws RemoteException;

    void start() throws IOException, RemoteException;

    void sendGoodbyeMessage() throws IOException;

    void joinSession(String sessionId) throws RemoteException;

    void createSession(String sessionId) throws RemoteException;

//    void addPeer(ControllerRemote peer) throws RemoteException;
//    void updateGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException;
//    void updateBrushManager(BrushManager brushManager) throws RemoteException;
//    void updatePixelGrid(PixelGrid pixelGrid) throws RemoteException;
}
