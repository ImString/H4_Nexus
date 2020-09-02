package me.stringdev.h4nexus.apis;

import me.stringdev.h4nexus.enums.State;

import java.util.Date;
import java.util.HashMap;

public class TimeStop {

	public static HashMap<State, Long> stopNexus = new HashMap<State, Long>();

	public static boolean passou(State s) {
		Date vencimento = new Date(stopNexus.get(s));
		Date atual = new Date();
		return atual.after(vencimento);
	}

	public static String getTime(State s) {

		if (stopNexus.containsKey(s)) {
			long millis = stopNexus.get(s) - System.currentTimeMillis();
			System.currentTimeMillis();

			return "§aO evento acabara em §f$tempo!".replace("$tempo", TimeFormatter.format(millis));
		} else {
			return "";
		}
	}
}