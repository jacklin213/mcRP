package com.mcRP.Managers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mcRP.Plugin;

public class DiseaseManager {
	private final Plugin plugin;

	public DiseaseManager(Plugin plugin) {
		this.plugin = plugin;
	}

	public void giveDisease() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin,
				new Runnable() {
					public void run() {
						int random = new Random().nextInt(5);
						List<Player> onlinePlayers = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
						Iterator<Player> iterator = onlinePlayers.iterator();
						while (iterator.hasNext()) {
							int random2 = new Random().nextInt(5);
							if (random == random2) {
								Player onlinePlayer = (Player) iterator.next();
								if ((DiseaseManager.this.plugin.getConfig().getBoolean("Realism.Diseases"))
										&& (DiseaseManager.this.plugin.getConfig().getBoolean("Realism"))
										&& (!onlinePlayer.hasPermission("mcrp.realism.exempt"))) {
									onlinePlayer.sendMessage(ChatColor.GRAY+ "You become dehydrated");
									onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
									onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
									onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
									onlinePlayer.sendMessage(Plugin.getChatName() + ChatColor.GRAY + "You are dehydrated");
									DiseaseManager.this.plugin.hm.put(onlinePlayer, Integer.valueOf(1));
								}
							}
						}
					}
				}, 12000L, 12000L);
	}

	public void diseaseChecks() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin,
				new Runnable() {
					public void run() {
						List<Player> onlinePlayers = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
						Iterator<Player> iterator = onlinePlayers.iterator();
						while (iterator.hasNext()) {
							Player onlinePlayer = (Player) iterator.next();
							if (DiseaseManager.this.plugin.hm.containsKey(onlinePlayer)) {
								onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
								onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
								onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
								onlinePlayer.sendMessage(Plugin.getChatName() + ChatColor.GRAY + "You become naucious from dehydration");
								onlinePlayer.sendMessage(Plugin.getChatName() + ChatColor.GRAY + "Go drink from a water bottle");
							}
						}
					}
				}, 14200L, 12000L);
	}
}