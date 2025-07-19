package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;
import it.unibo.pcd.assignment03.controller.GridCellUpdateMessage;

import java.rmi.RemoteException;
import java.util.Objects;
import java.util.Set;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class ModelImpl implements Model {
    private Controller controller;
    private ModelStateShared stateShared;
    private final Brush localBrush;
    private final int numRows;
    private final int numCols;

    public ModelImpl(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.localBrush = new BrushImpl(0, 0, randomColor());
    }

    @Override
    public void init(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public Brush getLocalBrush() {
        return localBrush;
    }

    @Override
    public PixelGrid getGrid() throws RemoteException {
        return this.stateShared.getPixelGrid();
    }

    @Override
    public ModelStateShared getStateShared() {
        return stateShared;
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public int getNumRows() {
        return numRows;
    }

    @Override
    public void updateLocalBrush(int x, int y) throws RemoteException {
        this.stateShared.updateBrushPosition(this.localBrush, x, y);
    }

    @Override
    public void updatePixelGrid(int x, int y, int color) throws RemoteException {
        this.stateShared.setGridCell(new GridCellUpdateMessage(x, y, color));
    }

    @Override
    public void updateLocalBrushColor(int color) throws RemoteException {
        this.localBrush.setColor(color);
    }

    @Override
    public synchronized void setStateShared(ModelStateShared stateShared) throws RemoteException {
        this.stateShared = stateShared;
        System.out.println("Setting local brush in state shared: " + this.localBrush);
        this.stateShared.addBrush(this.localBrush);
        this.stateShared.getBrushes().forEach(b -> System.out.println("Brush in state shared: " + b));
    }

}
