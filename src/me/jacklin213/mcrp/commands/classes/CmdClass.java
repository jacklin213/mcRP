package me.jacklin213.mcrp.commands.classes;

import org.bukkit.command.CommandSender;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.commands.SubCommand;

public class CmdClass extends SubCommand {
	
	private static final String NAME = "mcRP Class";
	private static final String COMMAND = "class";
	private static final String USAGE = "mcrp class";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = { 
		YELLOW + "Bellow is a list of commands bellonging to the class part of mcRP",
		GOLD + "Alias: " + AQUA + "/class" + YELLOW + " If you dont have any other class plugins.",
		GOLD + "Commands:",
		AQUA + "/mcrp class" + YELLOW + " To display this page. ",
		AQUA + "/mcrp class choose <class>" + YELLOW  + " To you to choose a class",
		AQUA + "/mcrp class list" + YELLOW + " Lists the currently avalible classes",
	};
	
	public CmdClass(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sendHelp(sender);
		} else {
			String[] newArgs = plugin.commandManager.shrinkArgs(args);
			if (args[0].equalsIgnoreCase("choose")) {
				plugin.commandManager.getClassCommands().get(1).run(sender, newArgs);
			}
			if (args[0].equalsIgnoreCase("list")) {
				
			}
		}
	}
}
