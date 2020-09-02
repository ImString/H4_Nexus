package me.stringdev.h4nexus.apis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleAPI {
	
	private static Method a;
	private static Object enumTIMES;
	private static Object enumTITLE;
	private static Object enumSUBTITLE;
	private static Constructor<?> timeTitleConstructor;
	private static Constructor<?> textTitleConstructor;
	
	@SuppressWarnings("deprecation")
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		if(title == null || subtitle == null) {
			return;
		}
		
		// Transforma para segundos
		stay = stay*20;
		fadeOut = fadeOut*20;
		fadeIn = fadeIn*20;
		
		try {
			Object chatTitle = a.invoke(null, "{\"text\":\"" + title + "\"}");
			Object chatSubtitle = a.invoke(null,"{\"text\":\"" + subtitle + "\"}");
			Object timeTitlePacket = timeTitleConstructor.newInstance(enumTIMES, null, fadeIn, stay, fadeOut);
			Object titlePacket = textTitleConstructor.newInstance(enumTITLE, chatTitle);
			Object subtitlePacket = textTitleConstructor.newInstance(enumSUBTITLE, chatSubtitle);

			Reflections.sendPacket(player, timeTitlePacket);
			Reflections.sendPacket(player, titlePacket);
			Reflections.sendPacket(player, subtitlePacket);
		} catch (Throwable e) {
			try {
				player.sendTitle(title, subtitle);
			} catch (Exception e2) {}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void broadcastTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		if(title == null || subtitle == null) return;
		try {
			Object chatTitle = a.invoke(null, "{\"text\":\"" + title + "\"}");
			Object chatSubtitle = a.invoke(null,"{\"text\":\"" + subtitle + "\"}");
			Object timeTitlePacket = timeTitleConstructor.newInstance(enumTIMES, null, fadeIn, stay, fadeOut);
			Object titlePacket = textTitleConstructor.newInstance(enumTITLE, chatTitle);
			Object subtitlePacket = textTitleConstructor.newInstance(enumSUBTITLE, chatSubtitle);

			for (Player player : Bukkit.getOnlinePlayers()) {
				Reflections.sendPacket(player, timeTitlePacket);
				Reflections.sendPacket(player, titlePacket);
				Reflections.sendPacket(player, subtitlePacket);
			}
		} catch (Throwable e) {
			try {
				Bukkit.getOnlinePlayers().forEach(online -> online.sendTitle(title, subtitle));
			} catch (Exception e2) {}
		}
	}
		
	public static void load() {
		try  {
			Class<?> icbc = Reflections.getNMSClass("IChatBaseComponent");
			Class<?> ppot = Reflections.getNMSClass("PacketPlayOutTitle");
			Class<?> enumClass;

			if (ppot.getDeclaredClasses().length > 0) {
				enumClass = ppot.getDeclaredClasses()[0];
			} else {
				enumClass = Reflections.getNMSClass("EnumTitleAction");
			}
			if (icbc.getDeclaredClasses().length > 0) {
				a = icbc.getDeclaredClasses()[0].getMethod("a", String.class);
			} else {
				a = Reflections.getNMSClass("ChatSerializer").getMethod("a", String.class);
			}
			enumTIMES = enumClass.getField("TIMES").get(null);
			enumTITLE = enumClass.getField("TITLE").get(null);
			enumSUBTITLE = enumClass.getField("SUBTITLE").get(null);
			timeTitleConstructor = ppot.getConstructor(enumClass, icbc, int.class, int.class, int.class);
			textTitleConstructor = ppot.getConstructor(enumClass, icbc);
		} catch (Throwable e) {}
	}
}