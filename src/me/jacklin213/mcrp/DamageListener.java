package me.jacklin213.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.*;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
	public static mcRP plugin;

	public DamageListener(mcRP mcRP) {
		
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void OnEntityDamageByEntity(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage) {
		Player d = (Player) damagee;
		if (d.getHealth() <= 5) {
			d.sendMessage(ChatColor.YELLOW + "You are now bleeding");
			d.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
			d.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
			d.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
		}

	}
	
}