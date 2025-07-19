package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.*;
import it.unibo.pcd.assignment03.view.BrushDrawerImpl;
import it.unibo.pcd.assignment03.view.PixelGridView;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerImpl implements Controller, Serializable {

    public static final String REGISTRY_BINDING_NAME = "peerRegistry";
    private static final String CONTROLLER_BINDING_NAME = "controller";
    private static final String MODEL_BINDING_NAME = "modelStateShared";
    private Model model;
    private View view;
    private final String host;
    ControllerRemote controllerRemote;

    private boolean receivedGrid = false;
    private boolean receivedBrushes = false;

    public ControllerImpl(String host) throws RemoteException {
        this.host = host;
    }

    @Override
    public void init(View view, Model model) {
        this.view = view;
        this.model = model;
        this.controllerRemote = new ControllerRemoteImpl();
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
//        this.view.setPixelGridView(new PixelGridView(this.model.getStateShared().getPixelGrid(), new BrushDrawerImpl(this.model.getBrushManager()), 600, 600, this.view));
        //this.model.getStateShared().updateGridCell(new GridCellUpdateMessage(x, y, this.model.getLocalBrush().getColor()));
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
    public void sendGoodbyeMessage() {
//        String message = gson.toJson(this.model.getLocalBrush());
    }

    @Override
    public void createSession(String sessionId, String host, int port) throws RemoteException {
        ModelStateShared modelStateShared = new ModelStateSharedImpl(new PixelGridImpl(this.model.getNumRows(), this.model.getNumCols()), new BrushManagerImpl());
        ModelStateShared modelStateSharedStub = (ModelStateShared) UnicastRemoteObject.exportObject(modelStateShared, 0);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(MODEL_BINDING_NAME, modelStateSharedStub);
        this.model.setStateShared(modelStateShared);
        this.view.changeFrame();

//        this.view.setPixelGridView(new PixelGridView(new BrushDrawerImpl(this.model.getBrushManager()), 600, 600, this.view));


//        this.model.getStateShared().printCounter();
//        this.model.getStateShared().setCounter(1, new SerializableConsumer<>());
//        this.model.getStateShared().printCounter();
    }

    @Override
    public void joinSession(String sessionId, String host, int port) throws RemoteException {
        this.view.changeFrame();
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            ModelStateShared modelStateShared = (ModelStateShared) registry.lookup(MODEL_BINDING_NAME);
            this.model.setStateShared(modelStateShared);
            System.out.println("Joined session with model state shared.");
//            Map<String, PeerInfo> peers = modelStateShared.getPeerRegistryService();
//            for (Map.Entry<String, PeerInfo> entry : peers.entrySet()) {
//                PeerInfo peerInfo = entry.getValue();
//                try {
//                    Registry peerRegistry = LocateRegistry.getRegistry(peerInfo.getHost(), peerInfo.getPort());
//                    ControllerRemote peer = (ControllerRemote) peerRegistry.lookup(CONTROLLER_BINDING_NAME);
//                    this.controllerRemote.addPeer(peer);
//                    peer.addPeer(this.controllerRemote);
//                    peer.printHello();
//                    System.out.println("Connesso a peer " + entry.getKey() + " su " + peerInfo.getHost() + ":" + peerInfo.getPort());
//                } catch (Exception e) {
//                    System.out.println("Errore nel connettersi a peer " + entry.getKey());
//                    e.printStackTrace();
//                }
//            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void createSession(String sessionId, String host, int port) throws RemoteException {
//        this.view.changeFrame();
//
//        SessionService service = new SessionServiceImpl();
//        service.addPeer(sessionId, new PeerInfo(host, port));
//        SessionService sessionServiceStub = (SessionService) UnicastRemoteObject.exportObject(service, 0);
//        ControllerRemote controllerRemoteStub = (ControllerRemote) UnicastRemoteObject.exportObject(this.controllerRemote, 0);
//        // Avvia un registry RMI (porta 1099)
////        Registry registry = LocateRegistry.getRegistry();
//        Registry registry = LocateRegistry.createRegistry(port);
//        registry.rebind(REGISTRY_BINDING_NAME, sessionServiceStub);
//        registry.rebind(CONTROLLER_BINDING_NAME, controllerRemoteStub);
//
//        Map<String, PeerInfo> peers = sessionServiceStub.getPeerRegistryService();
//        for (Map.Entry<String, PeerInfo> entry : peers.entrySet()) {
//            String peerId = entry.getKey();
//            PeerInfo peerInfo = entry.getValue();
//            System.out.println("Peer ID: " + peerId + ", Host: " + peerInfo.getHost() + ", Port: " + peerInfo.getPort());
//        }
//        System.out.println("Peer registry ready.");
//    }
//
//    @Override
//    public void joinSession(String sessionId, String host, int port) {
//        this.view.changeFrame();
//
//        try {
//            Registry registry = LocateRegistry.getRegistry(host);
////            ControllerRemote controllerRemoteStub = (ControllerRemote) UnicastRemoteObject.exportObject(this.controllerRemote, 0);
////            registry.rebind(CONTROLLER_BINDING_NAME, controllerRemoteStub);
//            SessionService sessionService = (SessionService) registry.lookup(REGISTRY_BINDING_NAME);
//            Map<String, PeerInfo> peers = sessionService.getPeerRegistryService();
//            for (Map.Entry<String, PeerInfo> entry : peers.entrySet()) {
//                PeerInfo peerInfo = entry.getValue();
//                try {
//                    Registry peerRegistry = LocateRegistry.getRegistry(peerInfo.getHost(), peerInfo.getPort());
//                    ControllerRemote peer = (ControllerRemote) peerRegistry.lookup(CONTROLLER_BINDING_NAME);
//                    this.controllerRemote.addPeer(peer);
//                    peer.addPeer(this.controllerRemote);
//                    peer.printHello();
//                    System.out.println("Connesso a peer " + entry.getKey() + " su " + peerInfo.getHost() + ":" + peerInfo.getPort());
//                } catch (Exception e) {
//                    System.out.println("Errore nel connettersi a peer " + entry.getKey());
//                    e.printStackTrace();
//                }
//            }
//        } catch (RemoteException | NotBoundException e) {
//            e.printStackTrace();
//        }
//    }
}