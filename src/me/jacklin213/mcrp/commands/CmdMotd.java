package me.jacklin213.mcrp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.jacklin213.mcrp.mcRP;

public class CmdMotd extends SubCommand {
	
	private static final String NAME = "mcRP Motd";
	private static final String COMMAND = "motd";
	private static final String USAGE = "mcrp motd";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp motd" + YELLOW + " to view the message of the day", 
		GOLD + "Use: " + AQUA + "/mcrp motd set <message>" + YELLOW + " to set the message of the day",
		GOLD + "The Motd will be stored in your config if you want to change it mannually"
	};

	public CmdMotd(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}
	
	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(CHAT_NAME + YELLOW + "Motd: " + WHITE + plugin.getConfig().getString("Motd.Message"));
		}
		if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
			if (sender.hasPermission("mcrp.setmotd")) {
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				sender.sendMessage(mcRP.getChatName() + YELLOW + "The message of the day has been changed to: " + WHITE + ChatColor.translateAlternateColorCodes('&', sb.toString()));
				plugin.getConfig().set("Motd.Message", ChatColor.translateAlternateColorCodes('&', sb.toString()));
				plugin.saveConfig();
			} else {
				sender.sendMessage(NO_PERMS);
			}
		} else {
			sendHelp(sender);
		}
	}

}
