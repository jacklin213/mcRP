package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SkillInfo(
		name = "Confuse",
		description = "Confuse allows you to confuse a target",
		usage = "Use /skills confuse <target>",
		skilltype = SkillType.BOTH
)

public class Confuse extends Skill{

	@Override
	public void execute(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);
		if (args.length == 1) {
			player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
		} else if (args.length == 2) {
			if (plugin.SM.isCoolingDown(player, this.getName())) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else {
				plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, this.getDuration(), 1));
				player.sendMessage(mcRP.getChatName() + ChatColor.GREEN + " You have activated your Confuse ability on " + GOLD + target.getName());
				target.sendMessage(mcRP.getChatName() + ChatColor.GREEN + " You were confused by " + GOLD + player);
			}
		}
	}

}
