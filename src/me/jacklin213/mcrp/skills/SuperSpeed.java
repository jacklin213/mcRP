package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SkillInfo (
	name = "SuperSpeed",
	description = "SuperSpeed gives you a temporary speed boost",
	cooldown = 60,
	duration = 10
)

public class SuperSpeed extends Skill{
	
	public SuperSpeed(mcRP instance) {
		super(instance);
	}

	@Override
	public void exceute(Player player, String args[]) {
		if (plugin.SM.isCoolingDown(player, this.getSkillName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCoolDown(player), this.getSkillName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCoolDown(player), this.getSkillName());
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + "SuperSpeed" + YELLOW + " ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.getDuration() , 1));
		}
	}
	
}
