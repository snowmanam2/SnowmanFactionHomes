package com.gmail.snowmanam2.factionhomes.cmd;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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



public class CmdSethome extends FactionsCommand {
	private static CmdSethome i = new CmdSethome();
	public static CmdSethome get() { return i; }
	
	public CmdSethome()
	{
		// Aliases
		this.addAliases("sethome");

		// Parameters
		this.addParameter(TypeString.get(), "name", "home");
	}
	
	@Override
	public void perform() throws MassiveException
	{
		String homeName = this.readArg(null);
		
		List<String> homes = EssentialsWrapper.getHomes(me);
		
		if (homeName == null) {
			if( homes.size() > 1) {
				me.sendMessage(Messages.get("homeList", StringUtils.join(EssentialsWrapper.getHomes(me), ", ")));
				return;
			} else if (homes.size() == 1) {
				homeName = homes.get(0);
			}
		}
		
		this.attemptSetHome(me, homeName);

	}
	
	public void attemptSetHome(Player p, String home) {
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(p.getLocation());
		Faction f = BoardColl.get().getFactionAt(psChunk);
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			String msg = Txt.parse("%s<b> does not allow you to set home", f.describeTo(mp, true));
			p.sendMessage(msg);
		} else {
			EssentialsWrapper.setHome(p, home);
			p.sendMessage(Messages.get("homeSet"));
		}
		
	}
}
