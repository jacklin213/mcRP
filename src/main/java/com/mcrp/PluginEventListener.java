package com.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.*;

public class PluginEventListener implements Listener {

    public final mcRP plugin;

    public PluginEventListener(mcRP plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void OnEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player d = (Player) event.getEntity();
        if ((d.getType() == EntityType.PLAYER)) {
            if (d.getHealth() <= 5) {
                d.sendMessage(ChatColor.YELLOW + "You are" + ChatColor.RED + " bleeding");
                d.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
                d.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
                d.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(plugin.getConfig().getString("WelcomeMessage"));
    }
}