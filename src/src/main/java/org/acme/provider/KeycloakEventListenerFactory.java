package org.acme.provider;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.messages.MessagesContext;
import org.messages.onRegister;
import org.rabbitmq.RabbitPublisher;

import java.util.List;

public class KeycloakEventListenerFactory implements EventListenerProviderFactory {


    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        try {
            RabbitPublisher rabbit = new RabbitPublisher("rabbitmq",5672,"guest","guest");
            MessagesContext messages = new MessagesContext(List.of(
                    new onRegister()
            ));

            return new KeycloakEventListener(keycloakSession,rabbit,messages);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(Config.Scope scope) {
        System.out.println("🔥 SPI INIT");
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public void close() {}

    @Override
    public String getId() {
        return "keycloak-event-listener";
    }
}
