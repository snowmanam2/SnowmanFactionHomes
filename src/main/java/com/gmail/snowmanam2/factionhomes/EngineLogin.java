package com.gmail.snowmanam2.factionhomes;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;

public class EngineLogin extends Engine {
	private static EngineLogin i = new EngineLogin();
	public static EngineLogin get() { return i; }
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		Location location = p.getLocation();
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(location);
		Faction f = BoardColl.get().getFactionAt(psChunk);
		
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			p.teleport(location.getWorld().getSpawnLocation(), TeleportCause.PLUGIN);
			String msg = Messages.get("loginTeleport");
			p.sendMessage(msg);
		}
	}
}
