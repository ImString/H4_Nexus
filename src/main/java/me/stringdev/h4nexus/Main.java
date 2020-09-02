package me.stringdev.h4nexus;

import me.stringdev.h4nexus.apis.Config;
import me.stringdev.h4nexus.apis.Loader;
import me.stringdev.h4nexus.commands.NexusCommand;
import me.stringdev.h4nexus.commands.SetNexuCommand;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.objects.H4NexusAPI;
import me.stringdev.h4nexus.objects.NexusTeamAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;
    public static Config config;
    public static Config message;
    public static Config locations;
    public static Config teamLocations;
    public static Economy economy;

    @Override
    public void onEnable() {
        instance = this;

        if (!hookEconomy()) {
            Bukkit.getConsoleSender().sendMessage("[H4_Nexus] §cVault não encontrado!");
            Bukkit.getPluginManager().disablePlugin(instance);
        }

        new H4NexusAPI().setState(State.FECHADO);
        Loader loader = new Loader();
        loader.loadAPI();
        loader.loadConfigs();
        loader.loadListener();

        getCommand("nexus").setExecutor(new NexusCommand());
        getCommand("setnexus").setExecutor(new SetNexuCommand());
    }

    @Override
    public void onDisable() {
        new NexusTeamAPI().removeAllNexus();
    }

    private boolean hookEconomy() {
        if (!getServer().getPluginManager().isPluginEnabled("Vault"))
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;

        economy = rsp.getProvider();
        return economy != null;
    }
}
