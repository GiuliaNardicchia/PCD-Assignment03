package it.unibo.pcd.assignment03.controller;

public class GridCellUpdateMessage {
    int x;
    int y;
    int color;

    public GridCellUpdateMessage(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
