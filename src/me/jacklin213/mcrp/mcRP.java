package me.jacklin213.mcrp;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class mcRP extends JavaPlugin {
	public static mcRP plugin;
	PluginDescriptionFile pdfFile;
	public final DamageListener dl = new DamageListener(this);
	public final Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		this.pdfFile = getDescription();
		this.log.info(this.pdfFile.getName() + this.pdfFile.getVersion()
				+ " By jacklin213 & TickleNinja is now enabled!.");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.dl, this);

	}

	public void onDisable() {
		this.pdfFile = getDescription();
		this.log.info(this.pdfFile.getName() + " is now disabled.");
	}

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
}