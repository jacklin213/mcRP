package me.jacklin213.mcrp.managers;

import java.util.ArrayList;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.commands.CmdBinds;
import me.jacklin213.mcrp.commands.CmdHelp;
import me.jacklin213.mcrp.commands.CmdInfo;
import me.jacklin213.mcrp.commands.CmdMotd;
import me.jacklin213.mcrp.commands.CmdReload;
import me.jacklin213.mcrp.commands.CmdSkillInfo;
import me.jacklin213.mcrp.commands.CmdSkills;
import me.jacklin213.mcrp.commands.SubCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
	
	private mcRP plugin;

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();

    public CommandManager(mcRP instance) {
    	plugin = instance;
    	commands.add(new CmdHelp()); //0
    	// Index of 1 starting bellow
    	commands.add(new CmdBinds()); //1
    	commands.add(new CmdInfo(plugin));
    	commands.add(new CmdMotd(plugin));
        commands.add(new CmdReload(plugin));
        commands.add(new CmdSkillInfo(plugin)); //5
        commands.add(new CmdSkills(plugin));  
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("mcrp")) {
        	if (args.length == 0) {
        		commands.get(2).run(sender, args);
        		return true;
        	}
        	if (args.length >= 1) {
                String command = args[0];
                // Shift args down
                String[] newArgs = new String[args.length-1];
                for (int i = 1; i < args.length; i++) {
                    newArgs[i-1] = args[i];
                }
                for (SubCommand subCmd : commands) {
                    if (subCmd.getCommand().equalsIgnoreCase(command)) {
                    	subCmd.run(sender, newArgs);
                        return true;
                    }
                }
                // Command not found, send help
                commands.get(0).run(sender, null);
                return true;
            } else {
                // Send help
                commands.get(0).run(sender, null);
                return true;
            }
        }
        if (commandLabel.equalsIgnoreCase("skillinfo")) {
        	commands.get(5).run(sender, args);
        	return true;
        }
        if (commandLabel.equalsIgnoreCase("skills")) {
        	commands.get(6).run(sender, args);
        	return true;
        }
        if (commandLabel.equalsIgnoreCase("binds")) {
        	commands.get(1).run(sender, args);
        	return true;
        }
        return false;
    }
}