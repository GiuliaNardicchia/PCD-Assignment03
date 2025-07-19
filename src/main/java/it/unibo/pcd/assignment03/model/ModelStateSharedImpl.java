package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.RemoteException;
import java.util.Set;

public class ModelStateSharedImpl implements ModelStateShared {

    private final PixelGrid grid;
    private final BrushManager brushManager;

    public ModelStateSharedImpl(PixelGrid grid, BrushManager brushManager) throws RemoteException {
        this.grid = grid;
        this.brushManager = brushManager;
    }

    @Override
    public synchronized void setGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException {
        this.grid.set(gridCellUpdate.getX(), gridCellUpdate.getY(), gridCellUpdate.getColor());
        System.out.println("Updated cell at (" + gridCellUpdate.getX() + ", " + gridCellUpdate.getY() + ") to color " + gridCellUpdate.getColor());
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
    }

    @Override
    public synchronized Set<Brush> getBrushes() throws RemoteException {
        return this.brushManager.getBrushes();
    }

    @Override
    public void updateBrush(Brush localBrush, int x, int y, int color) throws RemoteException {
        this.brushManager.updateBrush(localBrush, x, y, color);
    }

}
