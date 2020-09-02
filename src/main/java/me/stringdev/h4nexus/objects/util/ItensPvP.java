package me.stringdev.h4nexus.objects.util;

import me.stringdev.h4nexus.apis.ItemBuilder;
import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class ItensPvP {

    private Player player;

    public ItensPvP(Player player){
        this.player = player;
    }

    public void giveArmour(Teams team){
        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setColor(team.getColorTeam()).glow().build());
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setColor(team.getColorTeam()).glow().build());
        player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setColor(team.getColorTeam()).glow().build());
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setColor(team.getColorTeam()).glow().build());
    }

    public void giveItens(){
        player.getInventory().addItem(new ItemBuilder(Material.STONE_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).build());
        player.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE).setAmount(3).build());
        player.getInventory().addItem(new ItemBuilder(Material.ARROW).setAmount(6).build());
        player.getInventory().addItem(new ItemBuilder(Material.BOW).build());
    }

    protected Player getPlayer() {
        return player;
    }
}
