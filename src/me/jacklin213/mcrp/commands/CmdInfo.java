package me.jacklin213.mcrp.commands;

import me.jacklin213.mcrp.mcRP;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdInfo extends SubCommand {
	
	private static final String NAME = "mcRP Info";
	private static final String COMMAND = "info";
	private static final String USAGE = "mcrp info";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp info" + YELLOW + " to view basic info of the plugin"
	};
	
	public CmdInfo(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}

	
	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(GOLD + "#================[" + YELLOW + "mcRP" + GOLD + "]===============#");
	    	sender.sendMessage(RED + "mcRP:" + ChatColor.YELLOW + " Lightweight RPG plugin/MCMMO!");
	    	sender.sendMessage(RED + "By: " + GREEN + "jacklin213");
	    	sender.sendMessage(RED + "Version: " + GOLD + plugin.getDescription().getVersion());
	    	sender.sendMessage(GOLD + "Command: " + AQUA + "/mcrp help - for a list of commands");
	    	sender.sendMessage(GOLD + "#====================================#");
		} else {
			sendHelp(sender);
		}
	}

}
