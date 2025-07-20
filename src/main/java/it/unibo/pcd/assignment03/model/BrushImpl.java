package it.unibo.pcd.assignment03.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class BrushImpl implements Brush, Serializable {
    private int x;
    private int y;
    private int color;
    private final int id;

    public BrushImpl(int id, int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.id = id;
    }

    public BrushImpl(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.id = new Random().nextInt();
    }

    @Override
    public synchronized void updatePosition(int x, int y) {
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
    public synchronized void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BrushImpl{" + "x=" + x + ", y=" + y + ", color=" + color + ", id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        BrushImpl brush = (BrushImpl) o;
        return id == brush.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
