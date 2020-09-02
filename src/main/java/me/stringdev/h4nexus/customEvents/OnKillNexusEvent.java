package me.stringdev.h4nexus.customEvents;

import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnKillNexusEvent extends Event {

    Player lastDamage;
    Teams team;

    public OnKillNexusEvent(Player lastDamage, Teams team) {
        this.lastDamage = lastDamage;
        this.team = team;
    }

    public Player getLastDamage() {
        return lastDamage;
    }

    public Teams getTeam() {
        return team;
    }

    private static HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    static public HandlerList getHandlerList() {
        return handlers;
    }
}
