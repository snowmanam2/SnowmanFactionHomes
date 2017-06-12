package com.gmail.snowmanam2.factionhomes.cmd;

import org.apache.commons.lang.StringUtils;

import com.gmail.snowmanam2.factionhomes.EssentialsWrapper;
import com.gmail.snowmanam2.factionhomes.Messages;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.MassiveException;

public class CmdHomes extends FactionsCommand {
	private static CmdHomes i = new CmdHomes();
	public static CmdHomes get() { return i; }
	
	public CmdHomes()
	{
		// Aliases
		this.addAliases("homes");

	}
	
	@Override
	public void perform() throws MassiveException
	{
		me.sendMessage(Messages.get("homeList", StringUtils.join(EssentialsWrapper.getHomes(me), ", ")));
	}
	
}
