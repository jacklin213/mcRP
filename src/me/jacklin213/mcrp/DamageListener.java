package me.jacklin213.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {
	public static mcRP plugin;

	public DamageListener(mcRP instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void OnEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Player d = (Player) event.getEntity();
		if((d.getType() == EntityType.PLAYER)){
		if (d.getHealth() <= 5) {
			d.sendMessage(ChatColor.YELLOW + "You are" + ChatColor.RED
					+ "bleeding");
			d.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
			d.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200,
					1));
			d.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
		}
		
		}
	}
}