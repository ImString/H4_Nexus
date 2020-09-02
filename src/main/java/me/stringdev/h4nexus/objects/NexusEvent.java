package me.stringdev.h4nexus.objects;

import java.util.Date;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.apis.ItemBuilder;
import me.stringdev.h4nexus.apis.TimeStop;
import me.stringdev.h4nexus.apis.TitleAPI;
import me.stringdev.h4nexus.customEvents.OnPlayerLeaveOfEvent;
import me.stringdev.h4nexus.customEvents.OnStartEvent;
import me.stringdev.h4nexus.enums.ReasonDisconnect;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.events.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class NexusEvent implements EventManager {

    public static EventManager evento;
    private static NexusEvent instance = new NexusEvent();
    private int iniciarTask = Main.config.getInt("Tempos.Iniciar");
    private int iniciandoTask = Main.config.getInt("Tempos.Iniciando");

    public static NexusEvent getInstance() {
        return instance;
    }

    @Override
    public void iniciar() {
        H4NexusAPI h4 = new H4NexusAPI();
        h4.setState(State.AGUARDANDO);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (h4.getState() == State.AGUARDANDO) {
                    if (iniciarTask == 0) {
                        iniciando();
                        cancel();
                        return;
                    }

                    Main.message.getStringList("Chamados-Inicio").forEach(m -> {
                        Bukkit.broadcastMessage(m.replace("&", "§").replace("${quantia}", String.valueOf(h4.participantsSize())).replace("${max}", String.valueOf(Main.config.getInt("Maximo-Players"))).replace("${tempo}", String.valueOf(iniciarTask)));
                    });

                    iniciarTask = iniciarTask - 1;
                }
            }
        }.runTaskTimer(Main.instance, 0L, 60 * 20);
    }

    @Override
    public void terminar(String reason, Player p) {
        H4NexusAPI h4 = new H4NexusAPI();
        h4.setState(State.FECHADO);
        H4NexusAPI.nexusEvent = null;
        if(TimeStop.stopNexus.containsKey(State.INICIADO)) {
            TimeStop.stopNexus.clear();
        }

        iniciarTask = Main.config.getInt("Tempos.Iniciar");
        iniciandoTask = Main.config.getInt("Tempos.Iniciando");
        new NexusTeamAPI().removeAllNexus();
        h4.players().forEach(td -> {
            TitleAPI.sendTitle(td, 1, 3, 2, "§c§lEVENTO CANCELADO", "");
            td.teleport(Main.locations.getLocation("Location.SAIDA"));
            td.playSound(td.getLocation(), Sound.AMBIENCE_RAIN, 5f, 5f);
            td.getInventory().clear();
            td.getInventory().setHelmet(null);
            td.getInventory().setChestplate(null);
            td.getInventory().setLeggings(null);
            td.getInventory().setBoots(null);
        });

        h4.players().forEach(tds -> h4.forceRemove(tds));

        if (reason.equalsIgnoreCase("Cancelado")) {
            Main.message.getStringList("Staff-Cancelou").forEach(msg -> {
                Bukkit.broadcastMessage(msg.replace("&", "§"));
            });
        }

        if (reason.equalsIgnoreCase("Falta-Players")) {
            Main.message.getStringList("Falta-Players").forEach(msg -> {
                Bukkit.broadcastMessage(msg.replace("&", "§"));
            });
        }
    }

    @Override
    public void terminar(Teams team) {
        H4NexusAPI h4 = new H4NexusAPI();
        h4.setState(State.FECHADO);

        Main.message.getStringList("Ganhador").forEach(msg -> {
            Bukkit.broadcastMessage(msg
                    .replace("&", "§")
                    .replace("${time}", team.getColorString() + "§l" + team.getNameTeam().toUpperCase())
                    .replace("${pAzul}", String.valueOf(h4.playersTeam(Teams.AZUL).size()))
                    .replace("${pVermelho}", String.valueOf(h4.playersTeam(Teams.VERMELHO).size())));
        });

        h4.players().forEach(td -> {
            td.teleport(Main.locations.getLocation("Location.SAIDA"));
            td.getInventory().clear();
            td.getInventory().setHelmet(null);
            td.getInventory().setChestplate(null);
            td.getInventory().setLeggings(null);
            td.getInventory().setBoots(null);
        });

        h4.players().forEach(tds -> h4.forceRemove(tds));
        TimeStop.stopNexus.clear();
        H4NexusAPI.nexusEvent = null;
    }

    private void iniciando() {
        H4NexusAPI h4 = new H4NexusAPI();
        h4.setState(State.INICIANDO);

        if(h4.getState() == State.INICIANDO) {
            if (h4.participantsSize() < 2) {
                Main.message.getStringList("Falta-Players").forEach(m -> {
                    Bukkit.broadcastMessage(m.replace("&", "§"));
                });

                h4.setState(State.FECHADO);
                H4NexusAPI.nexusEvent = null;
                h4.players().forEach(td -> {
                    td.teleport(Main.locations.getLocation("Location.SAIDA"));
                    h4.removePlayerEvent(td);
                });
                return;
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (h4.getState() == State.INICIANDO) {
                    if (iniciandoTask == 0) {
                        iniciado();
                        cancel();
                        return;
                    }

                    if (iniciandoTask == 15) {
                        Main.message.getStringList("Chamados-Iniciando").forEach(m -> h4.msgPlayers(m.replace("&", "§").replace("${tempo}", String.valueOf(iniciandoTask))));
                        iniciandoTask = iniciandoTask - 1;
                        return;
                    }

                    if (iniciandoTask == 10) {
                        Main.message.getStringList("Chamados-Iniciando").forEach(m -> h4.msgPlayers(m.replace("&", "§").replace("${tempo}", String.valueOf(iniciandoTask))));
                        iniciandoTask = iniciandoTask - 1;
                        return;
                    }

                    if (iniciandoTask == 5) {
                        Main.message.getStringList("Chamados-Iniciando").forEach(m -> h4.msgPlayers(m.replace("&", "§").replace("${tempo}", String.valueOf(iniciandoTask))));
                        iniciandoTask = iniciandoTask - 1;
                        return;
                    }

                    if (iniciandoTask <= 3 && iniciandoTask >= 1) {
                        Main.message.getStringList("Chamados-Iniciando").forEach(m -> h4.msgPlayers(m.replace("&", "§").replace("${tempo}", String.valueOf(iniciandoTask))));
                        iniciandoTask = iniciandoTask - 1;
                        return;
                    }

                    iniciandoTask = iniciandoTask - 1;
                }
            }
        }.runTaskTimer(Main.instance, 0L, 20);
    }

    @SuppressWarnings("deprecation")
    private void iniciado() {
        H4NexusAPI h4 = new H4NexusAPI();
        h4.setState(State.INICIADO);
        Bukkit.getServer().getPluginManager().callEvent(new OnStartEvent());

        Date atual = new Date();
        atual.setMinutes(atual.getMinutes() + Main.config.getInt("Tempos.Acabar"));
        TimeStop.stopNexus.put(State.INICIADO, atual.getTime());

        iniciarTask = Main.config.getInt("Tempos.Iniciar");
        iniciandoTask = Main.config.getInt("Tempos.Iniciando");

        Main.message.getStringList("Iniciou").forEach(m -> h4.msgPlayers(m.replace("&", "§")));
        h4.players().forEach(players -> {
            h4.locationTeam(h4.getPlayerTeam(players));
        });

        int conta = Main.config.getInt("Tempos.Rolando") * 60;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (h4.getState() == State.INICIADO) {
                    if (TimeStop.passou(State.INICIADO)) {
                        for (Player td : h4.players()) {
                            td.playSound(td.getLocation(), Sound.AMBIENCE_RAIN, 1f, 1f);
                            Main.message.getStringList("Acabou-Tempo").forEach(m -> td.sendMessage(m.replace("&", "§")));
                        }

                        h4.setState(State.FECHADO);
                        TimeStop.stopNexus.remove(State.INICIADO);
                        h4.players().forEach(td -> {
                            td.teleport(Main.locations.getLocation("Location.SAIDA"));
                            h4.removePlayerEvent(td);
                        });
                    }
                    return;
                }
            }
        }.runTaskTimer(Main.instance, 0L, conta * 20);
    }
}