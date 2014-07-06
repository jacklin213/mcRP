package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SkillInfo(
		name = "Confuse",
		description = "Confuse allows you to confuse a target"
)

public class Confuse extends Skill{

	public Confuse(mcRP instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exceute(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);
		if (args.length == 1) {
			player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
		} else if (args.length == 2) {
			if (plugin.SM.isCoolingDown(player, this.getSkillName())) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCoolDown(player), this.getSkillName()) + RED + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else {
				plugin.SM.scheduleCooldown(player, this.getCoolDown(player), this.getSkillName()); 
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, this.getDuration(), 1));
				player.sendMessage(mcRP.getChatName() + ChatColor.GREEN + " You have activated your Confuse ability on " + GOLD + target.getName());
				target.sendMessage(mcRP.getChatName() + ChatColor.GREEN + " You were confused by " + GOLD + player);
			}
		}
	}

}
