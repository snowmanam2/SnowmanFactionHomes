package com.gmail.snowmanam2.factionhomes;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages {
	private static FileConfiguration messages = null;
	private static File configFile = null;
	private Messages() {
	}
	public static String get(String key) {
		if (messages != null) {
			if (messages.contains(key)) {
				String message = String.valueOf(messages.get(key));
				return ChatColor.translateAlternateColorCodes('&', message);
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	public static String get(String key, Object... args) {
		String message = Messages.get(key);
		
		for (int i = 0; i < args.length ; i++) {
			String replaceString = "{"+i+"}";
			
			if (message.contains(replaceString)) {
				message = message.replace(replaceString, String.valueOf(args[i]));
			}
		}
		
		return message;
	}
	
	public static void loadMessages (JavaPlugin plugin) {
		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), "messages.yml");
		}
		messages = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(plugin.getResource("messages.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			messages.addDefaults(defConfig);
		} else {
			plugin.getLogger().warning("Wasn't able to get default config file!");
		}
		
		messages.options().copyDefaults(true);
		
		try {
			messages.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
