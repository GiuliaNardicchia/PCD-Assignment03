package it.unibo.pcd.assignment03.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ChannelManagerImpl implements ChannelManager {
    private final Channel channel;


    public ChannelManagerImpl() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @Override
    public String exchangeDeclare(Channels exchange, String type) throws IOException {
        channel.exchangeDeclare(exchange.getName(), type);
        return channel.queueDeclare().getQueue();
    }

    @Override
    public void queueBind(String queueName, Channels exchange) throws IOException {
        channel.queueBind(queueName, exchange.getName(), exchange.getKey());
    }

    @Override
    public void sendMessage(Channels exchange, String message) throws IOException {
        channel.basicPublish(exchange.getName(), exchange.getKey(), null, message.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void registerConsumer(String queueName, DeliverCallback deliverCallback) throws IOException {
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
