package org.acme.provider;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.messages.Context;
import org.messages.MessagesContext;
import org.rabbitmq.RabbitPublisher;

public class KeycloakEventListener implements EventListenerProvider {

    private final RabbitPublisher rabbitmq;
    private final MessagesContext messageContext;
    private final KeycloakSession session;

    public KeycloakEventListener(KeycloakSession session, RabbitPublisher rabbit, MessagesContext messageContext) {
        this.rabbitmq = rabbit;
        this.messageContext = messageContext;
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        try {

            Context ctx = new Context(event, this.session);
            this.rabbitmq.publish(String.valueOf(event.getType()),this.messageContext.execute(ctx));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {}

    @Override
    public void close() {}
}
