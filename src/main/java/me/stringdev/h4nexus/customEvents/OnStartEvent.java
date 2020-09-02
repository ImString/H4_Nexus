package me.stringdev.h4nexus.customEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnStartEvent extends Event {

    public OnStartEvent(){

    }

    private static HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    static public HandlerList getHandlerList() {
        return handlers;
    }
}
