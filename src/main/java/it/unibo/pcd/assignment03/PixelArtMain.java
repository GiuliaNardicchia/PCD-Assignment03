package it.unibo.pcd.assignment03;

import it.unibo.pcd.assignment03.model.Brush;
import it.unibo.pcd.assignment03.model.BrushImpl;
import it.unibo.pcd.assignment03.model.BrushManager;
import it.unibo.pcd.assignment03.model.PixelGrid;
import it.unibo.pcd.assignment03.view.PixelGridView;
import it.unibo.pcd.assignment03.view.ViewImpl;

import java.util.Random;

import static it.unibo.pcd.assignment03.utils.Utils.randomColor;

public class PixelArtMain {

    public static void main(String[] args) {
//        Brush localBrush = new BrushImpl(0, 0, randomColor());
//        View view = new ViewImpl(localBrush);
//        PixelGrid grid = new PixelGrid(40, 40);
//
//        Random rand = new Random();
//        for (int i = 0; i < 10; i++) {
//            grid.set(rand.nextInt(40), rand.nextInt(40), randomColor());
//        }
//
//        PixelGridView view = new PixelGridView(grid, brushManager, 600, 600);
//
//        view.addMouseMovedListener((x, y) -> {
//            localBrush.updatePosition(x, y);
//            view.refresh();
//        });
//
//        view.addPixelGridEventListener((x, y) -> {
//            grid.set(x, y, localBrush.getColor());
//            view.refresh();
//        });
//
//        view.addColorChangedListener(localBrush::setColor);
//
//        view.display();
    }

}
