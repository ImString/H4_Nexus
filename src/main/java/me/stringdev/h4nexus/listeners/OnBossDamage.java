package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.customEvents.OnDamageNexus;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import me.stringdev.h4nexus.objects.NexusBoss;
import me.stringdev.h4nexus.objects.NexusTeamAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class OnBossDamage implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity().getType() == EntityType.ENDER_CRYSTAL) {
            if (e.getEntity().hasMetadata("Cristal")) {
                if (e.getEntity().isCustomNameVisible()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() == EntityType.ENDER_CRYSTAL) {
            if (e.getEntity().hasMetadata("Cristal")) {
                if (e.getEntity().isCustomNameVisible()) {
                    if (e.getDamager() instanceof Player) {
                        Entity entity = e.getEntity();
                        Player player = (Player) e.getDamager();
                        NexusBoss nexusBoss = new NexusTeamAPI().getNexus(entity);
                        H4NexusAPI h4NexusAPI = new H4NexusAPI();

                        if (h4NexusAPI.isParticipate(player)) {
                            if (nexusBoss.getColorTeam() != h4NexusAPI.getPlayerTeam(player)) {
                                e.setCancelled(true);
                                nexusBoss.setLife(nexusBoss.getLife() - 5);
                                entity.setCustomName("§d§lNEXUS §f- §e§l" + nexusBoss.getColorTeam().getNameTeam().toUpperCase() + " §f(§aHP: §f" + nexusBoss.getLife() + "§f)");
                                OnDamageNexus event = new OnDamageNexus(player, nexusBoss.getColorTeam(), e.getEntity(), 5, nexusBoss.getLife());
                                Bukkit.getServer().getPluginManager().callEvent(event);
                            } else {
                                player.sendMessage(Main.message.message("Hit-Proprio"));
                                e.setCancelled(true);
                            }
                        }
                    } else if (e.getDamager() instanceof Arrow) {
                        Arrow arrow = (Arrow) e.getDamager();
                        if (arrow.getShooter() instanceof Player) {
                            Player player = (Player) arrow.getShooter();
                            NexusBoss nexusBoss = new NexusTeamAPI().getNexus(e.getEntity());
                            H4NexusAPI h4NexusAPI = new H4NexusAPI();

                            if (h4NexusAPI.isParticipate(player)) {
                                if (nexusBoss.getColorTeam() != h4NexusAPI.getPlayerTeam(player)) {
                                    e.setCancelled(true);
                                    nexusBoss.setLife(nexusBoss.getLife() - 3);
                                    e.getEntity().setCustomName("§d§lNEXUS §f- §e§l" + nexusBoss.getColorTeam().getNameTeam().toUpperCase() + " §f(§aHP: §f" + nexusBoss.getLife() + "§f)");
                                    OnDamageNexus event = new OnDamageNexus(player, nexusBoss.getColorTeam(), e.getEntity(),3, nexusBoss.getLife());
                                    Bukkit.getServer().getPluginManager().callEvent(event);
                                } else {
                                    player.sendMessage(Main.message.message("Hit-Proprio"));
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
