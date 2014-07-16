package me.jacklin213.mcrp.commands;

import org.bukkit.command.CommandSender;

public class CmdHelp extends SubCommand{
	
	private static final String NAME = "mcRP Help";
	private static final String COMMAND = "help";
	private static final String USAGE = "mcrp help";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = {
		YELLOW + "To see a more indepth details for commands type any of the commands bellow",
		GOLD + "Commands:",
		AQUA + "/mcrp binds",
		AQUA + "/mcrp info",
		AQUA + "/mcrp help",
		AQUA + "/mcrp motd",
		AQUA + "/mcrp skillinfo",
		AQUA + "/mcrp skills"
	};

	public CmdHelp() {
		super(null, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}
	
	@Override
	protected void execute(CommandSender sender, String[] args) {
		sendHelp(sender);
	}
}
