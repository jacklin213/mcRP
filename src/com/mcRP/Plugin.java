package com.mcRP;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcRP.Managers.DiseaseManager;
import com.mcRP.Managers.SkillManager;

public class Plugin extends JavaPlugin {
	public static final Logger log = Logger.getLogger("Minecraft");
	public final HashMap<Player, Integer> hm = new HashMap();
	private SkillManager skillManager;
	private DiseaseManager diseaseManager;

	public static String getChatName() {
		return ChatColor.GOLD + "[mcRP]" + ChatColor.RESET;
	}

	public void onEnable() {
		log.info(String.format(
				"[%s] Version %s By The mcRP Team is now enabled!.",
				getDescription().getName(), getDescription().getVersion(),
				getDescription().getAuthors()));
		
		this.skillManager = new SkillManager(this);
		this.diseaseManager = new DiseaseManager(this);

		this.diseaseManager.giveDisease();
		this.diseaseManager.diseaseChecks();

		getServer().getPluginManager().registerEvents(new PluginEventListener(this), this);

		createConfig();
		saveConfig();

		PluginCommandExcecutor listener = new PluginCommandExcecutor(this);
		
		getCommand("mcrp").setExecutor(listener);
		getCommand("skills").setExecutor(listener);
	    getCommand("binds").setExecutor(listener);
	}

	public void onDisable() {
		log.info(String.format("[%s] Disabled Version %s", getDescription()
				.getName(), getDescription().getVersion()));
	}
	
	public void createConfig() {
		File file = new File(getDataFolder() + File.separator + "config.yml");
	    if (!file.exists()) {
	    	log.info(Level.WARNING + "You don't have a config file!!!");
	    	log.info(Level.WARNING + "Generating config.yml.....");
	    	getConfig().options().copyDefaults(true);
	    	saveConfig();
	    }
	}
	
	public SkillManager getSkillManager() { 
		return this.skillManager; 
	}
	
}