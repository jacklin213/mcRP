package me.jacklin213.mcrp.skills;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.events.SkillExecuteEvent;
import me.jacklin213.mcrp.managers.SkillManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
	 * This method also runs the {@link #initiate()} method which developers
	 * can use to initiate variables before executing their skill.
	 * <br></br>
	 * This method also runs the {@link #execute(Player, String[])} method which developers
	 * use to tell what a skill does upon executing.
	 * </p>
	 * @param player The player to run the skill for
	 * @param args The arguments from the command if needed
	 */
	public void run(Player player, String args[]) {
		SkillExecuteEvent event = new SkillExecuteEvent(player, this);
		Bukkit.getServer().getPluginManager().callEvent(event);
		this.initiate();
		this.execute(player, args);
	}
	
	/**
	 * Abstract execute directed at developers to control what happens when a spell is cast.
	 * @param player The player to execute the skill for
	 * @param args The arguments from the command if needed
	 */
	abstract public void execute(Player player, String args[]);
	
	/**
	 * Method which allows developers to initiate their variables.
	 * <p>
	 * The default method does nothing
	 * <br></br>
	 * Called in {@link #run(Player, String[])}
	 * </p>
	 */
	protected void initiate() {}
	
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
     * Gets the default cooldown of the Skill
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
        if (plugin.getConfig().contains("Classes.Bowman." + info.name() + ".Cooldown")) {
        	cooldown = plugin.getConfig().getInt("Classes.Bowman." + info.name() + ".Cooldown");
        	return cooldown;
        }
        if (plugin.getConfig().contains("Classes.Mage." + info.name() + ".Cooldown")) {
        	cooldown = plugin.getConfig().getInt("Classes.Warrior." + info.name() + ".Cooldown");
        	return cooldown;
        }
        if (plugin.getConfig().contains("Classes.Theif." + info.name() + ".Cooldown")) {
        	cooldown = plugin.getConfig().getInt("Classes.Theif." + info.name() + ".Cooldown");
        	return cooldown;
        }
        if (plugin.getConfig().contains("Classes.Warrior." + info.name() + ".Cooldown")) {
        	cooldown = plugin.getConfig().getInt("Classes.Warrior." + info.name() + ".Cooldown");
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
        if (plugin.getConfig().contains("Classes.Bowman." + info.name() + ".Duration")) {
        	duration = plugin.getConfig().getInt("Classes.Bowman." + info.name() + ".Duration");
        	return duration * 20;
        }
        if (plugin.getConfig().contains("Classes.Mage." + info.name() + ".Duration")) {
        	duration = plugin.getConfig().getInt("Classes.Warrior." + info.name() + ".Duration");
        	return duration * 20;
        }
        if (plugin.getConfig().contains("Classes.Theif." + info.name() + ".Duration")) {
        	duration = plugin.getConfig().getInt("Classes.Theif." + info.name() + ".Duration");
        	return duration * 20;
        }
        if (plugin.getConfig().contains("Classes.Warrior." + info.name() + ".Duration")) {
        	duration = plugin.getConfig().getInt("Classes.Warrior." + info.name() + ".Duration");
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
    
    // Other methods that maybe required in skills
    /**
	 * Gets a {@code List<Blocks>} within the specified radius around the specified location.
	 * @param location The base location
	 * @param radius The block radius from location to include within the list of blocks
	 * @return The list of Blocks
	 */
	public List<Block> getBlocksAroundLocation(Location location, double radius) {
		List<Block> blocks = new ArrayList<Block>();

		int xRoot = location.getBlockX();
		int yRoot = location.getBlockY();
		int zRoot = location.getBlockZ();

		int r = (int) radius * 4;

		for (int x = xRoot - r; x <= xRoot + r; x++) {
			for (int y = yRoot - r; y <= yRoot + r; y++) {
				for (int z = zRoot - r; z <= zRoot + r; z++) {
					Block block = location.getWorld().getBlockAt(x, y, z);
					if (block.getLocation().distance(location) <= radius) {
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}
	
	/**
	 * Gets the distance from a point in a straight line.
	 * @param line The direction of the line
	 * @param pointonline The point on the line
	 * @param point The farthest point on the line
	 * @return
	 */
	public double getDistanceFromLine(Vector line, Location pointonline, Location point) {

		Vector AP = new Vector();
		double Ax, Ay, Az;
		Ax = pointonline.getX();
		Ay = pointonline.getY();
		Az = pointonline.getZ();

		double Px, Py, Pz;
		Px = point.getX();
		Py = point.getY();
		Pz = point.getZ();

		AP.setX(Px - Ax);
		AP.setY(Py - Ay);
		AP.setZ(Pz - Az);

		return (AP.crossProduct(line).length()) / (line.length());
	}
	
	/**
	 * Gets a {@code List<Entity>} of entities around a specified radius from the specified area.
	 * @param location The base location
	 * @param radius The radius of blocks to look for entities from the location
	 * @return A list of entities around a point
	 */
	public List<Entity> getEntitiesAroundLocation(Location location, double radius) {

		List<Entity> entities = location.getWorld().getEntities();
		List<Entity> list = location.getWorld().getEntities();

		for (Entity entity : entities) {
			if (entity.getWorld() != location.getWorld()) {
				list.remove(entity);
			} else if (entity.getLocation().distance(location) > radius) {
				list.remove(entity);
			}
		}

		return list;

	}
	
	/**
	 * Gets a {@code List<LivingEntity>} of entities around a specified radius from the specified area.
	 * @param location The base location
	 * @param radius The radius of blocks to look for livining entities from the location
	 * @return A list of living entities around a point
	 */
	public List<LivingEntity> getLivingEntitiesAroundLocation(Location location, double radius) {

		List<LivingEntity> entities = location.getWorld().getLivingEntities();
		List<LivingEntity> list = location.getWorld().getLivingEntities();

		for (Entity entity : entities) {
			if (entity.getWorld() != location.getWorld()) {
				list.remove(entity);
			} else if (entity.getLocation().distance(location) > radius) {
				list.remove(entity);
			}
		}

		return list;

	}
	
	/**
	 * Gets the entity targeted by the Player.
	 * @param player The player to cast this method from
	 * @param range The range to include
	 * @param avoid Entities to avoid
	 * @return A list of entities that are targeted
	 */
	public Entity getTargetEntity(Player player, double range, List<Entity> avoid) {
		double longestr = range + 1;
		Entity target = null;
		Location eyeloc = player.getEyeLocation();
		Vector direction = player.getEyeLocation().getDirection().normalize();
		for (Entity entity : eyeloc.getWorld().getEntities()) {
			if (avoid != null && avoid.contains(entity))
				continue;
			if (entity.getLocation().distance(eyeloc) < longestr
					&& getDistanceFromLine(direction, eyeloc, entity.getLocation()) < 2
					&& (entity instanceof LivingEntity)
					&& entity.getEntityId() != player.getEntityId()
					&& entity.getLocation().distance(eyeloc.clone().add(direction)) < 
					entity.getLocation().distance(eyeloc.clone().add(direction.clone().multiply(-1)))) {
				target = entity;
				longestr = entity.getLocation().distance(eyeloc);
			}
		}
		return target;
	}
	
	public void knockbackEntity(Entity entity, double damage, double range) {
		entity.setVelocity(entity.getVelocity().multiply(-range));
		((LivingEntity) entity).damage(damage);
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
