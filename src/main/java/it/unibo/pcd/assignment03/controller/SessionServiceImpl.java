package it.unibo.pcd.assignment03.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionServiceImpl implements SessionService, Serializable {

    Map<String, PeerInfo> peerRegistryService = new ConcurrentHashMap<String, PeerInfo>();

    @Override
    public void addPeer(String id, PeerInfo peerInfo) throws RemoteException {
        this.peerRegistryService.put(id, peerInfo);
    }

    @Override
    public void removePeer(String id) throws RemoteException {
        this.peerRegistryService.remove(id);
    }

    @Override
    public Map<String, PeerInfo> getPeerRegistryService() throws RemoteException {
        return new HashMap<>(this.peerRegistryService);
    }
}
