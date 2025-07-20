package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.*;
import it.unibo.pcd.assignment03.view.View;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ControllerImpl implements Controller, Serializable {

    private static final String MODEL_BINDING_NAME = "modelStateShared";
    private Model model;
    private View view;
    private RemoteUpdateObserver remoteUpdateObserver;

    public ControllerImpl() throws RemoteException {
    }

    @Override
    public void init(View view, Model model) throws RemoteException {
        this.view = view;
        this.model = model;
        this.remoteUpdateObserver = new RemoteUpdateObserverImpl(view);
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
    }

    @Override
    public void updatePixelGrid(int x, int y) throws RemoteException {
        this.model.updatePixelGrid(x, y, this.model.getLocalBrush().getColor());
        this.view.refresh();
    }

    @Override
    public void updateLocalBrushColor(int color) throws RemoteException {
        this.model.updateLocalBrushColor(color);
    }

    @Override
    public void start() {
        this.view.display();
    }

    @Override
    public void leaveSession() {
        try {
            this.model.leaveSession();
            this.model.getStateShared().removeListeners(this.remoteUpdateObserver);
        } catch (RemoteException ignored) {
        }
    }

    @Override
    public void createSession(String sessionId, String host, int port) throws RemoteException {
        ModelStateShared modelStateShared = new ModelStateSharedImpl(
                new PixelGridImpl(this.model.getNumRows(), this.model.getNumCols()), new BrushManagerImpl());
        ModelStateShared modelStateSharedStub = (ModelStateShared) UnicastRemoteObject.exportObject(modelStateShared,
                0);
        Registry registry = LocateRegistry.getRegistry(host, port);
        registry.rebind(MODEL_BINDING_NAME, modelStateSharedStub);
        this.model.setStateShared(modelStateShared);
        this.model.getStateShared().addListeners(this.remoteUpdateObserver);
        this.view.changeFrame();
    }

    @Override
    public void joinSession(String sessionId, String host, int port) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            ModelStateShared modelStateShared = (ModelStateShared) registry.lookup(MODEL_BINDING_NAME);
            this.model.setStateShared(modelStateShared);
            this.model.getStateShared().addListeners(this.remoteUpdateObserver);
            System.out.println("Joined session with model state shared.");
            this.view.changeFrame();
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Error joining session: " + e.getMessage());
            System.exit(1);
        }
    }
}