package com.gmail.snowmanam2.factionhomes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.NumberUtil;

public class EssentialsWrapper {
	public static Essentials ess;
	
	public static void init () {
		ess = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	}
	
	public static Location getHome(Player p, String home) {
		try {
			User user = ess.getUser(p);
			return user.getHome(home);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<String> getHomes(Player p) {
		User user = ess.getUser(p);
		return user.getHomes();
	}
	
	public static void goHome (Player p, String home) {
		final Trade charge = new Trade("home", ess);
		User user = ess.getUser(p);
		Location homeLoc = EssentialsWrapper.getHome(p, home);
		try {
			user.getTeleport().teleport(homeLoc, charge, TeleportCause.COMMAND);
		} catch (Exception e) {
			p.sendMessage(Messages.get("invalidHome", home));
		}
	}
	
	public static void teleportPlayer (Player p, Location loc) {
		User user = ess.getUser(p);
		try {
			user.getTeleport().teleport(loc, null, TeleportCause.COMMAND);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	 
	public static void setHome(Player p, String name) {
		User user = ess.getUser(p);
		Location location = p.getLocation();
		List<String> homes = user.getHomes();
		int limit = ess.getSettings().getHomeLimit(user);
		
		if (name == null) {
				name = "home";
		}
		
		if (!homes.contains(name) && homes.size() >= limit) {
			p.sendMessage(Messages.get("maxHomes"));
			return;
		}
		if ("bed".equals(name) || NumberUtil.isInt(name))
		{
			p.sendMessage(Messages.get("invalidHomeName"));
		}
		
		user.setHome(name, location);
	}
}
