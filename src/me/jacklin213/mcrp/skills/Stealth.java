package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


@SkillInfo(
		name = "Stealth",
		description = "Grants player stealth for a configurable amount of time",
		usage = "Use /skills stealth",
		skilltype = SkillType.PASSIVE
)
public class Stealth extends Skill{

	public Stealth(mcRP instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exceute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + this.getName() + YELLOW + " ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, this.getDuration(), 1));
		}
	}
}
