package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnBlockCommand implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerUseCommand(PlayerCommandPreprocessEvent e) {
        if (!e.getMessage().toLowerCase().startsWith("/nexus")) {
            H4NexusAPI h4 = new H4NexusAPI();

            if (h4.isParticipate(e.getPlayer())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cVocê não pode executar comandos no evento.");
            }
        }
    }
}
