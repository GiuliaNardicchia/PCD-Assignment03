package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

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

    public ModelImpl(int numRows, int numCols) throws RemoteException {
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
        this.localBrush.updatePosition(x, y);
    }

    @Override
    public void updatePixelGrid(int x, int y, int color) throws RemoteException {
        this.stateShared.getPixelGrid().set(x, y, color);
    }

    @Override
    public void updateLocalBrushColor(int color) throws RemoteException {
        this.localBrush.setColor(color);
    }

    @Override
    public void setGrid(PixelGrid grid) throws RemoteException {
        this.stateShared.getPixelGrid().setGrid(grid.getGrid());
    }

    @Override
    public void updateGridFromSource(PixelGrid sourceGrid) throws RemoteException {
        this.stateShared.getPixelGrid().setGrid(sourceGrid.getGrid());
    }

    @Override
    public void setBrushes(Set<Brush> brushes) throws RemoteException {
        brushes.add(this.localBrush);
        this.stateShared.getBrushManager().setBrushes(brushes);
    }

    @Override
    public void setStateShared(ModelStateShared stateShared) throws RemoteException {
        this.stateShared = stateShared;
        this.stateShared.getBrushManager().addBrush(this.localBrush);
    }

    @Override
    public void updateBrushes(Brush brush) throws RemoteException {
        // TODO
        this.stateShared.getBrushManager().getBrushes().stream()
                .filter(b -> {
                    try {
                        return Objects.equals(b.getId(), brush.getId());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst()
                .ifPresentOrElse(
                        b -> {
                            try {
                                b.updatePosition(brush.getX(), brush.getY());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                b.setColor(brush.getColor());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            try {
                                this.stateShared.getBrushManager().getBrushes().add(brush);
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
}
