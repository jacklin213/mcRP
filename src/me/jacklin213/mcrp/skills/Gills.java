package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SkillInfo(
		name = "Gills",
		description = "Gills allows you to breath under water for a while"
)

public class Gills extends Skill{

	public Gills(mcRP instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exceute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getSkillName())) {
			player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCoolDown(player), this.getSkillName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCoolDown(player), this.getSkillName()); 
			player.sendMessage(mcRP.getChatName() + ChatColor.GREEN + " You have activated your Gills ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, this.getDuration(), 1));
		}
	}

}
