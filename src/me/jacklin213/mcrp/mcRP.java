package me.jacklin213.mcrp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import me.jacklin213.mcrp.database.DBLink;
import me.jacklin213.mcrp.managers.CommandManager;
import me.jacklin213.mcrp.managers.DiseaseManager;
import me.jacklin213.mcrp.managers.RPClassManager;
import me.jacklin213.mcrp.managers.SkillManager;
import me.jacklin213.mcrp.utils.MetricsLite;
import me.jacklin213.mcrp.utils.Updater;
import me.jacklin213.mcrp.utils.Updater.UpdateResult;
import me.jacklin213.mcrp.utils.Updater.UpdateType;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class mcRP extends JavaPlugin {
	
	private static mcRP plugin;
	
	public Logger log;
	public ArrayList<String> diseasePlayerList = new ArrayList<String>();
	public SkillManager SM = new SkillManager(this);
	public RPClassManager RPCM = new RPClassManager();
	private DiseaseManager diseaseManager = new DiseaseManager(this);
	private CommandManager commandManager = new CommandManager(this);
	private DBLink dbLink;
	private Updater updater;
	private File backupFolder;
	private boolean enabled = false;
	private boolean debug = false;
	//private PluginCommandExcecutor commandExecutor = new PluginCommandExcecutor(this); DEPRECATED

	/**
	 * Gets an instance of the mcRP class.
	 * @return mcRP instance
	 */
	public static mcRP getPluginInstance() {
		return plugin;
	}
	
	public static String getChatName() {
		return ChatColor.GOLD + "[" + ChatColor.YELLOW + "mcRP" + ChatColor.GOLD + "] " + ChatColor.RESET;
	}
	
	public DBLink getDBLink() {
		return this.dbLink;
	}

	public void onEnable() {
		enabled = true;
		this.setLogger();
		this.setBackupFolder();
		
		createConfig();
		
		debug = getConfig().getBoolean("Debug");
		Boolean useMetrics = Boolean.valueOf(getConfig().getBoolean("Metrics"));
	    Boolean updateCheck = Boolean.valueOf(getConfig().getBoolean("UpdateCheck"));
		Boolean autoUpdate = Boolean.valueOf(getConfig().getBoolean("AutoUpdate"));
		this.updateCheck(updateCheck, autoUpdate, 43503);
		this.updateCheckConfig();

		// Checks to see if anything has disabled plugin before tyring to enable it
		if (enabled) {
			this.diseaseManager.giveDisease();
			this.diseaseManager.diseaseChecks();

			getServer().getPluginManager().registerEvents(new mcRPListener(this), this);

			getCommand("mcrp").setExecutor(commandManager);
			getCommand("skills").setExecutor(commandManager);
			getCommand("skillinfo").setExecutor(commandManager);
		    getCommand("binds").setExecutor(commandManager);
		    
			this.startMetrics(useMetrics);
			
			log.info(String.format("Version %s By The mcRP Team is now enabled!.", getDescription().getVersion()));
		}
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
					//Regex '.' to ' ' and remove all ' '
					int configVersion = Integer.parseInt(config.getString("Version").replace('.', ' ').replaceAll("\\s",""));
					//Gets version number only eg) "1.3.1" then regex '.' to ' ' and remove all ' '
					//Substring is (0, 5) because 5 - 0 = 5 characters, refer to javadoc
					int pluginVersion = Integer.parseInt(getDescription().getVersion().substring(0, 5).replace('.', ' ').replaceAll("\\s",""));
					//Send debug messages
					if (debug) {
						log.info("[Debug][ConfigCheck] Version: " + getDescription().getVersion());
						log.info("[Debug][ConfigCheck] Plugin version: " + pluginVersion);
						log.info("[Debug][ConfigCheck] Config version: " + configVersion);
					}
					if (configVersion < pluginVersion) {
						backupConfig(file);
					}
					if (configVersion > pluginVersion) {
						log.severe("Config version is higher than plugin version");
						log.severe("Please delete your config and let it regenerate to prevent errors");
						disablePlugin();
					}
					if (configVersion == pluginVersion) {
						//log.info("Config file up to date!, Configuration loaded");
						if (config.contains("Beta")) {
							if (config.getInt("Beta") != -1) {
								int configVersionBeta = config.getInt("Beta");
								if (!getDescription().getVersion().contains("BETA")) {
									log.severe("Config version has BETA but plugin doesnt");
									log.severe("Please delete your config and let it regenerate to prevent errors");
									disablePlugin();
									return;
								}
								int pluginVersionBeta = Integer.parseInt(getDescription().getVersion().substring(11));
								if (configVersionBeta < pluginVersionBeta) {
									backupConfig(file);
								}
								if (configVersion > pluginVersion) {
									log.severe("Config BETA is higher than plugin BETA");
									log.severe("Please delete your config and let it regenerate to prevent errors");
									disablePlugin();
								}
								if (configVersionBeta == pluginVersionBeta) {
									log.info("Config file up to date!, BETA configuration loaded");
								}
							}
						} else {
							log.severe("Unable to find path: Beta in config.yml");
							log.severe("Delete your config.yml if you cannot find 'Beta'");
							disablePlugin();
						}
					}
				} else {
					log.severe("Unable to find path: Version in config.yml");
					log.severe("Delete your config.yml if you cannot find 'Version'");
					disablePlugin();
				}
			}
		}
		// REMEMBER TO REMOVE ^
	}
	
	private void disablePlugin() {
		getServer().getPluginManager().disablePlugin(this);
		enabled = false;
	}
	
	private void backupConfig(File file) {
		String date = new SimpleDateFormat("HH-mm-ss_dd-MM-yyyy").format(new Date());
		file.renameTo(new File(backupFolder + File.separator +"OLD_Config_" + date + ".yml"));
		createConfig();
		//file.delete();
		log.info("Old configuration file moved to backups folder");
		log.info("Remember to reconfigure the new configuration before running mcRP");
	}
	
	private void loadDatabase() {
		dbLink = new DBLink(
				getConfig().getString("Storage.Info.Host"), 
				getConfig().getInt("Storage.Info.Port"), 
				getConfig().getString("Storage.Info.Db"),
				getConfig().getString("Storage.Info.User"), 
				getConfig().getString("Storage.Info.Pass")
		);
		dbLink.load();
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