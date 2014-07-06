package me.jacklin213.mcrp.managers;

import java.util.Random;

import me.jacklin213.mcrp.mcRP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class DiseaseManager {
	
	private final mcRP plugin;

	public DiseaseManager(mcRP instance) {
		plugin = instance;
	}

	public void giveDisease() {
		if (plugin.getConfig().getBoolean("Realism.Enabled") && plugin.getConfig().getBoolean("Realism.Diseases.Enabled") && plugin.getConfig().getBoolean("Realism.Diseases.Dehydration.Enabled")){
			final int infectTime = plugin.getConfig().getInt("Realism.Diseases.Dehydration.InfectTime");
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					for (Player player : Bukkit.getOnlinePlayers()) {
						int max = new Random().nextInt(100);
						int min = plugin.getConfig().getInt("Realism.Diseases.Dehydration.InfectChance");
						if (max <= min) {
							if (!player.hasPermission("mcrp.diseases.dehydration.exempt")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, infectTime, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, infectTime, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, infectTime, 1));
								player.sendMessage(mcRP.getChatName() + ChatColor.GRAY+ "You have become dehydrated");
								plugin.hm.put(player.getUniqueId().toString(), Integer.valueOf(1));
							}
						}
					}
					//Old inefficient way of doing this
					/*int random = new Random().nextInt(5);
					List<Player> onlinePlayers = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
					Iterator<Player> iterator = onlinePlayers.iterator();
					while (iterator.hasNext()) {
						int random2 = new Random().nextInt(5);
						if (random == random2) {
							Player player = (Player) iterator.next();
							if (plugin.getConfig().getBoolean("Realism.Enabled") && plugin.getConfig().getBoolean("Realism.Diseases.Enabled") 
									&& plugin.getConfig().getBoolean("Realism.Diseases.Dehydration.Enabled") && !player.hasPermission("mcrp.diseases.dehydration.exempt")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
								player.sendMessage(Plugin.getChatName() + ChatColor.GRAY+ "You have become dehydrated");
								plugin.hm.put(player.getUniqueId().toString(), Integer.valueOf(1));
							}
						}
					}*/
				}
			}, 12000L, infectTime * 20);
		}
	}

	public void diseaseChecks() {
		if (plugin.getConfig().getBoolean("Realism.Enabled") && plugin.getConfig().getBoolean("Realism.Diseases.Enabled") && plugin.getConfig().getBoolean("Realism.Diseases.Dehydration.Enabled")) {
			final int infectTime = plugin.getConfig().getInt("Realism.Diseases.Dehydration.InfectTime");
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (plugin.hm.containsKey(player.getUniqueId().toString())) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, infectTime, 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, infectTime, 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, infectTime, 1));
							player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + "You become naucious from dehydration");
							player.sendMessage(mcRP.getChatName() + ChatColor.GRAY + "Go drink from a water bottle");
						}
					}
					//Old ineffecient way of doing this
					/*List<Player> onlinePlayers = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
					Iterator<Player> iterator = onlinePlayers.iterator();
					while (iterator.hasNext()) {
						Player onlinePlayer = (Player) iterator.next();
						if (DiseaseManager.this.plugin.hm.containsKey(onlinePlayer.getUniqueId().toString())) {
							onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
							onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
							onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
							onlinePlayer.sendMessage(Plugin.getChatName() + ChatColor.GRAY + "You become naucious from dehydration");
							onlinePlayer.sendMessage(Plugin.getChatName() + ChatColor.GRAY + "Go drink from a water bottle");
						}
					}*/
				}
			}, 12000L, 10 * (60 * 20));
		}
	}
}