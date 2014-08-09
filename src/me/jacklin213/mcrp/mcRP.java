package me.jacklin213.mcrp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.jacklin213.mcrp.database.DBLink;
import me.jacklin213.mcrp.managers.CharacterManager;
import me.jacklin213.mcrp.managers.CommandManager;
import me.jacklin213.mcrp.managers.ConfigManager;
import me.jacklin213.mcrp.managers.DiseaseManager;
import me.jacklin213.mcrp.managers.RPClassManager;
import me.jacklin213.mcrp.managers.SkillManager;
import me.jacklin213.mcrp.utils.MetricsLite;
import me.jacklin213.mcrp.utils.Updater;
import me.jacklin213.mcrp.utils.Updater.UpdateResult;
import me.jacklin213.mcrp.utils.Updater.UpdateType;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class mcRP extends JavaPlugin {
	
	private static mcRP plugin;
	
	public Logger log;
	public ArrayList<String> diseasePlayerList = new ArrayList<String>();
	
	public ConfigManager configManager;
	public CommandManager commandManager = new CommandManager(this);
	public SkillManager SM = new SkillManager(this);
	public RPClassManager RPCM = new RPClassManager();
	private DiseaseManager diseaseManager = new DiseaseManager(this);
	
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
	
	public void onEnable() {
		enabled = true;
		this.setLogger();
		this.setBackupFolder();
		plugin = this;
		
		configManager = new ConfigManager(this);
		configManager.createConfig();

		debug = getConfig().getBoolean("Debug");
		Boolean useMetrics = Boolean.valueOf(getConfig().getBoolean("Metrics"));
	    Boolean updateCheck = Boolean.valueOf(getConfig().getBoolean("UpdateCheck"));
		Boolean autoUpdate = Boolean.valueOf(getConfig().getBoolean("AutoUpdate"));
		this.updateCheck(updateCheck, autoUpdate, 43503);
		//configManager.updateCheckConfig();

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
			this.loadDatabase();
			
			log.info(String.format("Version %s By The mcRP Team is now enabled!.", getDescription().getVersion()));
		}
	}
	
	public void onDisable() {
		CharacterManager.saveCharacterAll();
		dbLink.getSql().close();
		log.info(String.format("Disabled Version %s", getDescription().getVersion()));
	}
	
	public SkillManager getSkillManager() { 
		return this.SM; 
	}
	
	public DBLink getDBLink() {
		return this.dbLink;
	}
	
	public boolean getDebug() {
		return debug;
	}
	
	public File getBackupFolder() {
		return this.backupFolder;
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
	
	public void disablePlugin() {
		enabled = false;
		getServer().getPluginManager().disablePlugin(this);
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