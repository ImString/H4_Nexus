package me.stringdev.h4nexus.enums;

import org.bukkit.Color;

public enum Teams {

    AZUL("Azul", Color.BLUE, "§3"),
    VERMELHO("Vermelho", Color.RED, "§c");

    private String nameTeam;
    private Color colorTeam;
    private String colorString;

    Teams(String nameTeam, Color colorTeam, String colorString){
        this.nameTeam = nameTeam;
        this.colorTeam = colorTeam;
        this.colorString = colorString;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public Color getColorTeam() {
        return colorTeam;
    }

    public String getColorString() {
        return colorString;
    }
}
