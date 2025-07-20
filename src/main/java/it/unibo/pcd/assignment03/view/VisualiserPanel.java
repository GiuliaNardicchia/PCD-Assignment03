package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.model.PixelGrid;

import javax.swing.*;
import java.awt.*;

public class VisualiserPanel extends JPanel {
    private static final int STROKE_SIZE = 1;
    private final BrushDrawer brushDrawer;
    private final int w, h;
    private final View view;

    public VisualiserPanel(View view, int w, int h) {
        setSize(w, h);
        this.view = view;
        this.w = w;
        this.h = h;
        this.brushDrawer = new BrushDrawerImpl(view);
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

        try {
            PixelGrid grid = view.getController().getModel().getGrid();
            int numRows = grid.getNumRows();
            int numCols = grid.getNumColumns();

            int dx = w / numCols;
            int dy = h / numRows;

            g2.setStroke(new BasicStroke(STROKE_SIZE));

            for (int i = 0; i < numRows; i++) {
                int y = i * dy;
                g2.drawLine(0, y, w, y);
            }
            for (int i = 0; i < numCols; i++) {
                int x = i * dx;
                g2.drawLine(x, 0, x, h);
            }

            for (int row = 0; row < numRows; row++) {
                int y = row * dy;
                for (int column = 0; column < numCols; column++) {
                    int x = column * dx;
                    int color = view.getController().getModel().getGrid().get(column, row);
                    if (color != 0) {
                        g2.setColor(new Color(color));
                        g2.fillRect(x + STROKE_SIZE, y + STROKE_SIZE, dx - STROKE_SIZE, dy - STROKE_SIZE);
                    }
                }
            }

            brushDrawer.draw(g2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
