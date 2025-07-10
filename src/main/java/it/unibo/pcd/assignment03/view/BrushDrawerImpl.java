package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.model.Brush;

import java.awt.*;
import java.util.Set;

public class BrushDrawerImpl implements BrushDrawer {
    private static final int BRUSH_SIZE = 10;
    private static final int STROKE_SIZE = 2;

    @Override
    public void draw(Graphics2D g, Set<Brush> brushes) {
        brushes.forEach(brush -> {
            g.setColor(new java.awt.Color(brush.getColor()));
            var circle = new java.awt.geom.Ellipse2D.Double(brush.getX() - BRUSH_SIZE / 2.0, brush.getY() - BRUSH_SIZE / 2.0, BRUSH_SIZE, BRUSH_SIZE);
            // draw the polygon
            g.fill(circle);
            g.setStroke(new BasicStroke(STROKE_SIZE));
            g.setColor(Color.BLACK);
            g.draw(circle);
        });
    }
}
