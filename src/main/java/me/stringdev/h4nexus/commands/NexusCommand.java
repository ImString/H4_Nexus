package me.stringdev.h4nexus.commands;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.apis.ItemBuilder;
import me.stringdev.h4nexus.customEvents.OnDamageNexus;
import me.stringdev.h4nexus.customEvents.OnPlayerLeaveOfEvent;
import me.stringdev.h4nexus.enums.ReasonDisconnect;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NexusCommand implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
        H4NexusAPI h4 = new H4NexusAPI();
        if (args.length == 0) {
            if (!s.hasPermission(Main.config.getString("Permissao.Admin"))) {
                s.sendMessage("§r");
                s.sendMessage("§a/nexus info §7- §fTer informações sobre o evento");
                s.sendMessage("§a/nexus entrar §7- §fEntrar no evento");
                s.sendMessage("§a/nexus sair §7- §fSair do evento");
                s.sendMessage("§r");
                return true;
            }

            s.sendMessage("§r");
            s.sendMessage("§a/nexus info §7- §fTer informações sobre o evento");
            s.sendMessage("§a/nexus entrar §7- §fEntrar no evento");
            s.sendMessage("§a/nexus sair §7- §fSair do evento");
            s.sendMessage("§c/nexus iniciar - Iniciar o evento");
            s.sendMessage("§c/nexus parar - Forçar parada do evento");
            s.sendMessage("§r");
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {
                s.sendMessage("§r");
                s.sendMessage("§eO evento está: " + h4.getState().getColorState() + h4.getState().getNameState().toUpperCase());
                s.sendMessage("§r");
                return true;
            }

            if (args[0].equalsIgnoreCase("entrar")) {
                if (s instanceof Player) {
                    if (h4.getState() != State.AGUARDANDO) {
                        s.sendMessage("§cO evento está fechado.");
                        return true;
                    }

                    if (h4.isParticipate((Player) s)) {
                        s.sendMessage("§cVocê já está participando do evento.");
                        return true;
                    }

                    if (this.inventoryHasItem((Player) s) == false) {
                        s.sendMessage("§cVocê deve esvaziar seu inventário para participar no evento.");
                        return true;
                    }

                    if (h4.players().size() >= Main.config.getInt("Maximo-Players")) {
                        s.sendMessage("§cO evento está lotado.");
                        return true;
                    }

                    ((Player) s).teleport(Main.locations.getLocation("Location.ENTRADA"));
                    s.sendMessage("§aVocê entrou no evento.");
                    h4.putPlayerEvent((Player) s);
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("sair")) {
                if (s instanceof Player) {
                    if (h4.getState() == State.FECHADO) {
                        s.sendMessage("§cO evento está fechado.");
                        return true;
                    }

                    if (!h4.isParticipate((Player) s)) {
                        s.sendMessage("§cVocê não está participando do evento.");
                        return true;
                    }

                    s.sendMessage("§aVocê saiu do evento com sucesso.");
                    h4.playersTeam(h4.getPlayerTeam((Player) s)).forEach(players -> {
                        players.sendMessage(Main.message.message("Desconectou").replace("${player}", ((Player) s).getName()));
                    });
                    OnPlayerLeaveOfEvent event = new OnPlayerLeaveOfEvent((Player) s, ReasonDisconnect.LEAVE);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    h4.removePlayerEvent((Player) s);
                    return true;
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("iniciar")) {
                if (!s.hasPermission(Main.config.getString("Permissao.Admin"))) {
                    s.sendMessage("§cVocê não tem permissão para fazer isto.");
                    return true;
                }

                if (h4.getState() != State.FECHADO) {
                    s.sendMessage("§cO evento já está ocorrendo.");
                    return true;
                }

                s.sendMessage("§aEvento iniciado com sucesso.");
                h4.startEvent();
                return true;
            }

            if (args[0].equalsIgnoreCase("parar")) {
                if (s instanceof Player) {
                    if (!s.hasPermission(Main.config.getString("Permissao.Admin"))) {
                        s.sendMessage("§cVocê não tem permissão para fazer isto.");
                        return true;
                    }

                    if (h4.getState() == State.FECHADO) {
                        s.sendMessage("§cO evento já está fechado.");
                        return true;
                    }

                    s.sendMessage("§aEvento finalizado com sucesso.");
                    h4.stopEvent((Player) s);
                    return true;
                }
            }
            return true;
        }
        return true;
    }

    private boolean inventoryHasItem(Player getjogador) {
        for (ItemStack pegaitem : getjogador.getInventory().getContents()) {
            if (pegaitem != null) {
                return false;
            }
        }
        for (int loop = 36; loop <= 39; loop++) {
            if (getjogador.getInventory().getItem(loop) != null) {
                return false;
            }
        }
        return true;
    }
}
