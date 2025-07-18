package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.model.PixelGrid;
import it.unibo.pcd.assignment03.model.PixelGridImpl;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class VisualiserPanel extends JPanel {
    private static final int STROKE_SIZE = 1;
    private final BrushDrawer brushDrawer;
    private final PixelGrid grid;
    private final int w, h;

    public VisualiserPanel(PixelGrid grid, BrushDrawer brushDrawer, int w, int h) {
        setSize(w, h);
        this.grid = grid;
        this.w = w;
        this.h = h;
        this.brushDrawer = brushDrawer;
        this.setPreferredSize(new Dimension(w, h));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        //TODO
        int dx = 0;
        try {
            dx = w / grid.getNumColumns();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        int dy = 0;
        try {
            dy = h / grid.getNumRows();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        g2.setStroke(new BasicStroke(STROKE_SIZE));
        //TODO
        try {
            for (int i = 0; i < grid.getNumRows(); i++) {
                int y = i * dy;
                g2.drawLine(0, y, w, y);
            }
            for (int i = 0; i < grid.getNumColumns(); i++) {
                int x = i * dx;
                g2.drawLine(x, 0, x, h);
            }

            for (int row = 0; row < grid.getNumRows(); row++) {
                int y = row * dy;
                for (int column = 0; column < grid.getNumColumns(); column++) {
                    int x = column * dx;
                    int color = grid.get(column, row);
                    if (color != 0) {
                        g2.setColor(new Color(color));
                        g2.fillRect(x + STROKE_SIZE, y + STROKE_SIZE, dx - STROKE_SIZE, dy - STROKE_SIZE);
                    }
                }
            }

            brushDrawer.draw(g2);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
