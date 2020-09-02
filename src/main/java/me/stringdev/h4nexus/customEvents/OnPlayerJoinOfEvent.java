package me.stringdev.h4nexus.customEvents;

import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnPlayerJoinOfEvent extends Event {

    Player player;
    Teams team;

    public OnPlayerJoinOfEvent(Player player, Teams team) {
        this.player = player;
        this.team = team;
    }

    public Player getPlayer() {
        return player;
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
