package org.messages;
import org.keycloak.events.EventType;

public interface MessageStrategy{
    EventType supports();
    String execute(Context ctx);
}
