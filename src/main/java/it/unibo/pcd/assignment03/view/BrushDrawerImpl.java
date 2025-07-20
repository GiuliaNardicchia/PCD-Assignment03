package it.unibo.pcd.assignment03.view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;

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
            try {
                g.setColor(new Color(brush.getColor()));
                Ellipse2D.Double circle = null;
                circle = new Ellipse2D.Double(brush.getX() - BRUSH_SIZE / 2.0, brush.getY() - BRUSH_SIZE / 2.0,
                        BRUSH_SIZE, BRUSH_SIZE);
                g.fill(circle);
                g.setStroke(new BasicStroke(STROKE_SIZE));
                g.setColor(Color.BLACK);
                g.draw(circle);
            } catch (RemoteException ignored) {
            }
        });
    }
}
