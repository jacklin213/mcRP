package com.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandListener implements CommandExecutor {

    public final mcRP plugin;

    public CommandListener(mcRP instance) {
        plugin = instance;
    }

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
                        sender.sendMessage(ChatColor.RED + "[mcRP]" + ChatColor.GREEN + " Config reloaded!");
                        this.plugin.getLogger().info(sender.getName() + "has reloaded the config!");
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
                        sender.sendMessage(ChatColor.RED + "You are not a Player!");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("test2")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "You are not a Player!");
                        return true;
                    } else {
                        Player p = (Player) sender;
                        if (p.getHealth() <= 5) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));

                            p.sendMessage("works");
                            return true;
                        } else {
                            p.sendMessage("You have too much hp");
                            return true;
                        }
                    }
                }

                return false;
            } else if (args.length > 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length < 1) {
                        sender.sendMessage(ChatColor.RED
                                + "Not a valid command !");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("welcomemessage")
                            || args[1].equalsIgnoreCase("wm")) {
                        if (!(sender.hasPermission("mcRP.setwelcomemessage"))) {
                            sender.sendMessage("You don't have the permission to do this");
                            return false;
                        } else {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                if (i != 0) {
                                    sb.append(' ');
                                }
                                sb.append(args);
                            }

                            sender.sendMessage("The message has been changed to: " + sb.toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
                            plugin.getConfig().set("WelcomeMessage", sb.toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
                            plugin.saveConfig();

                            return true;
                        }

                    }

                }

                return false;
            }

            sender.sendMessage(ChatColor.DARK_GREEN + "+-----------------------------------+");
            sender.sendMessage(ChatColor.RED + "mcRP:" + ChatColor.GRAY + " Lightweight version of MCMMO!");
            sender.sendMessage(ChatColor.GREEN + "By jacklin213, TickleNinja");
            sender.sendMessage(ChatColor.GOLD + "Version: " + this.plugin.pdfFile.getVersion());
            sender.sendMessage(ChatColor.DARK_GREEN + "+-----------------------------------+");
            return true;
        } else if (commandLabel.equalsIgnoreCase("skills")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (args[0].equalsIgnoreCase("superspeed")) {
                        Skills.superSpeed(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("bless")) {
                        Skills.bless(p, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Might")) {
                        Skills.might(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Gills")) {
                        Skills.gills(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("SuperJump")) {
                        Skills.superJump(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Martyboom")) {
                        Skills.martyboom(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Confuse")) {
                        Skills.confuse(p, args);
                        return true;
                    }
                } else {
                    this.plugin.getLogger().info(
                            "This command can only be used by players");
                    return true;
                }
            } else if (args.length >= 2) {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("help")) {
                        Player player = (Player) sender;
                        return Skillshelp(player, args[1]);
                    } else {
                        this.plugin.getLogger().info(
                                "This command can only be used by players");
                        return true;
                    }

                }
            }

            sender.sendMessage("Not enough arguments. Type /skills help 1 for help");

            return true;
        }

        return false;
    }

    private boolean Skillshelp(Player player, String rawPage) {
        try {
            int page = Integer.parseInt(rawPage);

            if (page == 1) {
                player.sendMessage(ChatColor.YELLOW + " ------------ " + ChatColor.WHITE + "Help: mcRP Skills (Page 1)" + ChatColor.YELLOW + " ------------");
                send(player, "/skills superspeed", "Gives you a speed boost !");
                send(player, "/skills bless", "Heals you or a friend!");
                send(player, "/skills might", "Increases your damage temporarily !");
                send(player, "/skills gills", "Temporarily breathe underwater !");
                send(player, "/skills superjump", "Jump higher than normal !");
                send(player, "/skills martyboom", "Detonate yourself with huge impact !");
                send(player, "/skills superpunch", "Do huge damage penetrating armour !");
            } else {
                player.sendMessage(ChatColor.RED + " Invalid page number specified. There is only 1 page right now.");
            }
        } catch (NumberFormatException nfe) {
            player.sendMessage(ChatColor.RED + " Invalid page number specified. There is only 1 page right now.");
        }
        return true;
    }

    private void send(Player player, String command, String description) {
        player.sendMessage(ChatColor.GOLD + command + ChatColor.GRAY + " - " + ChatColor.WHITE + description);
    }
}
