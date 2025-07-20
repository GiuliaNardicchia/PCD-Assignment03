package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.BrushManager;
import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.model.PixelGrid;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Controller extends Remote {
    void init(View view, Model model) throws IOException, RemoteException;

    Model getModel() throws RemoteException;

    View getView() throws RemoteException;

    void updateLocalBrush(int x, int y) throws RemoteException;

    void updatePixelGrid(int x, int y) throws RemoteException;

    void updateLocalBrushColor(int color) throws RemoteException;

    void start() throws IOException;

    void sendGoodbyeMessage() throws IOException;

    void createSession(String sessionId, String host, int port) throws RemoteException;

    void joinSession(String sessionId, String host, int port) throws RemoteException, NotBoundException;

}
