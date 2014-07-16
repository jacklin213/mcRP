package me.jacklin213.mcrp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdBinds extends SubCommand {
	
	private static final String NAME = "mcRP Binds";
	private static final String COMMAND = "binds";
	private static final String USAGE = "mcrp binds";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp binds" + YELLOW + " to show the items binds for all skills",
		GOLD + "Alias: " + AQUA + "/binds"
	};
	
	public CmdBinds() {
		super(null, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}
	
	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
        	sender.sendMessage(ChatColor.GOLD + "Martyboom" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Gunpowder");
        	sender.sendMessage(ChatColor.GOLD + "Might" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Blaze Rod");
        	sender.sendMessage(ChatColor.GOLD + "Gills" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Pumpkin");
        	sender.sendMessage(ChatColor.GOLD + "SuperJump" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Leatherboots");
        	sender.sendMessage(ChatColor.GOLD + "SuperSpeed" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Sugar");
        	sender.sendMessage(ChatColor.GOLD + "/mcrp skillinfo" + ChatColor.GRAY + " - " + ChatColor.WHITE + "More information on skills");
    	} else {
    		sendHelp(sender);
    	}
	}

}
