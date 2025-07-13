package it.unibo.pcd.assignment03.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public interface ChannelManager {

    String exchangeDeclare(String exchangeName, String type) throws IOException;

    void sendMessage(Channels exchange, String message) throws IOException;

    void registerConsumer(String queueName, DeliverCallback deliverCallback) throws IOException;
}
