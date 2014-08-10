package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.entity.Player;

@SkillInfo(
		name = "MartyBoom",
		description = "Martyboom explode you and nearby targets",
		usage = "Use /skills martyboom or right-click whilst holding sulphur",
		skilltype = SkillType.ACTIVE
)

public class MartyBoom extends Skill{

	@Override
	public void execute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + RED  + "MartyBoom" + YELLOW + " ability");
			player.getWorld().createExplosion(player.getLocation(), 4.0F);
			if (player.getHealth() > 0)
				player.setHealth(0);
		}
	}

}
