package com.mcrp;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class mcRP extends JavaPlugin {

    public static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        log.log(Level.INFO, getDescription().getName() + getDescription().getVersion() + " By jacklin213 & PineAbe is now enabled!.");
        
        getServer().getPluginManager().registerEvents(new PluginEventListener(this), this);
        
        getConfig().options().copyDefaults(true);
        saveConfig();

        PluginCommandExcecutor commandListener = new PluginCommandExcecutor(this);

        getCommand("mcrp").setExecutor(commandListener);
        getCommand("skills").setExecutor(commandListener);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(getDescription().getName() + " is now disabled.");
    }
}