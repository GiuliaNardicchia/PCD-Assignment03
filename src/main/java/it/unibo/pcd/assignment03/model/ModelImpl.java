package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.controller.Controller;

import java.rmi.RemoteException;
import java.util.Objects;
import java.util.Set;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class ModelImpl implements Model {
    private Controller controller;
    private ModelStateShared stateShared;
    private final BrushManager brushManager = new BrushManagerImpl();
//    private PixelGrid grid;
    private final Brush localBrush;

    public ModelImpl(int numRows, int numCols) throws RemoteException {
        Brush localBrush = new BrushImpl(0, 0, randomColor());
        this.brushManager.addBrush(localBrush);
        this.localBrush = localBrush;
//        this.grid = new PixelGridImpl(numRows, numCols);
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
    public BrushManager getBrushManager() {
        return this.brushManager;
    }

//    @Override
//    public PixelGrid getGrid() {
//        return this.grid;
//    }

    @Override
    public ModelStateShared getStateShared() {
        return stateShared;
    }

    @Override
    public void updateLocalBrush(int x, int y) throws RemoteException {
        this.localBrush.updatePosition(x, y);
    }

//    @Override
//    public void updatePixelGrid(int x, int y, int color) throws RemoteException {
//        this.grid.set(x, y, color);
//    }

    @Override
    public void updateLocalBrushColor(int color) throws RemoteException {

        this.localBrush.setColor(color);
    }

//    @Override
//    public void setGrid(PixelGrid grid){
//        this.grid = grid;
//    }

//    @Override
//    public void updateGridFromSource(PixelGrid sourceGrid) throws RemoteException {
//        this.grid.setGrid(sourceGrid.getGrid());
//    }

    @Override
    public void setBrushes(Set<Brush> brushes) throws RemoteException {
        brushes.add(this.localBrush);
        this.brushManager.setBrushes(brushes);
    }

    @Override
    public void setStateShared(ModelStateShared stateShared) {
        this.stateShared = stateShared;
    }

    @Override
    public void updateBrushes(Brush brush) throws RemoteException {
        // TODO
        this.brushManager.getBrushes().stream()
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
                                this.brushManager.getBrushes().add(brush);
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
}
