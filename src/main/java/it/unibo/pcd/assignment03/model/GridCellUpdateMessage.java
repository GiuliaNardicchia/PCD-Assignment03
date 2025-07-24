package it.unibo.pcd.assignment03.model;

import java.io.Serializable;

public record GridCellUpdateMessage(int x, int y, int color) implements Serializable {
}
