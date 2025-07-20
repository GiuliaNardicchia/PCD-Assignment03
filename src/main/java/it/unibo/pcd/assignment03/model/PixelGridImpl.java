package it.unibo.pcd.assignment03.model;

import java.io.Serializable;

public class PixelGridImpl implements PixelGrid, Serializable {
    private final int nRows;
    private final int nColumns;

    private final int[][] grid;

    public PixelGridImpl(final int nRows, final int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        grid = new int[nRows][nColumns];
    }

    @Override
    public void set(final int x, final int y, final int color) {
        grid[y][x] = color;
    }

    @Override
    public int get(int x, int y) {
        return grid[y][x];
    }

    @Override
    public int getNumRows() {
        return this.nRows;
    }

    @Override
    public int getNumColumns() {
        return this.nColumns;
    }
}
