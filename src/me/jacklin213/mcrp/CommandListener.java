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
					} else if (sender instanceof Player) {
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
				} else if (args[0].equalsIgnoreCase("test")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.setHealth(6);
						p.sendMessage("works");
						return true;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You are not a Player!");
						return false;
					}
				} else // end of test
				if (args[0].equalsIgnoreCase("test2")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.RED
								+ "You are not a Player!");
						return false;
					} else {
						Player p = (Player) sender;
						if (p.getHealth() <= 5) {
							p.addPotionEffect(new PotionEffect(
									PotionEffectType.POISON, 200, 1));
							p.sendMessage("works");
							return true;
						} else {
							p.sendMessage("You have too much hp");
							return false;
						}
					}
				}// end of test 2
				return false;

			} else // end of args.length == 0
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("set")) {
					if (args.length < 1) {
						sender.sendMessage(ChatColor.RED
								+ "Not a valid command !");
						return false;
					}
					if (args[1].equalsIgnoreCase("welcomemessage")
							|| args[1].equalsIgnoreCase("wm")) {
						if (!(sender.hasPermission("mcRP.setwelcomemessage"))) {
							sender.sendMessage("You don't have the permission to do this");
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
		if (commandLabel.equalsIgnoreCase("skills")) {
			Player p = (Player) sender;
			if (args.length == 1) {
				if (sender instanceof Player) {
					if (args[0].equalsIgnoreCase("superspeed")) {
						Skills.SuperSpeed(p);
						return true;
					} else if (args[0].equalsIgnoreCase("bless")) {
						Skills.Bless(p, args);
						return true;
					} else if (args[0].equalsIgnoreCase("Might")) {
						Skills.Might(p);
						return true;
					} else if (args[0].equalsIgnoreCase("Gills")) {
						Skills.Gills(p);
						return true;
					} else if (args[0].equalsIgnoreCase("SuperJump")) {
						Skills.SuperJump(p);
						return true;
					}else if (args[0].equalsIgnoreCase("Martyboom")) {
						Skills.Martyboom(p);
						return true;
					}

				} else {
					this.plugin.getLogger().info(
							"This command can only be used by players");
				}
				// end of args.lenth ==1

			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("help")) {
					Player player = (Player) sender;
					return Skillshelp(player, args[1]);

				}
			}// end of args.length == 2
			Player player = (Player) sender;
			return Skillshelp(player, args[1]);
		}

		return false;
	}

	private boolean Skillshelp(Player player, String rawPage) {
		try {
			int page = Integer.parseInt(rawPage);

			if (page == 1) {
				player.sendMessage(ChatColor.YELLOW + " ------------ "+ ChatColor.WHITE + "Help: mcRP Skills (Page 1)"+ ChatColor.YELLOW + " ------------");
				player.sendMessage(ChatColor.GOLD + "/superspeed"+ ChatColor.GRAY + " - " + ChatColor.WHITE	+ "Gives you a speed boost !");
				player.sendMessage(ChatColor.GOLD + "/bless" + ChatColor.GRAY+ " - " + ChatColor.WHITE + "Heals you !");
			} else {
				player.sendMessage(ChatColor.RED
						+ " Invalid page number specified. Please specify a number between 1 and 2 inclusive.");
			}
		} catch (NumberFormatException nfe) {
			player.sendMessage(ChatColor.RED
					+ " Invalid page number specified. Please specify a number between 1 and 2 inclusive.");
		}
		return true;
	}
	// end of commandexecutor class
}
