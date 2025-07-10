package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.model.Brush;
import it.unibo.pcd.assignment03.model.BrushManager;

import java.awt.*;
import java.util.Set;

public interface BrushDrawer {
    void draw(final Graphics2D g, final Set<Brush> brushes);
}
