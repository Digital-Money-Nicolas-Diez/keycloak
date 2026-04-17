package org.messages;

import org.keycloak.events.EventType;

import java.util.EnumMap;
import java.util.List;

public class MessagesContext {

    private final EnumMap<EventType, MessageStrategy> strategies;

    public MessagesContext(List<MessageStrategy> strategiesList) {
        this.strategies = new EnumMap<>(EventType.class);

        for (MessageStrategy strategy : strategiesList) {
            strategies.put(strategy.supports(), strategy);
        }
    }

    public String execute(Context ctx) {
        MessageStrategy strategy = strategies.get(ctx.event().getType());

        if (strategy == null) {
            return null;
        }

        return strategy.execute(ctx);
    }
}

