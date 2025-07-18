package it.unibo.pcd.assignment03.controller;

import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;

public interface Controller {
    void init(View view, Model model) throws IOException;

    Model getModel();

    View getView();

    void updateLocalBrush(int x, int y);

    void updatePixelGrid(int x, int y);

    void updateLocalBrushColor(int color);

    void start() throws IOException;

    void sendGoodbyeMessage() throws IOException;
}
