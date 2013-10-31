package com.mcRP;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcRP.Updater.UpdateResult;
import com.mcRP.Updater.UpdateType;
import com.mcRP.Managers.DiseaseManager;
import com.mcRP.Managers.SkillManager;

public class Plugin extends JavaPlugin {
	
	public Logger log;
	public final HashMap<Player, Integer> hm = new HashMap<Player, Integer>();
	private SkillManager skillManager = new SkillManager(this);
	private DiseaseManager diseaseManager = new DiseaseManager(this);
	private Updater updater;
	private PluginCommandExcecutor commandExecutor = new PluginCommandExcecutor(this);

	public static String getChatName() {
		return ChatColor.GOLD + "[mcRP]" + ChatColor.RESET;
	}

	public void onEnable() {
		this.setLogger();
		createConfig();

		this.diseaseManager.giveDisease();
		this.diseaseManager.diseaseChecks();

		getServer().getPluginManager().registerEvents(new PluginEventListener(this), this);

		getCommand("mcrp").setExecutor(commandExecutor);
		getCommand("skills").setExecutor(commandExecutor);
	    getCommand("binds").setExecutor(commandExecutor);
	    
	    Boolean updateCheck = Boolean.valueOf(getConfig().getBoolean("UpdateCheck"));
		Boolean autoUpdate = Boolean.valueOf(getConfig().getBoolean("AutoUpdate"));
		this.updateCheck(updateCheck, autoUpdate, 43503);
	    
	    log.info(String.format("Version %s By The mcRP Team is now enabled!.", getDescription().getVersion()));
	}

	public void onDisable() {
		log.info(String.format("Disabled Version %s", getDescription().getVersion()));
	}
	
	public void createConfig() {
		File file = new File(getDataFolder() + File.separator + "config.yml");
	    if (!file.exists()) {
	    	log.warning("You don't have a config file!!!");
	    	log.warning("Generating config.yml.....");
	    	getConfig().options().copyDefaults(true);
	    	saveConfig();
	    }
	}
	
	public SkillManager getSkillManager() { 
		return this.skillManager; 
	}
	
	private void setLogger(){
		this.log = getLogger();
	}
	
	private void updateCheck(boolean updateCheck, boolean autoUpdate, int ID){
		if(updateCheck && (autoUpdate == false)){
			updater = new Updater(this, ID, this.getFile(), UpdateType.NO_DOWNLOAD, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
			    log.info("New version available! " + updater.getLatestName());
			}
			if (updater.getResult() == UpdateResult.NO_UPDATE){
				log.info(String.format("You are running the latest version of %s", getDescription().getName()));
			}
		}
		if(autoUpdate && (updateCheck == false)){
			updater = new Updater(this, ID, this.getFile(), UpdateType.NO_VERSION_CHECK, true);
		} 
		if(autoUpdate && updateCheck){
			updater = new Updater(this, ID, this.getFile(), UpdateType.DEFAULT, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
			    log.info("New version available! " + updater.getLatestName());
			}
			if (updater.getResult() == UpdateResult.NO_UPDATE){
				log.info(String.format("You are running the latest version of %s", getDescription().getName()));
			}
		}
	}
	
}