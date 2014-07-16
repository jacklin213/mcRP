package me.jacklin213.mcrp;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.jacklin213.mcrp.Updater.UpdateResult;
import me.jacklin213.mcrp.Updater.UpdateType;
import me.jacklin213.mcrp.managers.CommandManager;
import me.jacklin213.mcrp.managers.DiseaseManager;
import me.jacklin213.mcrp.managers.SkillManager;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class mcRP extends JavaPlugin {
	
	public Logger log;
	public ArrayList<String> diseasePlayerList = new ArrayList<String>();
	public SkillManager SM = new SkillManager(this);
	private DiseaseManager diseaseManager = new DiseaseManager(this);
	private CommandManager commandManager = new CommandManager(this);
	private Updater updater;
	//private PluginCommandExcecutor commandExecutor = new PluginCommandExcecutor(this); DEPRECATED

	public static String getChatName() {
		return ChatColor.GOLD + "[" + ChatColor.YELLOW + "mcRP" + ChatColor.GOLD + "] " + ChatColor.RESET;
	}

	public void onEnable() {
		this.setLogger();
		// Only have this code for v1.3
		File file = new File(getDataFolder(), "config.yml");
		if (file.exists()) {
			file.delete();
			log.info("Old configuration file deleted, Remember to reconfigure the new configuration before running mcRP");
		}
		// REMEMBER TO REMOVE ^
		createConfig();

		this.diseaseManager.giveDisease();
		this.diseaseManager.diseaseChecks();

		getServer().getPluginManager().registerEvents(new mcRPListener(this), this);

		getCommand("mcrp").setExecutor(commandManager);
		getCommand("skills").setExecutor(commandManager);
		getCommand("skillinfo").setExecutor(commandManager);
	    getCommand("binds").setExecutor(commandManager);
	    
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
	    	saveDefaultConfig();
	    	log.info("config.yml generated!");
	    }
	}
	
	public SkillManager getSkillManager() { 
		return this.SM; 
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