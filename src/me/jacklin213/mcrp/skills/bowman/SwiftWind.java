package me.jacklin213.mcrp.skills.bowman;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

@SkillInfo (
		name = "SwiftWind",
		description = "SwiftWind gives you a temporary speed boost",
		skilltype = SkillType.PASSIVE,
		cooldown = 60
	)

public class SwiftWind extends Skill {

	@Override
	public void execute(Player player, String args[]) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName());
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + "SuperSpeed" + YELLOW + " ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.getDuration() , 1));
		}
	}

}
