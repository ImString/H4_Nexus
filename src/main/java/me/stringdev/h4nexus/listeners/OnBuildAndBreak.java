package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBuildAndBreak implements Listener {

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        H4NexusAPI h4 = new H4NexusAPI();
        Player p = e.getPlayer();

        if (h4.isParticipate(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBuild(BlockPlaceEvent e) {
        H4NexusAPI h4 = new H4NexusAPI();
        Player p = e.getPlayer();

        if (h4.isParticipate(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        H4NexusAPI h4 = new H4NexusAPI();
        Player p = e.getPlayer();

        if (h4.isParticipate(p)) {
            e.setCancelled(true);
        }
    }

}
