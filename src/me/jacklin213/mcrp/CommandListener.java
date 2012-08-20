package me.jacklin213.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandListener implements CommandExecutor {
	mcRP plugin;

	public CommandListener(mcRP instance) {
		plugin = instance;
	}

	// define variables from main class

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("mcRP")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (!(sender.hasPermission("mcRP.reload"))) {
						return false;
					}
					if (sender instanceof Player) {
						this.plugin.CreateConfig();
						this.plugin.reloadConfig();
						sender.sendMessage(ChatColor.RED + "[mcRP]"
								+ ChatColor.GREEN + " Config reloaded!");
						this.plugin.getLogger().info(
								sender.getName() + "has reloaded the config!");
						return true;
					} else {
						this.plugin.CreateConfig();
						this.plugin.reloadConfig();
						this.plugin.getLogger().info("Config reloaded!");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("test")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.setHealth(6);
						p.sendMessage("works");
						return true;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You are not a Player!");
						return true;
					}
				}// end of test

				if (args[0].equalsIgnoreCase("test2")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.RED
								+ "You are not a Player!");
						return true;
					} else {
						Player p = (Player) sender;
						if (p.getHealth() <= 5) {
							p.addPotionEffect(new PotionEffect(
									PotionEffectType.POISON, 200, 1));
							p.sendMessage("works");
							return true;
						} else {
							p.sendMessage("You have too much hp");
							return true;
						}
					}
				}// end of test 2
				return false;

			}// end of args.length == 0

			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("set")) {
					if (args.length < 1) {
						sender.sendMessage(ChatColor.RED
								+ "Not a valid command !");
						return true;
					}
					if (args[1].equalsIgnoreCase("welcomemessage")
							|| args[1].equalsIgnoreCase("wm")) {
						if (!(sender.hasPermission("mcRP.setwelcomemessage"))) {
							return false;
						} else {
							// string builder
							String msg;

							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								if (i != 0)
									sb.append(' ');
								sb.append(args);
							}
							msg = sb.toString();

							sender.sendMessage("The message has been changed to: "
									+ msg.replaceAll("(&([a-f0-9]))",
											"\u00A7$2"));
							plugin.getConfig()
									.set("WelcomeMessage",
											msg.replaceAll("(&([a-f0-9]))",
													"\u00A7$2"));
							plugin.saveConfig();

							return true;
						}

					}

				}
				return false;

			}
			// set messsage commands

			sender.sendMessage(ChatColor.DARK_GREEN
					+ "+-----------------------------------+");
			sender.sendMessage(ChatColor.RED + "mcRP:" + ChatColor.GRAY
					+ " Lightweight version of MCMMO!");
			sender.sendMessage(ChatColor.GREEN + "By jacklin213, TickleNinja");
			sender.sendMessage(ChatColor.GOLD + "Version: "
					+ this.plugin.pdfFile.getVersion());
			sender.sendMessage(ChatColor.DARK_GREEN
					+ "+-----------------------------------+");
			return true;
		}

		return false;
	}

	// end of commandexecutor class
}
