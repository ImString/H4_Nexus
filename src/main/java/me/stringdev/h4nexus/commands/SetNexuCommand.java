package me.stringdev.h4nexus.commands;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.enums.Teams;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetNexuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
        if (!(s instanceof Player)) {
            s.sendMessage("§cVocê não é um player para fazer isto.");
            return true;
        }

        Player p = (Player) s;
        if (!p.hasPermission(Main.config.getString("Permissao.SetNexus"))) {
            p.sendMessage("§cVocê não possui permissões para fazer isto.");
            return true;
        }

        if (args.length == 0) {
            p.sendMessage("§cUse: /setnexus <nexus/locais> <local>");
            return true;
        }

        if (args.length == 1) {
            p.sendMessage("§cUse: /setnexus <nexus/locais> <local>");
            return true;
        }


        if (args.length == 2) {
            Location loc = p.getLocation();
            if (args[0].equalsIgnoreCase("nexus")) {
                if (args[1].equalsIgnoreCase("vermelho") || args[1].equalsIgnoreCase("azul")) {
                    Main.teamLocations.setLocation("Nexus." + Teams.valueOf(args[1].toUpperCase()).getNameTeam(), loc);
                    Main.teamLocations.saveConfig();
                    p.sendMessage("§aO nexu da equipe " + args[1] + " foi setado.");
                    p.playSound(loc, Sound.LEVEL_UP, 1f, 1f);
                    return true;
                }

                p.sendMessage("§cUse: /setnexus nexus <vermelho/azul>");
                return true;
            }

            if (args[0].equalsIgnoreCase("locais")) {
                if (args[1].equalsIgnoreCase("entrada") || args[1].equalsIgnoreCase("saida")) {
                    Main.locations.setLocation("Location." + args[1].toUpperCase(), loc);
                    Main.locations.saveConfig();
                    p.sendMessage("§aA " + args[1] + " do evento foi setada.");
                    p.playSound(loc, Sound.LEVEL_UP, 1f, 1f);
                    return true;
                }

                if (args[1].equalsIgnoreCase("azul") || args[1].equalsIgnoreCase("vermelho")) {
                    Main.teamLocations.setLocation("Location.Spawn" + args[1].toUpperCase(), loc);
                    Main.teamLocations.saveConfig();
                    p.sendMessage("§aO spawn do " + args[1] + " do evento foi setada.");
                    p.playSound(loc, Sound.LEVEL_UP, 1f, 1f);
                    return true;
                }

                p.sendMessage("§cUse: /setnexus locais <entrada/saida/vermelho/azul>");
                return true;
            }

            p.sendMessage("§cUse: /setnexus <nexus/locais> <local>");
            return true;
        }
        return true;
    }
}
