package it.unibo.pcd.assignment03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;


public class Recv {


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Send.EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, Send.EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            System.out.println(consumerTag);
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            if (delivery.getEnvelope().getRoutingKey().equals(Send.GRID_EXCANGE_KEY)) {
                System.out.println(" [x] Received a grid '" + message + "'");
            } else if (delivery.getEnvelope().getRoutingKey().equals(Send.BRUSH_EXCHANGE_KEY)) {
                System.out.println(" [x] Received a brush '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });


    }
}

