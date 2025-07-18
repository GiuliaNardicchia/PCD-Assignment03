package it.unibo.pcd.assignment03.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.DeliverCallback;
import it.unibo.pcd.assignment03.model.*;
import it.unibo.pcd.assignment03.view.View;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class ControllerImpl implements Controller {
    private Model model;
    private View view;
    private ChannelManager channelManager;

    private String brushExchangeQueueName;
    private String cellExchangeQueueName;
    private String welcomeExchangeQueueName;
    private String goodbyeExchangeQueueName;

    private boolean receivedGrid = false;
    private boolean receivedBrushes = false;

    private final DeliverCallback manageBrushCallback = (consumerTag, delivery) -> {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        Brush brush = gson.fromJson(message, BrushImpl.class);
        this.model.updateBrushes(brush);
        this.view.refresh();
    };

    private final DeliverCallback manageGridCallback = (consumerTag, delivery) -> {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received grid message: " + message);
        GridCellUpdateMessage gridCell = gson.fromJson(message, GridCellUpdateMessage.class);
        this.model.updatePixelGrid(gridCell.x, gridCell.y, gridCell.color);
    };

    private final DeliverCallback manageWelcomeCallback = (consumerTag, delivery) -> {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received welcome message: " + msg);
        if (delivery.getEnvelope().getRoutingKey().equals(Channels.WELCOME_EXCHANGE.getKey())) {
            try {
                Brush brush = gson.fromJson(msg, BrushImpl.class);
                this.model.updateBrushes(brush);
                this.view.refresh();
                Set<Brush> brushes = this.model.getBrushManager().getBrushes();
                String brushesJson = gson.toJson(brushes);
                this.channelManager.sendMessage(Channels.WELCOME_BRUSHES_EXCHANGE, brushesJson);
                String grid = gson.toJson(this.model.getGrid());
                this.channelManager.sendMessage(Channels.WELCOME_GRID_EXCHANGE, grid);
            } catch (IOException ignored) {
            }
        } else if (delivery.getEnvelope().getRoutingKey().equals(Channels.WELCOME_BRUSHES_EXCHANGE.getKey())
                && !receivedBrushes) {
            try {
                TypeToken<Set<BrushImpl>> brushSetType = new TypeToken<>() {};
                Set<BrushImpl> brushes = gson.fromJson(msg, brushSetType.getType());
                Set<Brush> brushSet = new HashSet<>(brushes);
                this.model.setBrushes(brushSet);
                this.receivedBrushes = true;
            } catch (Exception e) {
                System.err.println("Error deserializing brush set: " + e.getMessage());
            }
        } else if (delivery.getEnvelope().getRoutingKey().equals(Channels.WELCOME_GRID_EXCHANGE.getKey())
                && !receivedGrid) {
            try {
                PixelGrid pixelGrid = gson.fromJson(msg, PixelGrid.class);
                this.model.updateGridFromSource(pixelGrid);
                this.receivedGrid = true;
                this.view.refresh();
            } catch (Exception e) {
                System.err.println("Error deserializing pixel grid: " + e.getMessage());
            }
        }
    };

    private final DeliverCallback manageGoodbyeCallback = (consumerTag, delivery) -> {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received goodbye message: " + message);
        Brush brush = gson.fromJson(message, BrushImpl.class);
        this.model.getBrushManager().removeBrush(brush);
        this.view.refresh();
    };

    public ControllerImpl() {
        try {
            this.channelManager = new ChannelManagerImpl();
            this.brushExchangeQueueName = channelManager.exchangeDeclare(Channels.BRUSH_EXCHANGE, "fanout");
            this.channelManager.queueBind(this.brushExchangeQueueName, Channels.BRUSH_EXCHANGE);
            this.cellExchangeQueueName = channelManager.exchangeDeclare(Channels.GRID_CELL_EXCHANGE, "fanout");
            this.channelManager.queueBind(this.cellExchangeQueueName, Channels.GRID_CELL_EXCHANGE);
            this.welcomeExchangeQueueName = channelManager.exchangeDeclare(Channels.WELCOME_EXCHANGE, "fanout");
            this.channelManager.queueBind(this.welcomeExchangeQueueName, Channels.WELCOME_EXCHANGE);
            this.channelManager.queueBind(this.welcomeExchangeQueueName, Channels.WELCOME_BRUSHES_EXCHANGE);
            this.channelManager.queueBind(this.welcomeExchangeQueueName, Channels.WELCOME_GRID_EXCHANGE);
            this.goodbyeExchangeQueueName = channelManager.exchangeDeclare(Channels.GOODBYE_EXCHANGE, "fanout");
            this.channelManager.queueBind(this.goodbyeExchangeQueueName, Channels.GOODBYE_EXCHANGE);
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
        this.channelManager.registerConsumer(this.cellExchangeQueueName, manageGridCallback);
        this.channelManager.registerConsumer(this.welcomeExchangeQueueName, manageWelcomeCallback);
        this.channelManager.registerConsumer(this.goodbyeExchangeQueueName, manageGoodbyeCallback);
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
    public void updateLocalBrush(int x, int y) {
        this.model.updateLocalBrush(x, y);
        sendLocalBrushInfo();
    }

    @Override
    public void updatePixelGrid(int x, int y) {
        this.model.updatePixelGrid(x, y, this.model.getLocalBrush().getColor());
        GridCellUpdateMessage gridCell = new GridCellUpdateMessage(x, y, this.model.getLocalBrush().getColor());
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String message = gson.toJson(gridCell);
        try {
            this.channelManager.sendMessage(Channels.GRID_CELL_EXCHANGE, message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void updateLocalBrushColor(int color) {
        this.model.updateLocalBrushColor(color);
        this.sendLocalBrushInfo();
    }

    private void sendLocalBrushInfo() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String message = gson.toJson(this.model.getLocalBrush());
        try {
            this.channelManager.sendMessage(Channels.BRUSH_EXCHANGE, message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void start() throws IOException {
        this.view.display();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String message = gson.toJson(this.model.getLocalBrush());
        this.channelManager.sendMessage(Channels.WELCOME_EXCHANGE, message);
    }

    @Override
    public void sendGoodbyeMessage() throws IOException {
        GsonBuilder  gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String message = gson.toJson(this.model.getLocalBrush());
        this.channelManager.sendMessage(Channels.GOODBYE_EXCHANGE, message);
    }
}
