package me.stringdev.h4nexus.objects;

import me.stringdev.h4nexus.Main;
import me.stringdev.h4nexus.customEvents.OnPlayerLeaveOfEvent;
import me.stringdev.h4nexus.customEvents.OnPlayerJoinOfEvent;
import me.stringdev.h4nexus.enums.ReasonDisconnect;
import me.stringdev.h4nexus.enums.State;
import me.stringdev.h4nexus.enums.Teams;
import me.stringdev.h4nexus.objects.events.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H4NexusAPI {

    private static HashMap<State, Teams> lastPlayerTeam = new HashMap<>();
    public static EventManager nexusEvent;
    public static State stateEvent;
    private static Map<Player, Teams> teamPlayer = new HashMap<>();

    public void startEvent() {
        lastPlayerTeam.put(State.AGUARDANDO, Teams.AZUL);
        nexusEvent = NexusEvent.getInstance();
        nexusEvent.iniciar();
    }

    public void stopEvent(Player p) {
        nexusEvent.terminar("Cancelado", p);
    }

    public void teamWin(Teams team) {
        nexusEvent.terminar(team);
    }

    public void putPlayerEvent(Player p) {
        Teams team = lastPlayerTeam.get(State.AGUARDANDO) == Teams.AZUL ? Teams.VERMELHO : Teams.AZUL;
        teamPlayer.put(p, team);
        OnPlayerJoinOfEvent event = new OnPlayerJoinOfEvent(p, team);
        Bukkit.getServer().getPluginManager().callEvent(event);
        lastPlayerTeam.put(State.AGUARDANDO, team);
    }

    public void removePlayerEvent(Player p) {
        teamPlayer.remove(p);
        lastPlayerTeam.put(State.AGUARDANDO, getPlayerTeam(p));
    }

    public void forceRemove(Player p) {
        teamPlayer.remove(p);
    }

    public List<Player> playersTeam(Teams teams) {
        ArrayList<Player> newArray = new ArrayList<>();

        teamPlayer.entrySet()
                .stream()
                .filter(teamValue -> teamValue.getValue() == teams)
                .forEach(map -> newArray.add(map.getKey()));

        return newArray;
    }

    public Teams getPlayerTeam(Player player) {
        return teamPlayer.get(player);
    }

    public List<Player> players() {
        return new ArrayList<>(teamPlayer.keySet());
    }

    public int participantsSize() {
        return teamPlayer.size();
    }

    public boolean isParticipate(Player p) {
        return teamPlayer.containsKey(p);
    }

    public void msgPlayers(String msg) {
        teamPlayer.keySet().forEach(players -> players.sendMessage(msg));
    }

    public boolean isRunning() {
        return nexusEvent != null;
    }

    public State getState() {
        return stateEvent;
    }

    public void setState(State newState) {
        stateEvent = newState;
    }

    public Location locationTeam(Teams team) {
        return Main.teamLocations.getLocation("Location.Spawn" + team.getNameTeam().toUpperCase());
    }
}
