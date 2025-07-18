package it.unibo.pcd.assignment03.model;

import java.rmi.RemoteException;
import java.util.Set;
import java.rmi.Remote;

public interface BrushManager extends Remote {

    void addBrush(Brush brush) throws RemoteException;

    void removeBrush(Brush brush) throws RemoteException;

    Set<Brush> getBrushes() throws RemoteException;

    void setBrushes(Set<Brush> brushes) throws RemoteException;
}
