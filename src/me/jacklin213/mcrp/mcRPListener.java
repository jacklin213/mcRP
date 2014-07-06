package me.jacklin213.mcrp;

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

public class mcRPListener implements Listener {
	
	private final mcRP plugin;

	public mcRPListener(mcRP instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (plugin.getConfig().getBoolean("Realism") && plugin.getConfig().getBoolean("Realism.BleedMode") && event.getEntity().getType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			if ((player.getHealth() <= 8) && !player.hasPermission("mcrp.realism.exempt")) {
				player.sendMessage(mcRP.getChatName() + ChatColor.YELLOW + " You are" + ChatColor.RED + " bleeding!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		if (this.plugin.getConfig().getBoolean("WelcomeMessage.Enabled")){
			event.setJoinMessage(mcRP.getChatName() + this.plugin.getConfig().getString("WelcomeMessage.Message"));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if ((this.plugin.getConfig().getBoolean("Realism.Enabled")) && (this.plugin.getConfig().getBoolean("Realism.TreeDestroy")) 
				&& (block.getType() == Material.LOG) && (event.getPlayer().hasPermission("mcrp.realism.treedestroy"))) {
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
					if ((type == Material.WOOD_AXE) || (type == Material.STONE_AXE) || (type == Material.IRON_AXE) || (type == Material.DIAMOND_AXE) || (type == Material.GOLD_AXE)) {
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
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)){
			Material item = event.getPlayer().getItemInHand().getType();
			if (item == Material.SUGAR){
				this.plugin.getSkillManager().executeSkill(event.getPlayer(), "superspeed", null);
			}
			if (item == Material.BLAZE_ROD){
				this.plugin.getSkillManager().executeSkill(event.getPlayer(), "might", null);
			}
			if (item == Material.PUMPKIN){
				this.plugin.getSkillManager().executeSkill(event.getPlayer(), "gills", null);
			}
			if (item == Material.LEATHER_BOOTS){
				this.plugin.getSkillManager().executeSkill(event.getPlayer(), "superjump", null);
			}
			if (item == Material.SULPHUR){
				this.plugin.getSkillManager().executeSkill(event.getPlayer(), "martyboom", null);
			}
			if (item == Material.POTION){
				if (this.plugin.hm.containsKey(event.getPlayer())) {
					this.plugin.hm.remove(event.getPlayer());
					event.getPlayer().sendMessage(ChatColor.AQUA + "You have drank water and are no longer dehydrated");
					event.getPlayer().getItemInHand().setType(Material.GLASS_BOTTLE);
				} else {
					event.setCancelled(true);
				}
			}
		}
	}
}