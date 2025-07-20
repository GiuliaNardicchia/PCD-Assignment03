package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModelStateSharedImpl implements ModelStateShared {

    private final PixelGrid grid;
    private final BrushManager brushManager;
    private final List<RemoteUpdateObserver> observers = new CopyOnWriteArrayList<>();

    public ModelStateSharedImpl(PixelGrid grid, BrushManager brushManager) throws RemoteException {
        this.grid = grid;
        this.brushManager = brushManager;
    }

    @Override
    public synchronized void setGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException {
        this.grid.set(gridCellUpdate.x(), gridCellUpdate.y(), gridCellUpdate.color());
        // Notify observers about the grid cell update
        for (RemoteUpdateObserver observer : observers) {
            observer.notifyUpdate();
        }
    }

    @Override
    public synchronized PixelGrid getPixelGrid() throws RemoteException {
        return this.grid;
    }

    @Override
    public synchronized BrushManager getBrushManager() throws RemoteException {
        return this.brushManager;
    }

    @Override
    public synchronized void addBrush(Brush localBrush) throws RemoteException {
        this.brushManager.addBrush(localBrush);
        // Notify observers about the new brush
        for (RemoteUpdateObserver observer : observers) {
            observer.notifyUpdate();
        }
    }

    @Override
    public synchronized Set<Brush> getBrushes() throws RemoteException {
        return this.brushManager.getBrushes();
    }

    @Override
    public synchronized void updateBrush(Brush localBrush, int x, int y, int color) throws RemoteException {
        this.brushManager.updateBrush(localBrush, x, y, color);
        // Notify observers about the brush update
        for (RemoteUpdateObserver observer : observers) {
            observer.notifyUpdate();
        }
    }

    @Override
    public synchronized void addListeners(RemoteUpdateObserver remoteUpdateObserver) throws RemoteException {
        this.observers.add(remoteUpdateObserver);
    }

    @Override
    public synchronized void removeListeners(RemoteUpdateObserver remoteUpdateObserver) throws RemoteException {
        this.observers.remove(remoteUpdateObserver);
    }

    @Override
    public synchronized void removeBrush(Brush localBrush) throws RemoteException {
        this.brushManager.removeBrush(localBrush);
        // Notify observers about the brush removal
        for (RemoteUpdateObserver observer : observers) {
            observer.notifyUpdate();
        }

    }

}
