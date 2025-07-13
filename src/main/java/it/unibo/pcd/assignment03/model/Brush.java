package it.unibo.pcd.assignment03.model;

public interface Brush {
    public void updatePosition(final int x, final int y);

    public int getX();

    public int getY();

    public int getColor();

    public void setColor(int color);

    String getId();
}
