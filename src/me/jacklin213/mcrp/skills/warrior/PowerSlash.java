package me.jacklin213.mcrp.skills.warrior;

import java.util.List;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;
import me.jacklin213.mcrp.utils.ParticleEffect;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

@SkillInfo(
		name = "PowerSlash",
		description = "A slash which damages mobs/monsters next to you",
		usage = "Use /skills powerslash",
		skilltype = SkillType.ACTIVE
)

public class PowerSlash extends Skill {
	
	private int damage;
	private int radius;
	private int maxMob;
	private boolean monstersOnly;
	
	@Override
	protected void initiate() {
		damage = plugin.getConfig().getInt("Classes.Warrior.PowerSlash.Damage");
		radius = plugin.getConfig().getInt("Classes.Warrior.PowerSlash.Radius");
		maxMob = plugin.getConfig().getInt("Classes.Warrior.PowerSlash.MaxMobHit");
		monstersOnly = plugin.getConfig().getBoolean("Classes.Warrior.PowerSlash.MonstersOnly");
	}

	@Override
	public void execute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + this.getName() + YELLOW + " ability");
			List<LivingEntity> entities = getLivingEntitiesAroundLocation(player.getLocation(), radius);
			if (entities.contains(player)) entities.remove(player);
			if (maxMob > entities.size()) {
				maxMob = entities.size();
				if (maxMob == 0) player.sendMessage(mcRP.getChatName() + RED + "There are no mobs around you!");
				return;
			}
			/*if (plugin.getDebug())*/ plugin.log.info("Mobs detected: " + entities.size());
			for (int i = 0; i < maxMob; i++) {
				Entity entity = entities.get(i);
				if (monstersOnly && entity instanceof Monster) ((Monster) entity).damage(damage, player);
				else ((LivingEntity) entity).damage(damage, player);
				ParticleEffect.CRIT.display(player.getLocation(), (float)Math.random() * 2, (float)Math.random() * 2, (float)Math.random(), 5, 1000);
			}
		}
	}

	// Need to make it so that next attack does this not insta do this when "/skills powerslash" is used
}
