package me.stringdev.h4nexus.apis;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.apis.Config;
import me.stringdev.h4nexus.listeners.*;
import org.bukkit.Bukkit;

public class Loader {

    public void loadAPI(){
        Reflections.loadUtils();
        TitleAPI.load();
    }
    public void loadConfigs() {
        Main.config = new Config("config.yml");
        if (!Main.config.exists()) {
            Main.config.saveDefaultConfig();
        }

        Main.message = new Config("messages.yml");
        if (!Main.message.exists()) {
            Main.message.saveDefaultConfig();
        }

        Main.locations = new Config("locais/locations.yml");
        if(!Main.locations.exists()){
            Main.locations.saveDefaultConfig();
        }

        Main.teamLocations = new Config("locais/team.yml");
        if(!Main.teamLocations.exists()){
            Main.teamLocations.saveDefaultConfig();
        }
    }

    public void loadListener(){
        Bukkit.getPluginManager().registerEvents(new OnPlayerSpeakChat(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnPlayerHitPlayer(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnPlayerPlayEvent(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnBuildAndBreak(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnBlockCommand(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnPlayerDeath(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnBossDamage(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnBossDeath(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnDropItem(), Main.instance);
        Bukkit.getPluginManager().registerEvents(new OnQuit(), Main.instance);
    }
}
