package me.jacklin213.mcrp.skills.warrior;

import java.util.List;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Skill.SkillInfo;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

@SkillInfo(
		name = "MobMagnet",
		description = "Attracts all hostile mobs around a certain block radius to you for a short duration",
		usage = "Use /skills mobmagnet",
		skilltype = SkillType.ACTIVE
)

public class MobMagnet extends Skill {

	@Override
	public void execute(Player player, String[] args) {
		if (plugin.SM.isCoolingDown(player, this.getName())) {
			player.sendMessage(mcRP.getChatName() + RED + "You still have a " + GOLD + plugin.SM.getSecondsLeft(player, this.getCooldown(player), this.getName()) + RED + " second cooldown");
		} else {
			plugin.SM.scheduleCooldown(player, this.getCooldown(player), this.getName()); 
			player.sendMessage(mcRP.getChatName() + YELLOW + "You have activated your " + GREEN  + this.getName() + YELLOW + " ability");
			List<Entity> entities = getEntitiesAroundLocation(player.getLocation(), plugin.getConfig().getDouble("Classes.Warrior.MobMagnet.Radius"));
			for (Entity mob : entities) {
				if (mob instanceof Monster) {
					((Monster) mob).setTarget(player);
				}
			}
		}
	}

}
