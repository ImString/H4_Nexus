package me.stringdev.h4nexus.objects;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NexusTeamAPI {

    public static List<NexusBoss> nexusBosses = new ArrayList<>();

    public void createAllNexus() {
        if (!isSpawned(Teams.AZUL) && !isSpawned(Teams.VERMELHO)) {
            Main.teamLocations.getSection("Nexus").getKeys(false).forEach(team -> {
                Location location = Main.teamLocations.getLocation("Nexus." + team).add(0, 1, 0);

                Entity entity = location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
                entity.setCustomName("§d§lNEXUS §f- §e§l" + team.toUpperCase() + " §f(§aHP: §f" + Main.config.getInt("NexusLife." + team) + "§f)");
                entity.setMetadata("Cristal", new FixedMetadataValue(Main.instance, true));
                entity.setCustomNameVisible(true);

                nexusBosses.add(new NexusBoss(Teams.valueOf(team.toUpperCase()), entity, Main.config.getInt("NexusLife." + team)));
            });
        }
    }

    public void removeAllNexus() {
        nexusBosses.forEach(nexusBoss -> {
            nexusBoss.getBoss().remove();
        });
        nexusBosses.clear();
    }

    public void createNexus(Teams team) {
        Location location = Main.teamLocations.getLocation("Nexus." + team.getNameTeam());

        Entity entity = location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
        entity.setCustomName("§d§lNEXUS §f- §e§l" + team.getNameTeam().toUpperCase() + " §f(§aHP: §f" + Main.config.getString("NexusLife." + team) + "§f)");
        entity.setMetadata("Cristal", new FixedMetadataValue(Main.instance, true));
        entity.setCustomNameVisible(true);

        nexusBosses.add(new NexusBoss(team, entity, Main.config.getInt("NexusLife." + team.getNameTeam())));
    }

    public void removeNexus(Teams teams) {
        nexusBosses.forEach(nexusBoss -> {
            if (nexusBoss.getColorTeam() == teams) {
                nexusBoss.getBoss().remove();
                nexusBosses.remove(nexusBoss);
            }
        });
    }

    public boolean isSpawned(Teams team) {
        return nexusBosses.stream().filter(map -> map.getColorTeam() == team).collect(Collectors.toList()).size() >= 1 ? true : false;
    }

    public NexusBoss getNexus(Teams team) {
        return nexusBosses.stream().filter(map -> map.getColorTeam() == team).findFirst().get();
    }

    public NexusBoss getNexus(Entity entity) {
        return nexusBosses.stream().filter(map -> map.getBoss() == entity).findFirst().get();
    }
}
