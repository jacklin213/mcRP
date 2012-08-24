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

import net.milkycraft.Scheduler.Scheduler;
import net.milkycraft.Utility.PlayerTimer;
import net.milkycraft.Utility.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PluginCommandExcecutor implements CommandExecutor {

    public final mcRP plugin;

    public PluginCommandExcecutor(mcRP plugin) {
        this.plugin = plugin;
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
                        plugin.reloadConfig();
                        sender.sendMessage(ChatColor.RED + "[mcRP]" + ChatColor.GREEN + " Config reloaded!");
                        plugin.getLogger().info(sender.getName() + "has reloaded the config!");
                        return true;
                    } else {
                        plugin.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
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
            sender.sendMessage(ChatColor.GOLD + "Version: " + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.DARK_GREEN + "+-----------------------------------+");

            return true;
        } else if (commandLabel.equalsIgnoreCase("skills")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (args[0].equalsIgnoreCase("superspeed")) {
                        superSpeed(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("bless")) {
                        bless(p, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Might")) {
                        might(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Gills")) {
                        gills(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("SuperJump")) {
                        superJump(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Martyboom")) {
                        martyboom(p);
                        return true;
                    } else if (args[0].equalsIgnoreCase("Confuse")) {
                        confuse(p, args);
                        return true;
                    }
                } else {
                    plugin.getLogger().info(
                            "This command can only be used by players");
                    return true;
                }
            } else if (args.length >= 2) {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("help")) {
                        Player player = (Player) sender;
                        return Skillshelp(player, args[1]);
                    } else {
                        plugin.getLogger().info(
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

    public void superSpeed(Player player) {
        if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
            player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
        } else {
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
            player.sendMessage(ChatColor.GRAY + "You have activated your SuperSpeed ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
        }
    }

    public void bless(Player player, String args[]) {
        if (args.length == 1) {
            if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
                player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
            } else {
                Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
                player.sendMessage(ChatColor.GRAY + "You have activated your Bless ability");
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
            }
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (!target.isOnline()) {
                player.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
            } else if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
                player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
            } else {
                Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
                player.sendMessage(ChatColor.GRAY + "You have activated your Bless ability on " + target.getName());
                target.sendMessage(ChatColor.GRAY + "You have been healed by " + player.getName());
                target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
            }
        }
    }

    public void might(Player player) {
        if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
            player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
        } else {
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
            player.sendMessage(ChatColor.GRAY + "You have activated your Might ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
        }
    }

    public void gills(Player player) {
        if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
            player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
        } else {
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
            player.sendMessage(ChatColor.GRAY + "You have activated your Gills ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
        }

    }

    public void superJump(Player player) {
        if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
            player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
        } else {
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
            player.sendMessage(ChatColor.GRAY + "You have activated your SuperJump ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
        }

    }

    public void martyboom(Player player) {
        if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
            player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
        } else {
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
            player.sendMessage(ChatColor.GRAY + "You have activated your martyboom ability");
            player.getWorld().createExplosion(player.getLocation(), 4);
            if (player.getHealth() > 0) {
                player.setHealth(0);
            }
        }
    }

    //TODO use this
    public void superPunch(Player player, String args[]) {
        Player target = Bukkit.getPlayerExact(args[1]);

        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "You NEED to define a player to use this skill");
        } else if (args.length == 2) {
            if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
                player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
            } else if (!target.isOnline()) {
                player.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
            } else {
                target.setHealth(target.getHealth() - 5);
                Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
                player.sendMessage(ChatColor.GRAY + "You have activated your SuperPunch ability on " + target.getName());
                if (player.getHealth() > 0) {
                    player.setHealth(0);
                }
            }
        }

    }

    public void confuse(Player player, String args[]) {
        Player target = Bukkit.getPlayerExact(args[1]);
        if (args.length == 1) {

            player.sendMessage(ChatColor.RED + "You NEED to define a player to use this skill");
        } else if (args.length == 2) {
            if (PlayerTimer.isCoolingDown(player.getName(), Time.EXONE)) {
                player.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(player.getName(), Time.EXONE) + " second cooldown");
            } else if (!target.isOnline()) {
                player.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
            } else {
                Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, player.getName(), Time.EXONE));
                target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
                player.sendMessage(ChatColor.GRAY + "You have activated your Confuse ability on " + target.getName());
                target.sendMessage(ChatColor.GRAY + "You were confused by " + player.getName());
            }
        }
    }
}
