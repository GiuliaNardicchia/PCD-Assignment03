package it.unibo.pcd.assignment03.model;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Brush extends Remote {
    void updatePosition(final int x, final int y) throws RemoteException;

    int getX() throws RemoteException;

    int getY() throws RemoteException;

    int getColor() throws RemoteException;

    void setColor(int color) throws RemoteException;

    int getId() throws RemoteException;
}
