package com.mcRP.Managers;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mcRP.Plugin;

public class SkillManager {
	private final Plugin plugin;
	private HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	public SkillManager(Plugin plugin) {
		this.plugin = plugin;
	}

	private void scheduleCooldown(final Player player) {
		int cooldown = this.plugin.getConfig().getInt("Skills.Cooldown");
		final String playerName = player.getName();
		this.cooldowns.put(playerName, System.currentTimeMillis());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				SkillManager.this.cooldowns.remove(playerName);
			}
		}, cooldown*20);
	}
	
	private Integer getSecondsLeft(final Player player){
		int cooldown = this.plugin.getConfig().getInt("Skills.Cooldown");
		String playerName = player.getName();
		if (this.cooldowns.containsKey(playerName)){
			long oldTime = this.cooldowns.get(playerName);
			long curTime = System.currentTimeMillis();
			int secondsLeft = (int)((cooldown - (curTime - oldTime)/1000));
			return secondsLeft;
		} else {
			return 0;
		}
	}

	private boolean isCoolingDown(Player player) {
		return this.cooldowns.get(player.getName()) != null;
	}
	
	public void superSpeed(Player player) {
		if (isCoolingDown(player)) {
 	     player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your SuperSpeed ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
		}
	}

	public void bless(Player player, String[] args) {
		if (args.length == 1) {
			if (isCoolingDown(player)) {
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else {
				scheduleCooldown(player);
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Bless ability");
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
			}
		} else if (args.length == 2) {
			Player target = Bukkit.getPlayerExact(args[1]);
			if (!target.isOnline()) {
				player.sendMessage(Plugin.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else if (isCoolingDown(player)) {
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else {
				scheduleCooldown(player);
        	player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Bless ability on " + target.getName());
        	target.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have been healed by " + player);
        	target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
			}
		}
	}

	public void might(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Might ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
		}
	}

	public void gills(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Gills ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
		}
	}

	public void superJump(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your SuperJump ability");
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
		}
	}

	public void martyboom(Player player) {
		if (isCoolingDown(player)) {
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
		} else {
			scheduleCooldown(player);
			player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your martyboom ability");
			player.getWorld().createExplosion(player.getLocation(), 4.0F);
			if (player.getHealth() > 0)
				player.setHealth(0);
		}
  	}

	public void superPunch(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);

		if (args.length == 1)
			player.sendMessage(Plugin.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
		else if (args.length == 2)
			if (isCoolingDown(player)) {
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(Plugin.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else {
				target.setHealth(target.getHealth() - 5);
				scheduleCooldown(player);
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your SuperPunch ability on " + target.getName());
				if (player.getHealth() > 0)
					player.setHealth(0);
			}
	}

	public void confuse(Player player, String[] args) {
		Player target = Bukkit.getPlayerExact(args[1]);
		if (args.length == 1)
		{
			player.sendMessage(Plugin.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
		} else if (args.length == 2)
			if (isCoolingDown(player)) {
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + this.getSecondsLeft(player) + " second cooldown");
			} else if (!target.isOnline()) {
				player.sendMessage(Plugin.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			} else {
				scheduleCooldown(player);
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
				player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Confuse ability on " + target.getName());
				target.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You were confused by " + player);
			}
	}
}