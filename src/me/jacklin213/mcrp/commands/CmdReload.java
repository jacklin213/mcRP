package me.jacklin213.mcrp.commands;

import org.bukkit.command.CommandSender;

import me.jacklin213.mcrp.mcRP;

public class CmdReload extends SubCommand {

	private static final String NAME = "mcRP Reload";
	private static final String COMMAND = "reload";
	private static final String USAGE = "mcrp reload";
	private static final String PERMISSIONNODE = "mcrp.reload";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp reload" + YELLOW + " to reload mcRP config" };

	public CmdReload(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, HELP, PERMISSIONNODE);
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		plugin.reloadConfig();
		sender.sendMessage(CHAT_NAME + GREEN + "Configuration reloaded.");
	}
}
