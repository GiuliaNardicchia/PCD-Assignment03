package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.RemoteException;

public class ModelStateSharedImpl implements ModelStateShared {

    private static final int NUM_ROWS = 40;
    private static final int NUM_COLS = 40;

    private int counter;
    private PixelGrid grid = new PixelGridImpl(NUM_ROWS, NUM_COLS);;

    @Override
    public void setCounter(int counter, SerializableConsumer<Integer> andThen) throws RemoteException {
        this.counter = counter;
        andThen.accept(this.counter);
    }

    @Override
    public int getCounter() throws RemoteException {
        return this.counter;
    }
    @Override
    public void printCounter() throws RemoteException {
        System.out.println("Counter value: " + this.counter);
    }

    @Override
    public void updateGridCell(GridCellUpdateMessage gridCellUpdate) throws RemoteException {
        this.grid.set(gridCellUpdate.getX(), gridCellUpdate.getY(), gridCellUpdate.getColor());
        System.out.println("Updated cell at (" + gridCellUpdate.getX() + ", " + gridCellUpdate.getY() + ") to color " + gridCellUpdate.getColor());
    }

    @Override
    public PixelGrid getPixelGrid() throws RemoteException {
        return this.grid;
    }
}
