package me.jacklin213.mcrp;

import java.io.File;
import java.util.logging.Logger;

<<<<<<< HEAD
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
=======
>>>>>>> Updated everything
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class mcRP extends JavaPlugin {
	public static mcRP plugin;
	public static PluginDescriptionFile pdfFile;
	public final DamageListener dl = new DamageListener(this);
	public final PlayerListener pl = new PlayerListener(this); 
	public final CommandListener ce = new CommandListener(this);
	public final Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		pdfFile = getDescription();
		this.getLogger().info(pdfFile.getName() + pdfFile.getVersion()
				+ " By jacklin213 & TickleNinja is now enabled!.");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.dl, this);
<<<<<<< HEAD
=======
		pm.registerEvents(this.pl, this);
		Userfilegenerator(); // Creates UserFile folder
		CreateConfig();//Creates config
>>>>>>> Updated everything

	}

	public void onDisable() {
		pdfFile = getDescription();
		this.getLogger().info(pdfFile.getName() + " is now disabled.");
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

	

<<<<<<< HEAD
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("test")) {
			Player p = (Player) sender;
			p.setHealth(6);
			p.sendMessage("works");
			return true;
		}
		if (commandLabel.equalsIgnoreCase("test2")) {
			Player p = (Player) sender;
			if (p.getHealth() <= 5) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
			p.sendMessage("works");
			return true;	
			}else{
				p.sendMessage("You have too much hp");
				return true;
			}
		    
		}
		return false;

	} // end of main class
=======
  // end of main class
>>>>>>> Updated everything
}