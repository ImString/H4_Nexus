package me.stringdev.h4nexus.enums;

public enum ReasonDisconnect {

    DISCONNECT("Deslogou"),
    LEAVE("Saiu do evento");

    private String reason;

    ReasonDisconnect(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
