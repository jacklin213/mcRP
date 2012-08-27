/*
 * Copyright (c) 2012, Keeley Hoek
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

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkillManager {

    private final Plugin plugin;
    private int cooldown = 60;
    private Map<String, Integer> cooldowns = new HashMap();

    public SkillManager(Plugin plugin) {
        this.plugin = plugin;
    }

    private void scheduleCooldown(final String player) {
        cooldowns.put(player, cooldown);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {
                int time = cooldowns.get(player);

                if (time > 0) {
                    cooldowns.put(player, time - 1);
                } else {
                    cooldowns.remove(player);
                }
            }
        }, cooldown);
    }

    private boolean isCoolingDown(String player) {
        return cooldowns.get(player) != null;
    }

    private int getCooldownRemaining(String player) {
        return cooldowns.get(player);
    }
    
    public void superSpeed(Player player) {
        if (isCoolingDown(player.getName())) {
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
        } else {
            scheduleCooldown(player.getName());
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your SuperSpeed ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
        }
    }

    public void bless(Player player, String args[]) {
        if (args.length == 1) {
            if (isCoolingDown(player.getName())) {
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
            } else {
                scheduleCooldown(player.getName());
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Bless ability");
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
            }
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (!target.isOnline()) {
                player.sendMessage(Plugin.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
            } else if (isCoolingDown(player.getName())) {
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
            } else {
                scheduleCooldown(player.getName());
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Bless ability on " + target.getName());
                target.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have been healed by " + player.getName());
                target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
            }
        }
    }

    public void might(Player player) {
        if (isCoolingDown(player.getName())) {
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
        } else {
            scheduleCooldown(player.getName());
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Might ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
        }
    }

    public void gills(Player player) {
        if (isCoolingDown(player.getName())) {
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
        } else {
            scheduleCooldown(player.getName());
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Gills ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
        }

    }

    public void superJump(Player player) {
        if (isCoolingDown(player.getName())) {
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
        } else {
            scheduleCooldown(player.getName());
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your SuperJump ability");
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
        }
    }

    public void martyboom(Player player) {
        if (isCoolingDown(player.getName())) {
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
        } else {
            scheduleCooldown(player.getName());
            player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your martyboom ability");
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
            player.sendMessage(Plugin.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
        } else if (args.length == 2) {
            if (isCoolingDown(player.getName())) {
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
            } else if (!target.isOnline()) {
                player.sendMessage(Plugin.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
            } else {
                target.setHealth(target.getHealth() - 5);
                scheduleCooldown(player.getName());
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your SuperPunch ability on " + target.getName());
                if (player.getHealth() > 0) {
                    player.setHealth(0);
                }
            }
        }

    }

    public void confuse(Player player, String args[]) {
        Player target = Bukkit.getPlayerExact(args[1]);
        if (args.length == 1) {

            player.sendMessage(Plugin.getChatName() + ChatColor.RED + " You NEED to define a player to use this skill");
        } else if (args.length == 2) {
            if (isCoolingDown(player.getName())) {
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You still have a " + getCooldownRemaining(player.getName()) + " second cooldown");
            } else if (!target.isOnline()) {
                player.sendMessage(Plugin.getChatName() + ChatColor.RED + " Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
            } else {
                scheduleCooldown(player.getName());
                target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
                player.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You have activated your Confuse ability on " + target.getName());
                target.sendMessage(Plugin.getChatName() + ChatColor.GRAY + " You were confused by " + player.getName());
            }
        }
    }
}
