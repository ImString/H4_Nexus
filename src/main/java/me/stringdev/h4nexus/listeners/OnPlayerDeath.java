package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import me.stringdev.h4nexus.objects.util.ItensPvP;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnPlayerDeath implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        H4NexusAPI h4 = new H4NexusAPI();

        if(h4.getState() != State.FECHADO) {
            if (h4.isParticipate(p)) {
                CraftPlayer cp = (CraftPlayer) p;
                EntityPlayer ep = cp.getHandle();
                PlayerConnection pc = ep.playerConnection;
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!p.isDead()) return;
                        pc.a(packet);
                        p.setFireTicks(0);
                    }
                }.runTaskLater(Main.instance, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = (Player) e.getPlayer();
        H4NexusAPI h4 = new H4NexusAPI();
        if (h4.getState() == State.INICIADO) {
            if (h4.isParticipate(p)) {
                Teams team = h4.getPlayerTeam(p);
                ItensPvP itensPvP = new ItensPvP(p);

                p.sendMessage(Main.message.message("Morreu"));
                itensPvP.giveArmour(team);
                itensPvP.giveItens();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.teleport(h4.locationTeam(h4.getPlayerTeam(p)));
                    }
                }.runTaskLater(Main.instance, 1);
            }
        }
    }
}
