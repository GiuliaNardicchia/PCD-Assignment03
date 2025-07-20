package it.unibo.pcd.assignment03.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class PixelGridView extends JFrame {
    private final VisualiserPanel panel;
    private final int w, h;
    private final List<PixelGridEventListener> pixelListeners;
    private final List<MouseMovedListener> movedListener;
    private final View view;

    private final List<ColorChangeListener> colorChangeListeners;

    public PixelGridView(View view, int w, int h) {
        this.view = view;
        this.w = w;
        this.h = h;
        this.setSize(w, h);
        pixelListeners = new ArrayList<>();
        movedListener = new ArrayList<>();
        colorChangeListeners = new ArrayList<>();
        setTitle(".:: PixelArt ::.");
        setResizable(false);
        panel = new VisualiserPanel(this.view, w, h);
        panel.addMouseListener(createMouseListener());
        panel.addMouseMotionListener(createMotionListener());
        JButton colorChangeButton = getChangeColorButton();
        add(panel, BorderLayout.CENTER);
        add(colorChangeButton, BorderLayout.SOUTH);
        getContentPane().add(panel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("shutting down");
                try {
                    view.getController().leaveSession();
                } catch (IOException ignored) {
                }
                dispose();
            }
        });
        hideCursor();
    }

    private JButton getChangeColorButton() {
        var colorChangeButton = new JButton("Change color");
        colorChangeButton.addActionListener(
                e -> {
                    var color = JColorChooser.showDialog(this, "Choose a color", Color.BLACK);
                    if (color != null) {
                        colorChangeListeners.forEach(l -> {
                            try {
                                l.colorChanged(color.getRGB());
                            } catch (RemoteException ignored) {
                            }
                        });
                    }
                });
        return colorChangeButton;
    }

    public void refresh() {
        panel.repaint();
    }

    public void display() {
        SwingUtilities.invokeLater(
                () -> {
                    this.pack();
                    this.setVisible(true);
                });
    }

    public void addPixelGridEventListener(PixelGridEventListener l) {
        pixelListeners.add(l);
    }

    public void addMouseMovedListener(MouseMovedListener l) {
        movedListener.add(l);
    }

    public void addColorChangedListener(ColorChangeListener l) {
        colorChangeListeners.add(l);
    }

    private void hideCursor() {
        var cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        var blankCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(cursorImage, new Point(0, 0), "blank cursor");
        this.getContentPane().setCursor(blankCursor);
    }

    private MouseListener createMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int dx = w / view.getController().getModel().getGrid().getNumColumns();
                    int dy = h / view.getController().getModel().getGrid().getNumRows();
                    int col = e.getX() / dx;
                    int row = e.getY() / dy;
                    pixelListeners.forEach(l -> {
                        try {
                            l.selectedCell(col, row);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                } catch (RemoteException ignored) {
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }

    private MouseMotionListener createMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                movedListener.forEach(l -> {
                    try {
                        l.mouseMoved(e.getX(), e.getY());
                    } catch (RemoteException ignored) {
                    }
                });
            }
        };
    }
}
