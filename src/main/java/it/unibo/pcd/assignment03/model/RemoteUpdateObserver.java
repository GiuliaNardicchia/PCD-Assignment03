package it.unibo.pcd.assignment03.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUpdateObserver extends Remote {
    void notifyUpdate() throws RemoteException;
}
