package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SkillInfo(
		name = "SuperPunch",
		description = "SuperPunch a target through his armour",
		usage = "Use /skills superpunch <target>",
		skilltype = SkillType.ACTIVE
)

public class SuperPunch extends Skill{

	public SuperPunch(mcRP instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exceute(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);
		if (args.length == 1)
			player.sendMessage(mcRP.getChatName() + RED + "You NEED to define a player to use this skill");
		else if (args.length == 2)
			if (plugin.SM.isCoolingDown(player, this.getName())) {
				player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + RED + "Player: " +  GOLD + args[0] + RED + " is not online!");
			} else {
				target.setHealth(target.getHealth() - 5);
				plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
				player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + RED  + "SuperPunch" + YELLOW + " ability on " + GREEN + target.getName());
				if (player.getHealth() > 0)
					player.setHealth(0);
			}
		
	}

}
