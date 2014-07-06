package me.jacklin213.mcrp.commands;

import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.skills.Skill;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.bukkit.command.CommandSender;

public class CmdSkillInfo extends SubCommand {
	
	private static final String NAME = "mcRP SkillInfo";
	private static final String COMMAND = "skillinfo";
	private static final String USAGE = "mcrp skillinfo";
	private static final String PERMISSIONNODE = "none";
	private static final String[] HELP = { 
		GOLD + "Use: " + AQUA + "/mcrp skillinfo <skillname>" + YELLOW + " to see description of specific skill", 
		// Find time to change these to automatic V
		GOLD + "Skills:" + YELLOW + "Bless, Confuse, Gills, MartyBoom, Might, SuperJump, SuperPunch, SuperSpeed."
	};

	public CmdSkillInfo(mcRP instance) {
		super(instance, NAME, COMMAND, USAGE, HELP, PERMISSIONNODE);
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("bless")) {
				skillInfo(sender, plugin.SM.getSkill("bless"));
			}
			if (args[0].equalsIgnoreCase("confuse")) {
				skillInfo(sender, plugin.SM.getSkill("confuse"));
			}
			if (args[0].equalsIgnoreCase("gills")) {
				skillInfo(sender, plugin.SM.getSkill("gills"));
			}
			if (args[0].equalsIgnoreCase("martyboom")) {
				skillInfo(sender, plugin.SM.getSkill("martyboom"));
			}
			if (args[0].equalsIgnoreCase("might")) {
				skillInfo(sender, plugin.SM.getSkill("might"));
			}
			if (args[0].equalsIgnoreCase("superjump")) {
				skillInfo(sender, plugin.SM.getSkill("superjump"));
			}
			if (args[0].equalsIgnoreCase("superpunch")) {
				skillInfo(sender, plugin.SM.getSkill("superpunch"));
			}
			if (args[0].equalsIgnoreCase("superspeed")) {
				skillInfo(sender, plugin.SM.getSkill("superspeed"));
			}
		} else {
			sendHelp(sender);
		}
	}
	
	private void skillInfo(CommandSender sender, Skill skill) {
		sender.sendMessage(GOLD + "====[" + YELLOW + skill.getName() + GOLD + "]====" );
		sender.sendMessage(GOLD + "Description: " + AQUA + skill.getDescription());
		sender.sendMessage(GOLD + "Usage: " + YELLOW + skill.getUsage());
		sender.sendMessage(GOLD + "SkillType: " + AQUA + 
				(skill.getSkillType() == SkillType.BOTH ? skill.getSkillType().toString(): "PASSIVE, ACTIVE"));
		if (skill.hasCooldown()) {
			sender.sendMessage(GOLD + "Cooldown: " + GREEN + skill.getCooldown());
		}
		if (skill.getSkillType() == SkillType.PASSIVE || skill.getSkillType() == SkillType.BOTH) {
			sender.sendMessage(GOLD + "Duration: " + GREEN + skill.getDuration() * 20);
		}
	}

}
