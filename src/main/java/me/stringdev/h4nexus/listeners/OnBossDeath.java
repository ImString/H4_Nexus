package me.stringdev.h4nexus.listeners;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.apis.FireWork;
import me.stringdev.h4nexus.apis.TitleAPI;
import me.stringdev.h4nexus.customEvents.OnDamageNexus;
import me.stringdev.h4nexus.customEvents.OnKillNexusEvent;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import me.stringdev.h4nexus.objects.NexusTeamAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class OnBossDeath implements Listener {

    @EventHandler
    public void onBossReceiveDamage(OnDamageNexus e) {
        if (e.getLifeActual() <= 0) {
            Entity entity = e.getEntity();
            H4NexusAPI h4NexusAPI = new H4NexusAPI();
            Teams teamWin = e.getTeam() == Teams.AZUL ? Teams.VERMELHO : Teams.AZUL;

            new NexusTeamAPI().removeAllNexus();
            h4NexusAPI.playersTeam(e.getTeam()).forEach(losers ->{
                TitleAPI.sendTitle(losers, 1, 3, 2, "§c§lDERROTA", "");
            });

            h4NexusAPI.playersTeam(teamWin).forEach(players -> {
                TitleAPI.sendTitle(players, 1, 3, 2, "§a§lVITORIA", "");
                players.playSound(players.getLocation(), Sound.LEVEL_UP, 1f, 1f);
                Main.economy.withdrawPlayer(players.getPlayer(), Main.config.getInt("Recompensa.1o"));
            });


            OnKillNexusEvent event = new OnKillNexusEvent(e.getPlayer(), e.getTeam());
            Bukkit.getServer().getPluginManager().callEvent(event);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (h4NexusAPI.getState() == State.FECHADO) cancel();
                    h4NexusAPI.players().forEach(FireWork::spawnFireWork);
                }
            }.runTaskTimer(Main.instance, 0L,20);

            new BukkitRunnable() {
                @Override
                public void run() {
                    h4NexusAPI.teamWin(teamWin);
                }
            }.runTaskLater(Main.instance, 20 * Main.config.getInt("Tempos.Acabou"));
        }
    }
}
