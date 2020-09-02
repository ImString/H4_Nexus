package me.stringdev.h4nexus.objects.events;

import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface EventManager extends Listener {

	public void iniciar();
	public void terminar(String reason, Player p);
	public void terminar(Teams team);

}
