package me.jacklin213.mcrp;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class mcRP extends JavaPlugin {
	public static mcRP plugin;
	PluginDescriptionFile pdfFile;
	public final CommandListener ce = new CommandListener(this);
	public final DamageListener dl = new DamageListener(this);
	public final PlayerListener pl = new PlayerListener(this); 
	public final Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		this.pdfFile = getDescription();
		this.getLogger().info(this.pdfFile.getName() +this.pdfFile.getVersion()
				+ " By jacklin213 & PineAbe is now enabled!.");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.dl, this);
		pm.registerEvents(this.pl, this);
		Userfilegenerator(); // Creates UserFile folder
		CreateConfig();//Creates config
		getCommand("mcrp").setExecutor(ce);

	}

	public void onDisable() {
		pdfFile = getDescription();
		this.getLogger().info(this.pdfFile.getName() + " is now disabled.");
	}

	public void Userfilegenerator(){
		File userdata = new File(getDataFolder() + File.separator + "UserData" + File.separator);
		if(!userdata.exists()){
			userdata.mkdirs();
			this.getLogger().info("Missing folder!");
			this.getLogger().info("Creating User data folder now...");
		}	
	}
	
	public void CreateConfig(){
		File file = new File(getDataFolder() + File.separator + "config.yml");
		// If config.yml doesnt exit
		if (!file.exists()) {
			// Tells console its creating a config.yml
			this.getLogger().info("You don't have a config file!!!");
			this.getLogger().info("Generating config.yml.....");
			this.getConfig().options().copyDefaults(true);
			this.saveDefaultConfig();
		}

	}
	
	


	

  // end of main class
}