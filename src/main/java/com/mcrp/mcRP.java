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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class mcRP extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    public static String getChatName() {
        return ChatColor.GOLD + "[mcRP]" + ChatColor.RESET;
    }
    private int cooldown = 60;
    private Map<String, Integer> cooldowns = new HashMap();

    @Override
    public void onEnable() {
        log.log(Level.INFO, getDescription().getName() + getDescription().getVersion() + " By: The mcRP Team is now enabled!.");

        getServer().getPluginManager().registerEvents(new PluginEventListener(this), this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        PluginCommandExcecutor commandListener = new PluginCommandExcecutor(this);

        getCommand("mcrp").setExecutor(commandListener);
        getCommand("skills").setExecutor(commandListener);
    }

    @Override
    public void onDisable() {
        log.log(Level.INFO, getDescription().getName() + " is now disabled.");
    }

    public void scheduleCooldown(final String player) {
        cooldowns.put(player, cooldown);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
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

    public boolean isCoolingDown(String player) {
        return cooldowns.get(player) != null;
    }

    public int getCooldownRemaining(String player) {
        return cooldowns.get(player);
    }
}