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
import me.jacklin213.mcrp.commands.classes.CmdChoose;
import me.jacklin213.mcrp.commands.classes.CmdClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
	
	private mcRP plugin;

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
    private ArrayList<SubCommand> classCommands = new ArrayList<SubCommand>();

    public CommandManager(mcRP instance) {
    	plugin = instance;
    	this.registerClassCommands();
    	this.registerSubCommands();
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
                for (SubCommand subCmd : commands) {
                    if (subCmd.getCommand().equalsIgnoreCase(command)) {
                    	subCmd.run(sender, this.shrinkArgs(args));
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
        if (commandLabel.equalsIgnoreCase("class")) {
        	if (args.length >= 1) {
        		String command = args[0];
                for (SubCommand classCmd : classCommands) {
                    if (classCmd.getCommand().equalsIgnoreCase(command)) {
                    	classCmd.run(sender, this.shrinkArgs(args));
                        return true;
                    }
                }
                // Command not found, send help
                classCommands.get(0).run(sender, args);
                return true;
        	} else {
        		classCommands.get(0).run(sender, args);
        		return true;
        	}
        }
        if (commandLabel.equalsIgnoreCase("skillinfo")) {
        	commands.get(3).run(sender, args);
        	return true;
        }
        if (commandLabel.equalsIgnoreCase("skills")) {
        	commands.get(4).run(sender, args);
        	return true;
        }
        if (commandLabel.equalsIgnoreCase("binds")) {
        	commands.get(1).run(sender, args);
        	return true;
        }
        return false;
    }
    
    public String[] shrinkArgs(String args[]) {
        // Shift args down
        String[] newArgs = new String[args.length-1];
        for (int i = 1; i < args.length; i++) {
            newArgs[i-1] = args[i];
        }
        return newArgs;
    }
    
    private void registerSubCommands() {
    	commands.add(new CmdHelp()); //0
    	// Index of 1 starting bellow
    	// Commands with Aliases
    	commands.add(new CmdBinds()); //1
    	commands.add(new CmdClass(plugin));
    	commands.add(new CmdSkillInfo(plugin));  
        commands.add(new CmdSkills(plugin));
        // Normal subcommands
    	commands.add(new CmdInfo(plugin)); //5
    	commands.add(new CmdMotd(plugin));
        commands.add(new CmdReload(plugin));
    }
 
    private void registerClassCommands() {
    	// Class Commands
        classCommands.add(new CmdClass(plugin)); //0
        // Index of 1 starting bellow
        classCommands.add(new CmdChoose(plugin));
    }

    // Default getters
	public ArrayList<SubCommand> getCommands() {
		return commands;
	}

	public ArrayList<SubCommand> getClassCommands() {
		return classCommands;
	}
}