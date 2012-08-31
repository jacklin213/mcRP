/*
 * Copyright (c) 2012, The mcRP Team
 * All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.mcrp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommandExcecutor implements CommandExecutor {

    public final Plugin plugin;

    public PluginCommandExcecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (alias.equalsIgnoreCase("mcRP")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!(sender.hasPermission("mcRP.reload"))) {
                        return false;
                    } else if (sender instanceof Player) {
                        plugin.reloadConfig();

                        sender.sendMessage(Plugin.getChatName() + ChatColor.GREEN + " Config reloaded.");

                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("superspeed")) {
                    sender.sendMessage(ChatColor.AQUA + "SuperSpeed gives you a temporary speed boost");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills superspeed or right-click whilst holding sugar");

                    return false;
                } else if (args[0].equalsIgnoreCase("might")) {
                    sender.sendMessage(ChatColor.AQUA + "Might will give you a temporary damage boost");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills might or right-click whilst holding a blaze rod");

                    return false;
                } else if (args[0].equalsIgnoreCase("gills")) {
                    sender.sendMessage(ChatColor.AQUA + "Gills allows you to breath under water for a while");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills gills or right-click whilst holding a pumpkin");

                    return false;
                } else if (args[0].equalsIgnoreCase("superjump")) {
                    sender.sendMessage(ChatColor.AQUA + "SuperJump allows you a temporarily jump higher");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills superjump or right-click whilst holding leather boots");

                    return false;
                } else if (args[0].equalsIgnoreCase("martyboom")) {
                    sender.sendMessage(ChatColor.AQUA + "Martyboom explode you and nearby targets");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills martyboom or right-click whilst holding sulphur");

                    return false;
                } else if (args[0].equalsIgnoreCase("bless")) {
                    sender.sendMessage(ChatColor.AQUA + "Bless puts you back at full HP");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills bless or /skills bless <target>");

                    return false;
                } else if (args[0].equalsIgnoreCase("confuse")) {
                    sender.sendMessage(ChatColor.AQUA + "Confuse allows you to confuse a target");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills confuse <target>");

                    return false;
                } else if (args[0].equalsIgnoreCase("superpunch")) {
                    sender.sendMessage(ChatColor.AQUA + "SuperPunch a target through his armour");
                    sender.sendMessage(ChatColor.GOLD + "Use /skills superpunch <target>");

                    return false;
                }

                return false;
            } else if (args.length > 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length < 1) {
                        sender.sendMessage(ChatColor.RED + "Not a valid command!");

                        return true;
                    } else {
                        if (args[1].equalsIgnoreCase("welcomemessage") || args[1].equalsIgnoreCase("wm")) {
                            if (!(sender.hasPermission("mcRP.setwelcomemessage"))) {
                                sender.sendMessage(Plugin.getChatName() + " You don't have the permission to do this");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < args.length; i++) {
                                    if (i != 0) {
                                        sb.append(' ');
                                    }
                                    sb.append(args);
                                }

                                sender.sendMessage(Plugin.getChatName() + " The message has been changed to: " + sb.toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
                                plugin.getConfig().set("WelcomeMessage", sb.toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
                                plugin.saveConfig();
                            }
                        }
                    }
                }
            }

            sender.sendMessage(ChatColor.DARK_GREEN + "+-----------------------------------+");
            sender.sendMessage(ChatColor.RED + "mcRP:" + ChatColor.GRAY + " Lightweight version of MCMMO!");
            sender.sendMessage(ChatColor.GREEN + "By jacklin213, TickleNinja");
            sender.sendMessage(ChatColor.GOLD + "Version: " + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.DARK_GREEN + "+-----------------------------------+");
        } else if (alias.equalsIgnoreCase("skills")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (args[0].equalsIgnoreCase("superspeed")) {
                        plugin.getSkillManager().superSpeed(p);

                        return true;
                    } else if (args[0].equalsIgnoreCase("bless")) {
                        plugin.getSkillManager().bless(p, args);

                        return true;
                    } else if (args[0].equalsIgnoreCase("might")) {
                        plugin.getSkillManager().might(p);

                        return true;
                    } else if (args[0].equalsIgnoreCase("gills")) {
                        plugin.getSkillManager().gills(p);

                        return true;
                    } else if (args[0].equalsIgnoreCase("superJump")) {
                        plugin.getSkillManager().superJump(p);

                        return true;
                    } else if (args[0].equalsIgnoreCase("martyboom")) {
                        plugin.getSkillManager().martyboom(p);

                        return true;
                    } else if (args[0].equalsIgnoreCase("confuse")) {
                        plugin.getSkillManager().confuse(p, args);

                        return true;
                    }
                } else {
                    sender.sendMessage(Plugin.getChatName() + " This command can only be used by players");
                    return true;
                }
            } else if (args.length >= 2) {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("help")) {
                        Player player = (Player) sender;

                        try {
                            int page = Integer.parseInt(args[0]);

                            if (page == 1) {
                                player.sendMessage(ChatColor.YELLOW + " ------------ " + ChatColor.WHITE + "Help: mcRP Skills (Page 1)" + ChatColor.YELLOW + " ------------");
                                player.sendMessage(ChatColor.GOLD + "/skills superspeed" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Gives you a speed boost");
                                player.sendMessage(ChatColor.GOLD + "/skills bless" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Heals you or a friend!");
                                player.sendMessage(ChatColor.GOLD + "/skills might" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Increases your damage temporarily");
                                player.sendMessage(ChatColor.GOLD + "/skills gills" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Temporarily breathe underwater");
                                player.sendMessage(ChatColor.GOLD + "/skills superjump" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Jump higher than normal");
                                player.sendMessage(ChatColor.GOLD + "/skills martyboom" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Detonate yourself with huge impact");
                                player.sendMessage(ChatColor.GOLD + "/skills superpunch" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Do huge damage penetrating armour");
                            } else {
                                player.sendMessage(ChatColor.RED + " Invalid page number specified. There is only 1 page right now.");
                            }
                        } catch (NumberFormatException nfe) {
                            player.sendMessage(ChatColor.RED + " Invalid page number specified. There is only 1 page right now.");
                        }
                    } else {
                        sender.sendMessage(Plugin.getChatName() + " This command can only be used by players");
                    }
                }
            }

            sender.sendMessage("Not enough arguments. Type /skills help 1 for help");

            return true;
        }

        return false;
    }
}
