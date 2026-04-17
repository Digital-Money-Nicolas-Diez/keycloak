package org.messages;

import org.keycloak.events.Event;
import org.keycloak.models.KeycloakSession;

public record Context(
        Event event,
        KeycloakSession session
) {}