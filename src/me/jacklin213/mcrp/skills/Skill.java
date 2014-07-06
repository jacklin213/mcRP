package me.jacklin213.mcrp.skills;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.managers.SkillManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Skill {
	
	public mcRP plugin;
	public SkillManager SM;
	
	ChatColor AQUA = ChatColor.AQUA;
	ChatColor BLACK = ChatColor.BLACK;
	ChatColor BLUE = ChatColor.BLUE;
	ChatColor RED = ChatColor.RED;
	ChatColor GOLD = ChatColor.GOLD;
	ChatColor GRAY = ChatColor.GRAY;
	ChatColor GREEN = ChatColor.GREEN;
	ChatColor YELLOW = ChatColor.YELLOW;
	ChatColor WHITE = ChatColor.WHITE;
	
	public Skill (mcRP instance) {
		plugin = instance;
	}
	
	public void setSkillManager() {
		SM = plugin.getSkillManager();
	}

	abstract public void exceute(Player player, String args[]);
	
	/**
     * Gets the name of this Skill
     *
     * @return the Skill's name
     */
    public String getSkillName() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        // Ternary operator
        return info == null ? this.getClass().getSimpleName() : info.name();
    }

    /**
     * Gets the description of this Skill
     *
     * @return the description of Skill
     */
    public String getSkillDescription() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        return info == null ? "" : info.description();
    }
    
    /**
     * Gets the cooldown of the Skill for a player
     *
     * @return the cooldown in seconds
     */
    public int getCoolDown(Player player) {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        if (player.hasPermission("mcrp.skills.nocooldown")) {
        	return 0;
        }
        int cooldown = 60;
        if (plugin.getConfig().contains("Skills." + info.name() + ".Cooldown")) {
        	cooldown = plugin.getConfig().getInt("Skills." + info.name() + ".Cooldown");
        	return cooldown;
        }
		return cooldown; 
    }
    
    /**
     * Gets the duration of a passive Skill
     *
     * @return the duration in ticks
     */
    public int getDuration() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        int duration = info.duration();
        if (plugin.getConfig().contains("Skills." + info.name() + ".Duration")) {
        	duration = plugin.getConfig().getInt("Skills." + info.name() + ".Duration");
        	return duration * 20;
        }
		return duration * 20; 
    }
	
	/**
     * The annotation required for a skill to be used. <br>
     * This annotation contains all info on a skill
    */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface SkillInfo {
        String name() default ""; //"" defaults to class name

        String description() default "";

        int cooldown() default 60;
        
        int duration() default 10;

    }
}
