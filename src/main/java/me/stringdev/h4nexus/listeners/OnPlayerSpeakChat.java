package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnPlayerSpeakChat implements Listener {

    @EventHandler
    public void onPlayerSpeakChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        H4NexusAPI h4 = new H4NexusAPI();

        if (h4.getState() != State.FECHADO) {
            if (h4.isParticipate(p)) {
                e.setCancelled(true);
                Teams team = h4.getPlayerTeam(p);
                h4.playersTeam(team).forEach(times -> {
                    times.sendMessage(team.getColorString() + "[" + team.getNameTeam() + "] " + p.getName() + "§7: " + e.getMessage());
                });
            }
        }
    }
}
