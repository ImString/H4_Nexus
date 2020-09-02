package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnPlayerHitPlayer implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Player) {
                Player tomou = (Player) e.getEntity();
                Player bateu = (Player) e.getDamager();
                H4NexusAPI h4 = new H4NexusAPI();

                if (h4.isParticipate(bateu) || h4.isParticipate(tomou)) {
                    if (h4.getState() == State.AGUARDANDO && h4.getState() == State.INICIANDO) {
                        e.setCancelled(true);
                        return;
                    }
                }

                if (h4.isParticipate(bateu) && h4.isParticipate(tomou)) {
                    if (h4.getPlayerTeam(bateu) == h4.getPlayerTeam(tomou)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerDame(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            H4NexusAPI h4 = new H4NexusAPI();
            Player p = (Player) e.getEntity();

            if (h4.getState() == State.AGUARDANDO || h4.getState() == State.INICIANDO) {
                if (h4.isParticipate(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
