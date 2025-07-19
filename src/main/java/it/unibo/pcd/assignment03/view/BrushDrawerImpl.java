package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.model.Brush;
import it.unibo.pcd.assignment03.model.BrushManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;
import java.util.Set;

public class BrushDrawerImpl implements BrushDrawer {
    private static final int BRUSH_SIZE = 10;
    private static final int STROKE_SIZE = 2;
    private final View view;

    public BrushDrawerImpl(View view) {
        this.view = view;
    }

    @Override
    public void draw(Graphics2D g) throws RemoteException {
        this.view.getController().getModel().getStateShared().getBrushManager().getBrushes().forEach(brush -> {
            // TODO
            try {
                g.setColor(new Color(brush.getColor()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Ellipse2D.Double circle = null;
            try {
                circle = new Ellipse2D.Double(brush.getX() - BRUSH_SIZE / 2.0, brush.getY() - BRUSH_SIZE / 2.0, BRUSH_SIZE, BRUSH_SIZE);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            g.fill(circle);
            g.setStroke(new BasicStroke(STROKE_SIZE));
            g.setColor(Color.BLACK);
            g.draw(circle);
        });
    }
}
