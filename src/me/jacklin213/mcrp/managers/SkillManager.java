package me.jacklin213.mcrp.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import me.jacklin213.mcrp.Character;
import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.classes.ClassType;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.skills.Bless;
import me.jacklin213.mcrp.skills.Confuse;
import me.jacklin213.mcrp.skills.Gills;
import me.jacklin213.mcrp.skills.MartyBoom;
import me.jacklin213.mcrp.skills.Might;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Stealth;
import me.jacklin213.mcrp.skills.SuperJump;
import me.jacklin213.mcrp.skills.SuperPunch;
import me.jacklin213.mcrp.skills.SuperSpeed;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class SkillManager {
	
	private final mcRP plugin;
	private HashMap<String, HashMap<String, Long>> cooldowns = new HashMap<String, HashMap<String, Long>>();
	private HashMap<String, Skill> skills = new HashMap<String, Skill>();
	// HashMap<classname, <skillname, skill>>
	private HashMap<String, HashMap<String, Skill>> classSkills = new HashMap<String, HashMap<String, Skill>>();
	//private HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	public SkillManager(mcRP instance) {
		plugin = instance;
		this.addSkills();
		this.addClassSkills();
		plugin.log.info("Skills and Class Skills have been loaded");
	}

	private void addSkills() {
		//Note: These skill names have to be in lower case.
		//As of v1.3.1 BETA 3 the above can be ignored
		this.registerSkill(new Bless());
		this.registerSkill(new Confuse());
		this.registerSkill(new Gills());
		this.registerSkill(new MartyBoom());
		this.registerSkill(new Might());
		this.registerSkill(new Stealth());
		this.registerSkill(new SuperJump());
		this.registerSkill(new SuperPunch());
		this.registerSkill(new SuperSpeed());
	}
	
	private void addClassSkills() {
		Map<String, RPClass> map = plugin.RPCM.getRPClasses();
		if (map == null) plugin.disablePlugin(Level.SEVERE, "Unable to load ClassSkills. NullPointerExecption");
		if (plugin.getDebug()) plugin.log.info("Class names: ");
		for (String className : map.keySet()) {
			if (plugin.getDebug()) plugin.log.info(className);
			classSkills.put(className, map.get(className).getSkills());
		}
		//Debug
		if (plugin.getDebug()) {
			plugin.log.info("Skill names: ");
			for (String skillName : map.get("Warrior").getSkills().keySet()) {
				plugin.log.info(skillName);
			}
		}
	}
	
	public Skill getSkill(String skillName) {
		for (String name : skills.keySet()) {
			if (name.equalsIgnoreCase(skillName)) {
				return skills.get(name);
			}
		}
		return null;
	}
	
	public Skill getClassSkill(String className, String skillName) {
		for (String classKey : classSkills.keySet()) {
			if (classKey.equalsIgnoreCase(className)) {
				for (String skillKey : classSkills.get(classKey).keySet()) {
					if (skillKey.equalsIgnoreCase(skillName)) {
						return classSkills.get(classKey).get(skillKey);
					}
				}
			}
		}
		return null;
	}
	
	public boolean executeSkill(Player player, String skillName, String args[]) {
		Character character = CharacterManager.getCharacter(player);
		Skill skill;
		if (character == null) {
			plugin.log.severe("Error in loading character for " + player.getName() + " while executing skill " + skillName);
			return false;
		}
		// If players have a class are they skill allowed legacy moves
		if (plugin.getConfig().getBoolean("AllowLegacy")) {
			skill = getSkill(skillName);
			if (skill == null) skill = getClassSkill(character.getRPClassName(), skillName);
		} else {
			if (character.getClassType() == ClassType.LEGACY || character.getClassType() == ClassType.NOVICE) {
				skill = getSkill(skillName);
			} else {
				skill = getClassSkill(character.getRPClassName(), skillName);
			}
		}
		if (skill == null) return false;
		skill.run(player, args);
		return true;
		
	}
	
	public HashMap<String, HashMap<String, Skill>> getClassSkills() {
		return this.classSkills;
	}
	
	public HashMap<String, Skill> getSkills() {
		return this.skills;
	}
	
	public String getSkillNames() {
		StringBuilder names = new StringBuilder();
		int i = 0;
		for (String name : skills.keySet()) {
			names.append(name);
			if (i != skills.keySet().size() -1) {
				names.append(", ");
			} else {
				names.append(".");
			}
			i++;
		}
		return names.toString();
	}
	
	public ArrayList<String> getSkillNamesList() {
		ArrayList<String> names= new ArrayList<String>();
		for (String name : skills.keySet()) {
			names.add(name);
		}
		return names;
	}
	
	public void scheduleCooldown(final Player player, int cooldown, final String skillName) {
		if (cooldown == 0) {
			return;
		}
		HashMap<String, Long> localcd = new HashMap<String, Long>();
		//int cooldown = this.plugin.getConfig().getInt("Skills.Cooldown");
		/*final String playerName = player.getName();*/ //Not needed for 1.7.9
		final String pID = player.getUniqueId().toString();
		if (cooldowns.containsKey(pID) && cooldowns.get(pID).containsKey(skillName)) {			
			localcd = cooldowns.get(pID);
			localcd.put(skillName, System.currentTimeMillis());
			cooldowns.put(pID, localcd);
		} else {
			localcd.put(skillName, System.currentTimeMillis());
			cooldowns.put(pID, localcd);
		}
		
		/*this.cooldowns.put(pID, System.currentTimeMillis());*/
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				SkillManager.this.cooldowns.get(pID).remove(skillName);
			}
		}, cooldown*20);
	}
	
	public Integer getSecondsLeft(final Player player, int cooldown, String skillName) {
		//int cooldown = this.plugin.getConfig().getInt("Skills.Cooldown");
		/*String playerName = player.getName();*/ //Not needed for 1.7.9
		String pID = player.getUniqueId().toString();
		if (cooldowns.containsKey(pID) && cooldowns.get(pID).containsKey(skillName)) {
			long oldTime = this.cooldowns.get(pID).get(skillName);
			long curTime = System.currentTimeMillis();
			int secondsLeft = (int)((cooldown - (curTime - oldTime)/1000));
			return secondsLeft;
		} else {
			return 0;
		}
	}
	
	public boolean isCoolingDown(Player player, String skillName) {
		String pID = player.getUniqueId().toString();
		if (cooldowns.containsKey(pID) && (cooldowns.get(pID).containsKey(skillName))) {
			return true;
		}
		return false;
	}
	
	private void registerSkill(Skill skill) {
		skills.put(skill.getName(), skill);
	}
	
	// Old Skill management way
	/*public void superSpeed(Player player) {
		if (isCoolingDown(player)) {
 	     player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your SuperSpeed ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
		}
	}

	public void bless(Player player, String[] args) {
		if (args.length == 1) {
			if (isCoolingDown(player)) {
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else {
				scheduleCooldown(player);
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your Bless ability");
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
			}
		} else if (args.length == 2) {
			Player target = Bukkit.getPlayer(args[1]);
			if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else if (isCoolingDown(player)) {
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else {
				scheduleCooldown(player);
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your Bless ability on " + target.getName());
				target.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have been healed by " + player);
				target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
			}
		}
	}

	public void might(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your Might ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
		}
	}

	public void gills(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your Gills ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
		}
	}

	public void superJump(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your SuperJump ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
		}
	}

	public void martyboom(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your martyboom ability");
			player.getWorld().createExplosion(player.getLocation(), 4.0F);
			if (player.getHealth() > 0)
				player.setHealth(0);
		}
  	}

	public void superPunch(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);
		if (args.length == 1)
			player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
		else if (args.length == 2)
			if (isCoolingDown(player)) {
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else {
				target.setHealth(target.getHealth() - 5);
				scheduleCooldown(player);
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your SuperPunch ability on " + target.getName());
				if (player.getHealth() > 0)
					player.setHealth(0);
			}
	}

	public void confuse(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);
		if (args.length == 1) {
			player.sendMessage(mcRP.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
		} else if (args.length == 2) {
			if (isCoolingDown(player)) {
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(mcRP.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else {
				scheduleCooldown(player);
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
				player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You have activated your Confuse ability on " + target.getName());
				target.sendMessage(mcRP.getChatName() + ChatColor.GRAY + " You were confused by " + player);
			}
		}
	}*/
}