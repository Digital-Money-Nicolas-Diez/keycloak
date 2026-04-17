package org.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class RabbitPublisher {

    private static final String EXCHANGE = "keycloak.events";
    private static final String EXCHANGE_TYPE = "topic";

    private final Connection connection;
    private final Channel channel;

    public RabbitPublisher(String host, int port, String username, String password) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE, EXCHANGE_TYPE, true);
    }

    public void publish(String routingKey, String message) throws Exception {
        channel.basicPublish(
                EXCHANGE,
                routingKey,
                null,
                message.getBytes(StandardCharsets.UTF_8)
        );
    }

    public void close() {
        try { channel.close(); } catch (Exception ignored) {}
        try { connection.close(); } catch (Exception ignored) {}
    }

}
