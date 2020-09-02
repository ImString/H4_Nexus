package me.stringdev.h4nexus.customEvents;

import me.stringdev.h4nexus.enums.ReasonDisconnect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnPlayerLeaveOfEvent extends Event {

    Player player;
    ReasonDisconnect reason;

    public OnPlayerLeaveOfEvent(Player player, ReasonDisconnect reason) {
        this.player = player;
        this.reason = reason;
    }

    public Player getPlayer() {
        return player;
    }

    public ReasonDisconnect getReason() {
        return reason;
    }

    private static HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    static public HandlerList getHandlerList() {
        return handlers;
    }
}
