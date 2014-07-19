package me.jacklin213.mcrp.managers;

import java.util.ArrayList;
import java.util.HashMap;

import me.jacklin213.mcrp.mcRP;
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
	//private HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	public SkillManager(mcRP instance) {
		plugin = instance;
		this.addSkills();
	}

	private void addSkills() {
		//Note: These skill names have to be in lower case
		skills.put("bless", new Bless(plugin));
		skills.put("confuse", new Confuse(plugin));
		skills.put("gills", new Gills(plugin));
		skills.put("martyboom", new MartyBoom(plugin));
		skills.put("might", new Might(plugin));
		skills.put("stealth", new Stealth(plugin));
		skills.put("superjump", new SuperJump(plugin));
		skills.put("superpunch", new SuperPunch(plugin));
		skills.put("superspeed", new SuperSpeed(plugin));
	}
	
	public Skill getSkill(String skillName) {
		if (skills.containsKey(skillName)) {
			return skills.get(skillName);
		}
		return null;
	}
	
	public void executeSkill(Player player, String skillName, String args[]) {
		if (skills.containsKey(skillName)) {
			skills.get(skillName).exceute(player, args);
		}
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