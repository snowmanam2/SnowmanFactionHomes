package com.gmail.snowmanam2.factionhomes.cmd;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.snowmanam2.factionhomes.EssentialsWrapper;
import com.gmail.snowmanam2.factionhomes.Messages;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;

public class CmdHome extends FactionsCommand {
	private static CmdHome i = new CmdHome();
	public static CmdHome get() { return i; }
	
	public CmdHome()
	{
		// Aliases
		this.addAliases("home");

		// Parameters
		this.addParameter(TypeString.get(), "name", "home");
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		String homeName = this.readArg(null);
		
		List<String> homes = EssentialsWrapper.getHomes(me);
		
		if (homeName == null) {
			if (homes.size() == 1) {
				homeName = homes.get(0);
			} else {
				me.sendMessage(Messages.get("homeList", StringUtils.join(EssentialsWrapper.getHomes(me), ", ")));
				return;
			}
		} 
		
		this.attemptTeleportHome(me, homeName);
	}
	
	public void attemptTeleportHome(Player p, String home) {
		
		Location homeLoc = EssentialsWrapper.getHome(p, home);

		if (homeLoc == null) {
			p.sendMessage(Messages.get("invalidHome", home));
			return;
		}
		
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(homeLoc);
		Faction f = BoardColl.get().getFactionAt(psChunk);
		
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			String line2 = MessageFormat.format("teleport to your home at {0} {1}, {2}", 
					homeLoc.getWorld().getName(), homeLoc.getBlockX(), homeLoc.getBlockZ());
			String msg = Txt.parse("%s<b> does not allow you to", f.describeTo(mp, true));
			p.sendMessage(msg+"\n"+line2);
		} else {
			EssentialsWrapper.goHome(p, home);
		}
	}
}
