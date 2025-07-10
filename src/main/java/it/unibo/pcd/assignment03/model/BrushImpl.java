package it.unibo.pcd.assignment03.model;

import java.util.Objects;

public class BrushImpl implements Brush {
    private int x;
    private int y;
    private int color;


    public BrushImpl(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BrushImpl brush = (BrushImpl) o;
        return x == brush.x && y == brush.y && color == brush.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }
}
