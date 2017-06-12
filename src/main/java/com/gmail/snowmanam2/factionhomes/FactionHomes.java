package com.gmail.snowmanam2.factionhomes;

import com.gmail.snowmanam2.factionhomes.cmd.CmdHome;
import com.gmail.snowmanam2.factionhomes.cmd.CmdHomes;
import com.gmail.snowmanam2.factionhomes.cmd.CmdSethome;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.util.MUtil;

public class FactionHomes extends MassivePlugin {
	@Override
	public void onEnableInner()
	{
		Messages.loadMessages(this);
		EssentialsWrapper.init();
		MPerm.getCreative(23001, "userhome", "userhome", "teleport home", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), false, true, true);
		
		this.activate(
			CmdHome.class,
			CmdHomes.class,
			CmdSethome.class,
			
			EngineLogin.class
		);
	}
}
