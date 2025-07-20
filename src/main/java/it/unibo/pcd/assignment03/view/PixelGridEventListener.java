package it.unibo.pcd.assignment03.view;

import java.rmi.RemoteException;

public interface PixelGridEventListener {
    void selectedCell(int x, int y) throws RemoteException;
}
