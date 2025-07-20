package it.unibo.pcd.assignment03.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface RemoteUpdateObserver extends Remote {
    void update(String message) throws RemoteException;
}
