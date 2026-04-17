package org.messages;

import org.keycloak.events.EventType;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class onRegister implements MessageStrategy{

    @Override
    public EventType supports() {
        return EventType.REGISTER;
    }

    @Override
    public String execute(Context ctx) {

        RealmModel realm = ctx.session().realms().getRealm(ctx.event().getRealmId());
        UserModel user = ctx.session().users().getUserById(realm, ctx.event().getUserId());

        return """
            {
              "id": "%s",
              "username": "%s",
              "email": "%s",
              "firstName": "%s",
              "lastName": "%s",
              "realm": "%s"
            }
            """.formatted(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                realm.getName()
        );

    }
}
