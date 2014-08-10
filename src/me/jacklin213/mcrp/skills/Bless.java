package me.jacklin213.mcrp.skills;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SkillInfo(
		name = "Bless",
		description = "Bless puts you back at full HP",
		usage = "Use /skills bless or /skills bless <target>", 
		skilltype = SkillType.BOTH
)

public class Bless extends Skill{

	@Override
	public void execute(Player player, String args[]) {
		if (args.length == 1) {
			if (plugin.SM.isCoolingDown(player, this.getName())) {
				player.sendMessage(mcRP.getChatName() + RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
			} else {
				plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName());
				player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + "Bless" + YELLOW + " ability");
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, this.getDuration(), 1));
			}
		} else if (args.length == 2) {
			Player target = Bukkit.getPlayer(args[1]);
			if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + RED + "Player: " +  GOLD + args[0] + RED + " is not online!");
			} else if (plugin.SM.isCoolingDown(player, this.getName())) {
				player.sendMessage(mcRP.getChatName() + RED + " You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
			} else {
				plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName());
				player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + "Bless" + YELLOW + " ability on "  + GREEN + target.getName());
				target.sendMessage(mcRP.getChatName() + YELLOW + "You have been healed by " + GREEN + player);
				target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, this.getDuration(), 1));
			}
		}
	}

}
