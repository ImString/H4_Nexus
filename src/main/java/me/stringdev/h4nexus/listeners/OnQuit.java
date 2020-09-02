package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.customEvents.OnPlayerLeaveOfEvent;
import me.stringdev.h4nexus.enums.ReasonDisconnect;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = (Player) e.getPlayer();
        H4NexusAPI h4 = new H4NexusAPI();

        if (h4.getState() != State.FECHADO && h4.getState() != State.AGUARDANDO) {
            if (h4.isParticipate(p)) {
                h4.playersTeam(h4.getPlayerTeam(p)).forEach(players -> {
                    players.sendMessage(Main.message.message("Desconectou").replace("${player}", p.getName()));
                });

                OnPlayerLeaveOfEvent event = new OnPlayerLeaveOfEvent(p, ReasonDisconnect.DISCONNECT);
                Bukkit.getServer().getPluginManager().callEvent(event);
                h4.removePlayerEvent(p);
                p.setHealth(0);
            }
        }
    }

    @EventHandler
    public void onQuitEvent(OnPlayerLeaveOfEvent e) {
        Player p = (Player) e.getPlayer();
        H4NexusAPI h4 = new H4NexusAPI();

        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);

        p.teleport(Main.locations.getLocation("Location.SAIDA"));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (h4.getState() != State.FECHADO && h4.getState() != State.AGUARDANDO) {
                    if (h4.participantsSize() == 1) {
                        H4NexusAPI.nexusEvent.terminar("Falta-Players", null);
                    }
                }
            }
        }.runTaskLater(Main.instance, 10);
    }
}
