package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.*;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ControllerImpl implements Controller, Serializable {
    private Model model;
    private View view;
    private final String host;

    private boolean receivedGrid = false;
    private boolean receivedBrushes = false;

    // this.model.updatePixelGrid(gridCell.x, gridCell.y, gridCell.color);
    // this.model.updateBrushes(brush);
    // this.view.refresh();
    // this.model.setBrushes(brushSet);
    // this.model.updateGridFromSource(pixelGrid);
    // this.model.getBrushManager().removeBrush(brush);

    public ControllerImpl(String host) throws RemoteException {
        this.host = host;

//            BrushManager brushManagerReceived = (BrushManagerImpl) registry.lookup("brushManager");
//            PixelGrid pixelGridReceived = (PixelGridImpl) registry.lookup("pixelGrid");
    }

    @Override
    public void init(View view, Model model) throws IOException {
        this.view = view;
        this.model = model;

//        try {
//            BrushManager brushManager = new BrushManagerImpl();
//            BrushManager brushManagerStub = (BrushManagerImpl) UnicastRemoteObject.exportObject(brushManager, 0);
//            PixelGrid pixelGrid = new PixelGridImpl(600, 600);
//            PixelGrid pixelGridStub = (PixelGridImpl) UnicastRemoteObject.exportObject(pixelGrid, 0);
//
//            Registry registry = LocateRegistry.getRegistry(host);
//            registry.rebind("brushManager", brushManagerStub);
//            registry.rebind("pixelGrid", pixelGridStub);
//
//            this.model.setBrushes(brushManager.getBrushes());
//            this.model.setGrid(pixelGrid);
//            System.out.println("Objects registered.");
//        } catch (Exception e) {
//            System.err.println("Server exception: " + e.toString());
//            e.printStackTrace();
//        }
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public void updateLocalBrush(int x, int y) throws RemoteException {
        this.model.updateLocalBrush(x, y);
        sendLocalBrushInfo();
    }

    @Override
    public void updatePixelGrid(int x, int y) throws RemoteException {
        this.model.updatePixelGrid(x, y, this.model.getLocalBrush().getColor());
        this.view.refresh();
    }

    @Override
    public void updateLocalBrushColor(int color) throws RemoteException {
        this.model.updateLocalBrushColor(color);
        this.sendLocalBrushInfo();
    }

    private void sendLocalBrushInfo() {
//        String message = gson.toJson(this.model.getLocalBrush());
    }

    @Override
    public void start() throws IOException {
        this.view.display();
//        this.model.getLocalBrush());
    }

    @Override
    public void sendGoodbyeMessage() throws IOException {
//        String message = gson.toJson(this.model.getLocalBrush());
    }

    @Override
    public void joinSession(String sessionId) throws RemoteException {
        this.view.changeFrame();
    }

    @Override
    public void createSession(String sessionId) throws RemoteException {
        this.view.changeFrame();
    }
}
