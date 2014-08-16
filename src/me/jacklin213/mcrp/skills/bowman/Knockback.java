package me.jacklin213.mcrp.skills.bowman;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;
import me.jacklin213.mcrp.utils.ParticleEffect;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SkillInfo(
		name = "Knockback",
		description = "Knocks an entity away from you so you can shoot it",
		usage = "Use /skills knockback",
		skilltype = SkillType.ACTIVE
)

public class Knockback extends Skill {

	private double damage;
	private double radius;
	private double knockbackRange;
	
	@Override
	protected void initiate() {
		radius = plugin.getConfig().getDouble("Classes.Warrior.Knockback.Radius");
		damage = plugin.getConfig().getDouble("Classes.Warrior.Knockback.Damage");
		knockbackRange = plugin.getConfig().getDouble("Classes.Warrior.Knockback.KnockbackRange");
	}
	
	@Override
	public void execute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + this.getName() + YELLOW + " ability");
			Entity target = getTargetEntity(player, radius, null);
			knockbackEntity(target, damage, knockbackRange);
			ParticleEffect.CRIT.display(player.getLocation(), (float)Math.random() * 2, (float)Math.random() * 2, (float)Math.random(), 5, 1000);
		}
	}
	
}
