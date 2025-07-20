package it.unibo.pcd.assignment03.view;

import java.awt.*;
import java.rmi.RemoteException;

public interface BrushDrawer {
    void draw(final Graphics2D g) throws RemoteException;
}
