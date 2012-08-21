package me.jacklin213.mcrp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener{
	public static mcRP plugin;
	public PlayerListener(mcRP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent pje) {
	Player player = pje.getPlayer();
	
	player.sendMessage(plugin.getConfig().getString("WelcomeMessage"));
	}

}
