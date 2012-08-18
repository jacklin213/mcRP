package me.jacklin213.mcrp;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class mcRP extends JavaPlugin {
	public static mcRP plugin;
	PluginDescriptionFile pdfFile;
	public final DamageListener dl = new DamageListener(this);
	public final Logger log = Logger.getLogger("Minecraft");
	public static Permission Permissions = null;

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
		return false;
	} // end of main class
}