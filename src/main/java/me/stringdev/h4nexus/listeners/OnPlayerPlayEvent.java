package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.customEvents.OnPlayerJoinOfEvent;
import me.stringdev.h4nexus.customEvents.OnStartEvent;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import me.stringdev.h4nexus.objects.NexusTeamAPI;
import me.stringdev.h4nexus.objects.util.ItensPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerPlayEvent implements Listener {

    @EventHandler
    public void playerJoinEvent(OnPlayerJoinOfEvent e) {
        Player p = (Player) e.getPlayer();
        H4NexusAPI h4 = new H4NexusAPI();
        Teams team = e.getTeam();

        new ItensPvP(p).giveArmour(team);
        p.setMaxHealth(20);
        p.setHealth(20);
        p.setFoodLevel(20);
    }

    @EventHandler
    public void onPlayerPlayEvent(OnStartEvent e) {
        H4NexusAPI h4 = new H4NexusAPI();
        h4.players().forEach(player -> {
            new ItensPvP(player).giveItens();
            new NexusTeamAPI().createAllNexus();
            player.teleport(Main.teamLocations.getLocation("Location.Spawn" + h4.getPlayerTeam(player).getNameTeam().toUpperCase()));
        });
    }
}
