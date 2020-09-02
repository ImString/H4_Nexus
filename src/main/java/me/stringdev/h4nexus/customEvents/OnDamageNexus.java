package me.stringdev.h4nexus.customEvents;

import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnDamageNexus extends Event {

    Player player;
    Teams team;
    Entity entity;
    int damage;
    int lifeActual;

    public OnDamageNexus(Player player, Teams team, Entity entity, int damage, int lifeActual) {
        this.player = player;
        this.team = team;
        this.entity = entity;
        this.damage = damage;
        this.lifeActual = lifeActual;
    }

    public Player getPlayer() {
        return player;
    }

    public Teams getTeam() {
        return team;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getDamage() {
        return damage;
    }

    public int getLifeActual() {
        return lifeActual;
    }

    private static HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    static public HandlerList getHandlerList() {
        return handlers;
    }

}
