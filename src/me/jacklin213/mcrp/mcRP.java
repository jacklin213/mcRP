package me.jacklin213.mcrp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import me.jacklin213.mcrp.managers.CommandManager;
import me.jacklin213.mcrp.managers.DiseaseManager;
import me.jacklin213.mcrp.managers.SkillManager;
import me.jacklin213.mcrp.utils.MetricsLite;
import me.jacklin213.mcrp.utils.Updater;
import me.jacklin213.mcrp.utils.Updater.UpdateResult;
import me.jacklin213.mcrp.utils.Updater.UpdateType;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class mcRP extends JavaPlugin {
	
	public Logger log;
	public ArrayList<String> diseasePlayerList = new ArrayList<String>();
	public SkillManager SM = new SkillManager(this);
	private DiseaseManager diseaseManager = new DiseaseManager(this);
	private CommandManager commandManager = new CommandManager(this);
	private Updater updater;
	private File backupFolder;
	//private PluginCommandExcecutor commandExecutor = new PluginCommandExcecutor(this); DEPRECATED

	public static String getChatName() {
		return ChatColor.GOLD + "[" + ChatColor.YELLOW + "mcRP" + ChatColor.GOLD + "] " + ChatColor.RESET;
	}

	public void onEnable() {
		this.setLogger();
		this.setBackupFolder();
		
		createConfig();

		this.diseaseManager.giveDisease();
		this.diseaseManager.diseaseChecks();

		getServer().getPluginManager().registerEvents(new mcRPListener(this), this);

		getCommand("mcrp").setExecutor(commandManager);
		getCommand("skills").setExecutor(commandManager);
		getCommand("skillinfo").setExecutor(commandManager);
	    getCommand("binds").setExecutor(commandManager);
	    
	    Boolean useMetrics = Boolean.valueOf(getConfig().getBoolean("Metrics"));
	    Boolean updateCheck = Boolean.valueOf(getConfig().getBoolean("UpdateCheck"));
		Boolean autoUpdate = Boolean.valueOf(getConfig().getBoolean("AutoUpdate"));
		this.updateCheck(updateCheck, autoUpdate, 43503);
		this.updateCheckConfig();
		this.startMetrics(useMetrics);
		
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
	
	private void setBackupFolder() {
		backupFolder = new File(getDataFolder() + File.separator + "backups");
		if (!backupFolder.exists()) {
			backupFolder.mkdirs();
		}
	}
	
	private void updateCheckConfig() {
		// Only have this code for v1.3. DELETE RIGHT AWAY, THIS CAUSES CONFIG TO DELETE EVERY START UP
		File file = new File(getDataFolder(), "config.yml");
		if (file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if (config != null) {
				if (config.contains("Version")) {
					int configVersion = Integer.parseInt(config.getString("Version").replace('.', ' ').replaceAll("\\s",""));
					int pluginVersion = Integer.parseInt(getDescription().getVersion().replace('.', ' ').replaceAll("\\s",""));
					if (configVersion < pluginVersion) {
						String date = new SimpleDateFormat("HH-mm-ss_dd-MM-yyyy").format(new Date());
						file.renameTo(new File(backupFolder + File.separator +"OLD_Config_" + date + ".yml"));
						createConfig();
						//file.delete();
						log.info("Old configuration file moved to backups folder");
						log.info("Remember to reconfigure the new configuration before running mcRP");
					}
					if (configVersion > pluginVersion) {
						log.severe("Error: Config version is higher than plugin version");
						log.severe("Please delete your config and let it regenerate to prevent errors");
					}
					if (configVersion == pluginVersion) {
						log.info("Config file up to date!, Configuration loaded");
					}
				} else {
					log.warning("Unable to find path: Version in config.yml");
					log.warning("Delete your config.yml if you cannot find 'Version'");
				}
			}
		}
		// REMEMBER TO REMOVE ^
	}
	
	private void startMetrics(boolean useMetrics) {
		if (!useMetrics) {
			return;
		}
		try {
			//Metrics metrics = new Metrics(this);
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e) {
			log.severe("Unable to start Metrics. Printing error bellow");
			e.printStackTrace();
		    // Failed to submit the stats :-(
		}
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