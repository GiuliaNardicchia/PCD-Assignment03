package it.unibo.pcd.assignment03.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.DeliverCallback;
import it.unibo.pcd.assignment03.model.Brush;
import it.unibo.pcd.assignment03.model.BrushImpl;
import it.unibo.pcd.assignment03.model.Model;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ControllerImpl implements Controller {
    private Model model;
    private View view;
    private ChannelManager channelManager;

    private String brushExchangeQueueName;

    private final DeliverCallback manageBrushCallback = (consumerTag, delivery) -> {
        if (delivery.getEnvelope().getRoutingKey().equals(Channels.BRUSH_POSITION_EXCHANGE.getKey())) {
            // TODO
            // TODO: Manage position change
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Brush brush = gson.fromJson(message, BrushImpl.class);
            this.model.updateBrushes(brush);
            this.view.refresh();
        }

    };

    public ControllerImpl() {
        try {
            this.channelManager = new ChannelManagerImpl();
            this.brushExchangeQueueName = channelManager.exchangeDeclare(Channels.BRUSH_POSITION_EXCHANGE.getName(), "fanout");
        } catch (TimeoutException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void init(View view, Model model) throws IOException {
        this.view = view;
        this.model = model;
        this.channelManager.registerConsumer(this.brushExchangeQueueName, manageBrushCallback);
    }


    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public void updateLocalBrushPosition(int x, int y) {
        this.model.updateLocalBrushPosition(x, y);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String message = gson.toJson(this.model.getLocalBrush());
        try {
            this.channelManager.sendMessage(Channels.BRUSH_POSITION_EXCHANGE, message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void updatePixelGrid(int x, int y) {
        this.model.updatePixelGrid(x, y);

    }

    @Override
    public void updateLocalBrushColor(int color) {
        this.model.updateLocalBrushColor(color);

    }

    @Override
    public void start() {
        this.view.display();
    }
}
