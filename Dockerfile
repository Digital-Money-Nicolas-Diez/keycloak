FROM quay.io/keycloak/keycloak:26.4.7 AS builder
COPY kc-rabbit-event-listener-1.0.0.jar /opt/keycloak/providers/
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.4.7
COPY --from=builder /opt/keycloak/ /opt/keycloak/
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--http-enabled=true", "--hostname-strict=false"]
