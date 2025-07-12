package it.unibo.pcd.assignment03;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

public class Send {
    public final static String EXCHANGE_NAME = "hello";
    public final static String GRID_EXCANGE_KEY = "grid";
    public final static String BRUSH_EXCHANGE_KEY = "brush";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = "Hello World!";
            channel.basicPublish(EXCHANGE_NAME, GRID_EXCANGE_KEY, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            message = "Hello brush";
            channel.basicPublish(EXCHANGE_NAME, BRUSH_EXCHANGE_KEY, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
