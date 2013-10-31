package com.mcRP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PluginEventListener implements Listener {
	public final Plugin plugin;

	public PluginEventListener(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if ((this.plugin.getConfig().getBoolean("Realism.BleedMode")) 
				&& (this.plugin.getConfig().getBoolean("Realism")) 
				&& (event.getEntity().getType() == EntityType.PLAYER)) {
			Player player = (Player) event.getEntity();
			if ((player.getHealth() <= 8) && (!player.hasPermission("mcrp.realism.exempt"))) {
				player.sendMessage(Plugin.getChatName() + ChatColor.YELLOW	+ " You are" + ChatColor.RED + " bleeding!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		if (this.plugin.getConfig().getBoolean("WelcomeMessageEnabled")){
			event.getPlayer().sendMessage(Plugin.getChatName()+ " " + this.plugin.getConfig().getString("WelcomeMessage"));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if ((this.plugin.getConfig().getBoolean("Realism.TreeDestroy")) 
				&& (this.plugin.getConfig().getBoolean("Realism"))
				&& (block.getType() == Material.LOG)
				&& (event.getPlayer().hasPermission("mcrp.realism.treedestroy"))) {
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			int total = 0;
			Material type = event.getPlayer().getItemInHand().getType();
			Block top = null;
			int up = 1;
			while (up == 1) {
				top = block.getWorld().getBlockAt(x, y, z);
				if (top.getType() == Material.LOG) {
					if ((type == Material.WOOD_AXE) || 
							(type == Material.STONE_AXE) || 
							(type == Material.IRON_AXE) || 
							(type == Material.DIAMOND_AXE) || 
							(type == Material.GOLD_AXE)) {
						y++;
						top.breakNaturally();
						total++;
					}
				} else {
					event.getPlayer().getItemInHand().setDurability((short) (event.getPlayer().getItemInHand().getDurability() + total));
					up = 2;
				}
			}
		}
	}
	/*
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
			switch (e.getPlayer().getItemInHand().getTypeId()) {
			case 353:
				this.plugin.getSkillManager().superSpeed(e.getPlayer());
				break;
			case 369:
				this.plugin.getSkillManager().might(e.getPlayer());
				break;
			case 86:
				this.plugin.getSkillManager().gills(e.getPlayer());
				break;
			case 301:
				this.plugin.getSkillManager().superJump(e.getPlayer());
				break;
			case 289:
				this.plugin.getSkillManager().martyboom(e.getPlayer());
				break;
			case 373:
				if (this.plugin.hm.containsKey(e.getPlayer())) {
					this.plugin.hm.remove(e.getPlayer());
					e.getPlayer().sendMessage(ChatColor.AQUA + "You have drank water and are no longer dehydrated");
					e.getPlayer().getItemInHand().setType(Material.GLASS_BOTTLE);
				} else {
					e.setCancelled(true);
				}
				break;
			}
	}*/
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)){
			Material item = e.getPlayer().getItemInHand().getType();
			if (item == Material.SUGAR){
				this.plugin.getSkillManager().superSpeed(e.getPlayer());
			}
			if (item == Material.BLAZE_ROD){
				this.plugin.getSkillManager().might(e.getPlayer());
			}
			if (item == Material.PUMPKIN){
				this.plugin.getSkillManager().gills(e.getPlayer());	
			}
			if (item == Material.LEATHER_BOOTS){
				this.plugin.getSkillManager().superJump(e.getPlayer());
			}
			if (item == Material.SULPHUR){
				this.plugin.getSkillManager().martyboom(e.getPlayer());
			}
			if (item == Material.POTION){
				if (this.plugin.hm.containsKey(e.getPlayer())) {
					this.plugin.hm.remove(e.getPlayer());
					e.getPlayer().sendMessage(ChatColor.AQUA + "You have drank water and are no longer dehydrated");
					e.getPlayer().getItemInHand().setType(Material.GLASS_BOTTLE);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
}