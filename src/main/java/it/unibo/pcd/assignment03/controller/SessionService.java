package it.unibo.pcd.assignment03.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface SessionService extends Remote {

    void addPeer(String id, PeerInfo peerInfo) throws RemoteException;

    void removePeer(String id) throws RemoteException;

    Map<String, PeerInfo> getPeerRegistryService() throws RemoteException;
}
