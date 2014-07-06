package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SkillInfo(
		name = "Bless",
		description = "Bless puts you back at full HP"
)

public class Bless extends Skill{

	public Bless(mcRP instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exceute(Player player, String args[]) {
		if (args.length == 1) {
			if (plugin.SM.isCoolingDown(player, this.getSkillName())) {
				player.sendMessage(mcRP.getChatName() + RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCoolDown(player), this.getSkillName()) + RED + " second cooldown");
			} else {
				plugin.SM.scheduleCooldown(player, this.getCoolDown(player), this.getSkillName());
				player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + "Bless" + YELLOW + " ability");
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, this.getDuration(), 1));
			}
		} else if (args.length == 2) {
			Player target = Bukkit.getPlayer(args[1]);
			if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + RED + "Player: " +  GOLD + args[0] + RED + " is not online!");
			} else if (plugin.SM.isCoolingDown(player, this.getSkillName())) {
				player.sendMessage(mcRP.getChatName() + RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCoolDown(player), this.getSkillName()) + RED + " second cooldown");
			} else {
				plugin.SM.scheduleCooldown(player, this.getCoolDown(player), this.getSkillName());
				player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + "Bless" + YELLOW + " ability on "  + GREEN + target.getName());
				target.sendMessage(mcRP.getChatName() + YELLOW + "You have been healed by " + GREEN + player);
				target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, this.getDuration(), 1));
			}
		}
	}

}
