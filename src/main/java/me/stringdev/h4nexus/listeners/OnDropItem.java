package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnDropItem implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        H4NexusAPI h4 = new H4NexusAPI();
        Player p = e.getPlayer();

        if (h4.isParticipate(p)) {
            e.setCancelled(true);
        }
    }
}
