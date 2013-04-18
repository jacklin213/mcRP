/*
 * Copyright (c) 2012, The mcRP Team , LinCraft
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
package main.java.com.mcrp;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    public static String getChatName() {
        return ChatColor.GOLD + "[mcRP]" + ChatColor.RESET;
    }
    private SkillManager skillManager;
    
    @Override
    public void onEnable() {
        log.log(Level.INFO, getDescription().getName() + getDescription().getVersion() + " By The mcRP Team is now enabled!.");

        skillManager = new SkillManager(this);
        
        getServer().getPluginManager().registerEvents(new PluginEventListener(this), this);

        createConfig();
        saveConfig();

        PluginCommandExcecutor listener = new PluginCommandExcecutor(this);
        
        getCommand("mcrp").setExecutor(listener);
        getCommand("skills").setExecutor(listener);
    }

    @Override
    public void onDisable() {
        log.log(Level.INFO, getDescription().getName() + " is now disabled.");
    }
    public void createConfig() {
        File file = new File(getDataFolder() + File.separator + "config.yml");
  	  if (!file.exists()) {
  	   this.getLogger().info(Level.WARNING + "You don't have a config file!!!");
  	   this.getLogger().info(Level.WARNING + "Generating config.yml.....");
  	   getConfig().options().copyDefaults(true);
  	   this.saveConfig();
  	  }
  }
    public SkillManager getSkillManager() {
        return skillManager;
    }
}
