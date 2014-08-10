package me.jacklin213.mcrp.skills;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.events.SkillExecuteEvent;
import me.jacklin213.mcrp.managers.SkillManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Skill {
	
	public mcRP plugin;
	public SkillManager SM;
	
	public ChatColor AQUA = ChatColor.AQUA;
	public ChatColor BLACK = ChatColor.BLACK;
	public ChatColor BLUE = ChatColor.BLUE;
	public ChatColor RED = ChatColor.RED;
	public ChatColor GOLD = ChatColor.GOLD;
	public ChatColor GRAY = ChatColor.GRAY;
	public ChatColor GREEN = ChatColor.GREEN;
	public ChatColor YELLOW = ChatColor.YELLOW;
	public ChatColor WHITE = ChatColor.WHITE;
	
	public Skill () {
		plugin = mcRP.getPluginInstance();
	}
	
	public void setSkillManager() {
		SM = plugin.getSkillManager();
	}

	/**
	 * Runs a skill for the specified player. Calls a new {@link SkillExecuteEvent}
	 * <p>
	 * This method also runs the {@link #execute(Player, String[])} method which developers
	 * use to tell what a skill does upon executing.
	 * </p>
	 * @param player The player to run the skill for
	 * @param args The arguments from the command if needed
	 */
	public void run(Player player, String args[]) {
		SkillExecuteEvent event = new SkillExecuteEvent(player, this);
		Bukkit.getServer().getPluginManager().callEvent(event);
		this.execute(player, args);
	}
	
	/**
	 * Abstract execute directed at developers to control what happens when a spell is cast.
	 * @param player The player to execute the skill for
	 * @param args The arguments from the command if needed
	 */
	abstract public void execute(Player player, String args[]);
	
	/**
     * Gets the name of this Skill
     *
     * @return the Skill's name
     */
    public String getName() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        // Ternary operator
        return info == null ? this.getClass().getSimpleName() : info.name();
    }

    /**
     * Gets the description of this Skill
     *
     * @return the description of Skill
     */
    public String getDescription() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        return info == null ? "" : info.description();
    }
    
    /**
     * Gets the description of this Skill
     *
     * @return the description of Skill
     */
    public String getUsage() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        return info == null ? "" : info.usage();
    }
    
    /**
     * Gets the SkillType of this Skill
     *
     * @return the SkillType name
     */
    public SkillType getType() {
        SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
        // Ternary operator
        return info == null ? SkillType.OTHER : info.skilltype();
    }
    
    /**
     * Gets the cooldown of the Skill
     *
     * @return the cooldown in seconds
     */
    public int getCooldown() {
    	SkillInfo info = this.getClass().getAnnotation(SkillInfo.class);
    	return info == null ? Integer.valueOf(60) : info.cooldown();
    }
    
    /**
     * Gets the cooldown of the Skill for a player
     *
     * @return the cooldown in seconds
     */
    public int getCooldown(Player player) {
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
     * Gets the SkillEnum of the skill
     * @return SkillEnum if not null
     */
    public SkillEnum getEnum() {
    	return SkillEnum.matchSkillEnum(getName());
    }
	
    public boolean hasCooldown() {
    	if (getCooldown() == 0) {
    		return false;
    	}
    	return true;
    }
    
	/**
     * The annotation required for a skill to be used. <br>
     * This annotation contains all info on a skill
    */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	public @interface SkillInfo {
        String name() default ""; //"" defaults to class name

        String description() default "";
        
        String usage() default "";
        
        SkillType skilltype() default SkillType.OTHER;

        int cooldown() default 60;
        
        int duration() default 10;
       
    }
    
    /**
     * Enum to see if a Skill is an ACTIVE, PASSIVE, BOTH or OTHER
     *
     */
    public enum SkillType {
    	PASSIVE,
    	ACTIVE,
    	BOTH,
    	OTHER;
    	
    	public static SkillType getType(String string) {
    		for (SkillType type: SkillType.values()) {
    			if (type.toString().equalsIgnoreCase(string)) {
    				return type;
    			}
    		}
    		return null;
    	}
    }
    
}
