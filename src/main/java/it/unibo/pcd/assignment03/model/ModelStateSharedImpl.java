package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.RemoteException;

public class ModelStateSharedImpl implements ModelStateShared {

//    private int counter;
    private PixelGrid grid;
    private BrushManager brushManager;

    public ModelStateSharedImpl(PixelGrid grid, BrushManager brushManager) throws RemoteException {
        this.grid = grid;
        this.brushManager = brushManager;
    }

//    @Override
//    public void setCounter(int counter, SerializableConsumer<Integer> andThen) throws RemoteException {
//        this.counter = counter;
//        andThen.accept(this.counter);
//    }
//
//    @Override
//    public int getCounter() throws RemoteException {
//        return this.counter;
//    }
//    @Override
//    public void printCounter() throws RemoteException {
//        System.out.println("Counter value: " + this.counter);
//    }

    @Override
    public void setGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException {
        this.grid.set(gridCellUpdate.getX(), gridCellUpdate.getY(), gridCellUpdate.getColor());
        System.out.println("Updated cell at (" + gridCellUpdate.getX() + ", " + gridCellUpdate.getY() + ") to color " + gridCellUpdate.getColor());
    }

    @Override
    public PixelGrid getPixelGrid() throws RemoteException {
        return this.grid;
    }

    @Override
    public void setPixelGrid(PixelGridImpl pixelGrid) throws RemoteException {
        this.grid = pixelGrid;
    }

    @Override
    public BrushManager getBrushManager() throws RemoteException {
        return this.brushManager;
    }

    @Override
    public void setBrushManager(BrushManager brushManager) throws RemoteException {
        this.brushManager = brushManager;
    }
}
