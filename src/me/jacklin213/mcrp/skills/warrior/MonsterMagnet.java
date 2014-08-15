package me.jacklin213.mcrp.skills.warrior;

import java.util.List;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;
import me.jacklin213.mcrp.utils.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

@SkillInfo(
		name = "MonsterMagnet",
		description = "Attracts all hostile mobs around a certain block radius to you for a short duration",
		usage = "Use /skills mobmagnet",
		skilltype = SkillType.ACTIVE
)

public class MonsterMagnet extends Skill {

	private double radius;
	
	@Override
	protected void initiate() {
		radius = plugin.getConfig().getDouble("Classes.Warrior.MonsterMagnet.Radius");
	}
	
	@Override
	public void execute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + this.getName() + YELLOW + " ability");
			List<Entity> entities = getEntitiesAroundLocation(player.getLocation(), radius);
			if (entities.contains(player)) entities.remove(player);
			if (entities.size() == 0) {
				player.sendMessage(mcRP.getChatName() + RED + "There are no mobs around you!");
				return;
			}
			for (final Entity mob : entities) {
				if (mob instanceof Monster) {
					((Monster) mob).setTarget(player);
					ParticleEffect.PORTAL.display(player.getLocation(), (float)Math.random() * 2, (float)Math.random() * 2, (float)Math.random(), 5, 1000);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							((Monster) mob).setTarget(null);
						}
					}, getDuration());
				}
			}
		}
	}

}
