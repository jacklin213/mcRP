package me.jacklin213.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommandExcecutor implements CommandExecutor {
	public final mcRP plugin;

	public PluginCommandExcecutor(mcRP plugin)  {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("mcRP")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("mcRP.reload"))
						return false;
					if (sender instanceof Player) {
						this.plugin.reloadConfig();
						sender.sendMessage(mcRP.getChatName() + ChatColor.GREEN + " Config reloaded.");
						return true;
					} else {
						this.plugin.reloadConfig();
						this.plugin.log.info("Config reloaded.");
						return true;
					}
				} 
				if (args[0].equalsIgnoreCase("superspeed")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("superspeed").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills superspeed or right-click whilst holding sugar");
					return false;
				}
				if (args[0].equalsIgnoreCase("might")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("might").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills might or right-click whilst holding a blaze rod");
					return false;
				}
				if (args[0].equalsIgnoreCase("gills")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("gills").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills gills or right-click whilst holding a pumpkin");
					return false;
				}
				if (args[0].equalsIgnoreCase("superjump")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("superjump").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills superjump or right-click whilst holding leather boots");
					return false;
				}
				if (args[0].equalsIgnoreCase("martyboom")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("martyboom").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills martyboom or right-click whilst holding sulphur");
					return false;
				}
				if (args[0].equalsIgnoreCase("bless")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("bless").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills bless or /skills bless <target>");
					return false;
				}
				if (args[0].equalsIgnoreCase("confuse")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("confuse").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills confuse <target>");
					return false;
				}
				if (args[0].equalsIgnoreCase("superpunch")) {
					sender.sendMessage(ChatColor.AQUA + plugin.SM.getSkill("superpunch").getSkillDescription());
					sender.sendMessage(ChatColor.GOLD + "Use /skills superpunch <target>");
					return false;
	    		} else {
	    			sender.sendMessage(ChatColor.RED + "Not a valid command!");
    			return true;
	    		}
	    	} 
	    	if ((args.length > 1) && (args[0].equalsIgnoreCase("set"))) {
	    		if ((args[1].equalsIgnoreCase("welcomemessage")) || (args[1].equalsIgnoreCase("wm"))) {
	    			if (!sender.hasPermission("mcRP.setwelcomemessage")) {
	    				sender.sendMessage(mcRP.getChatName() + "You don't have the permission to do this");
	    				return true;
	    			} else {
	    				StringBuilder sb = new StringBuilder();
	    				for (int i = 2; i < args.length; i++) {
	    					sb.append(args[i]).append(" ");
	    				}
	    				sender.sendMessage(mcRP.getChatName() + "The welcome message has been changed to: " + ChatColor.translateAlternateColorCodes('&', sb.toString()));
	    				this.plugin.getConfig().set("WelcomeMessage.Message", ChatColor.translateAlternateColorCodes('&', sb.toString()));
	    				this.plugin.saveConfig();
	    				return true;
	    			}
	    		}
	    	}
	    	sender.sendMessage(ChatColor.DARK_GREEN + "#================[mcRP]===============#");
	    	sender.sendMessage(ChatColor.RED + "mcRP:" + ChatColor.YELLOW + " Lightweight RPG plugin/MCMMO!");
	    	sender.sendMessage(ChatColor.GREEN + "By jacklin213, PineAbe");
	    	sender.sendMessage(ChatColor.GOLD + "Version: " + plugin.getDescription().getVersion());
	    	sender.sendMessage(ChatColor.DARK_GREEN + "#====================================#");
	    	return true;
		} else if (commandLabel.equalsIgnoreCase("binds")) {
	    	if (sender instanceof Player){
	    		Player player = (Player)sender;
	        	player.sendMessage(ChatColor.GOLD + "SuperSpeed" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Sugar");
	        	player.sendMessage(ChatColor.GOLD + "Might" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Blaze Rod");
	        	player.sendMessage(ChatColor.GOLD + "Gills" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Pumpkin");
	        	player.sendMessage(ChatColor.GOLD + "SuperJump" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Leatherboots");
	        	player.sendMessage(ChatColor.GOLD + "Martyboom" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Right click Gunpowder");
	        	player.sendMessage(ChatColor.GOLD + "/skills help 1" + ChatColor.GRAY + " - " + ChatColor.WHITE + "More information on skills");
	    	} else {
    			sender.sendMessage(mcRP.getChatName() + "This command can only be used by players");
    			return true;
    		}
	    } else if (commandLabel.equalsIgnoreCase("skills")) {
	    	if (args.length == 1) {
	    		if (sender instanceof Player) {
	    			Player p = (Player)sender;
	    			if (args[0].equalsIgnoreCase("superspeed"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    			else if (args[0].equalsIgnoreCase("bless"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    			else if (args[0].equalsIgnoreCase("might"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    			else if (args[0].equalsIgnoreCase("gills"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    			else if (args[0].equalsIgnoreCase("superJump"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    			else if (args[0].equalsIgnoreCase("martyboom"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    			else if (args[0].equalsIgnoreCase("confuse"))
	    				this.plugin.getSkillManager().executeSkill(p, args[0], args);
	    		} else {
	    			sender.sendMessage(mcRP.getChatName() + "This command can only be used by players");
	    			return true;
	    		}
	    	} else if ((args.length >= 2) && (sender instanceof Player)) {
	    		Player player = (Player)sender;
	    		if (args[0].equalsIgnoreCase("help")) {
	    			try {
	    				int page = Integer.parseInt(args[1]);
	    				if (page == 1) {
	    					player.sendMessage(ChatColor.YELLOW + "------------ " + ChatColor.WHITE + "Help: mcRP Skills (Page 1)" + ChatColor.YELLOW + " ------------");
	    					player.sendMessage(ChatColor.GOLD + "/skills superspeed" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Gives you a speed boost");
	    					player.sendMessage(ChatColor.GOLD + "/skills bless" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Heals you or a friend!");
	    					player.sendMessage(ChatColor.GOLD + "/skills might" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Increases your damage temporarily");
	    					player.sendMessage(ChatColor.GOLD + "/skills gills" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Temporarily breathe underwater");
	    					player.sendMessage(ChatColor.GOLD + "/skills superjump" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Jump higher than normal");
	    					player.sendMessage(ChatColor.GOLD + "/skills martyboom" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Detonate yourself with huge impact");
	    					player.sendMessage(ChatColor.GOLD + "/skills superpunch" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Do huge damage penetrating armour");
	    					player.sendMessage(ChatColor.GOLD + "/binds" + ChatColor.GRAY + " - " + ChatColor.WHITE + "View binds for your skill");
	    				} else {
	    					player.sendMessage(ChatColor.RED + "Invalid page number specified. There is only 1 page right now.");
	    					return true;
		    			}
	    			} catch (NumberFormatException nfe) {
	    				player.sendMessage(ChatColor.RED + "Invalid page number specified. There is only 1 page right now.");
	    				return true;
	    			}
	    		} 
		
	    	} else {
	    		sender.sendMessage(mcRP.getChatName() + " This command can only be used by players");
	    		return true;
	    	}
	    	return true;
	    }
		return false;
	}
}