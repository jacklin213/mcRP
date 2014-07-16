package me.jacklin213.mcrp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jacklin213.mcrp.mcRP;

public class CmdSkills extends SubCommand {
	
	private static final String NAME = "mcRP Skills";
	private static final String COMMAND = "skills";
	private static final String USAGE = "mcrp skills";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp skills <skillname>" + YELLOW + " to activate/use the skill that is requested", 
		// Find time to change these to automatic V
		GOLD + "Alias: " + AQUA + "/skills <skillname>",
		GOLD + "Skills: " + YELLOW + "Bless, Confuse, Gills, MartyBoom, Might, SuperJump, SuperPunch, SuperSpeed."
	};
	
	public CmdSkills(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, PERMISSIONNODE, HELP);
	}
	
	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length > 0) {
			if (sender instanceof Player) {
    			Player p = (Player)sender;
    			for (String skillName : plugin.SM.getSkills().keySet()) {
    				if (args[0].equalsIgnoreCase(skillName)) {
    					/*String[] newArgs = new String[args.length-1];
    	                for (int i = 1; i < args.length; i++) {
    	                    newArgs[i-1] = args[i];
    	                }*/
    					plugin.SM.executeSkill(p, args[0]/*AKA skillName*/, args);
    				}
    			}
    		} else {
    			sender.sendMessage(CHAT_NAME + PLAYER_ONLY);
    		}
		} else {
			sendHelp(sender);
		}
	}

}
