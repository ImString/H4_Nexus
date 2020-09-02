package me.stringdev.h4nexus.enums;

public enum State {

    FECHADO("Fechado", "§c"),
    AGUARDANDO("Aguardando", "§a"),
    INICIANDO("Iniciando", "§e"),
    INICIADO("Ocorrendo", "§6");

    private String nameState;
    private String colorState;

    State(String nameState, String colorState){
        this.nameState = nameState;
        this.colorState = colorState;
    }

    public String getNameState() {
        return nameState;
    }

    public String getColorState() {
        return colorState;
    }
}