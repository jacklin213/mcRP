package me.jacklin213.mcrp.commands.classes;

import me.jacklin213.mcrp.Character;
import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.classes.ClassType;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.commands.SubCommand;
import me.jacklin213.mcrp.managers.CharacterManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdChoose extends SubCommand {

	private static final String NAME = "Class Choose";
	private static final String COMMAND = "choose";
	private static final String USAGE = "Class choose";
	private static final String PERMISSIONNODE = "mcrp.class.choose";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp class choose <class>" + YELLOW + " To choose a class",
		GOLD + "Alias: " + AQUA + "/class choose <class>"
	};
	
	public CmdChoose(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length >= 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(CHAT_NAME + PLAYER_ONLY);
			}
			String className = args[0];
			Character character = CharacterManager.getCharacter(((Player) sender).getUniqueId().toString());
			RPClass rpClass = plugin.RPCM.getRPClass(className);
			if (character == null) sender.sendMessage(NULL_CHARACTER); plugin.log.warning("Error on loading Character for command /class choose");
			if (rpClass == null) sender.sendMessage(NO_RPCLASS);
			if (character.getRPClass().getClassType() == ClassType.NOVICE) {
				character.setRPClass(className);
				sender.sendMessage(YELLOW + "You have choosen the class" + GREEN + rpClass.getName());
			}
		} else {
			sendHelp(sender);
		}
	}
}
