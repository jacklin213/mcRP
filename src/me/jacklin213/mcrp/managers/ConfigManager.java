package me.jacklin213.mcrp.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import me.jacklin213.mcrp.mcRP;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private static mcRP plugin;
	private Logger log;
	
	public ConfigManager(mcRP instance) {
		plugin = instance;
		log = plugin.getLogger();
	}
	
	public void createConfig() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	    if (!file.exists()) {
	    	log.warning("You don't have a config file!!!");
	    	log.warning("Generating config.yml.....");
	    	plugin.saveDefaultConfig();
	    	log.info("config.yml generated!");
	    } else {
	    	plugin.getConfig().options().copyDefaults(true);
	    	plugin.saveConfig();
	    }
	}
	
	// This isnt really needed anymore
	public void updateCheckConfig() {
		// Only have this code for v1.3. DELETE RIGHT AWAY, THIS CAUSES CONFIG TO DELETE EVERY START UP
		File file = new File(plugin.getDataFolder(), "config.yml");
		if (file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if (config != null) {
				if (config.contains("Version")) {
					//Regex '.' to ' ' and remove all ' '
					int configVersion = Integer.parseInt(config.getString("Version").replace('.', ' ').replaceAll("\\s",""));
					//Gets version number only eg) "1.3.1" then regex '.' to ' ' and remove all ' '
					//Substring is (0, 5) because 5 - 0 = 5 characters, refer to javadoc
					int pluginVersion = Integer.parseInt(plugin.getDescription().getVersion().substring(0, 5).replace('.', ' ').replaceAll("\\s",""));
					//Send debug messages
					if (plugin.getDebug()) {
						log.info("[Debug][ConfigCheck] Version: " + plugin.getDescription().getVersion());
						log.info("[Debug][ConfigCheck] Plugin version: " + pluginVersion);
						log.info("[Debug][ConfigCheck] Config version: " + configVersion);
					}
					if (configVersion < pluginVersion) {
						backupConfig(file);
					}
					if (configVersion > pluginVersion) {
						log.severe("Config version is higher than plugin version");
						log.severe("Please delete your config and let it regenerate to prevent errors");
						plugin.disablePlugin();
					}
					if (configVersion == pluginVersion) {
						//log.info("Config file up to date!, Configuration loaded");
						if (config.contains("Beta")) {
							if (config.getInt("Beta") != -1) {
								int configVersionBeta = config.getInt("Beta");
								if (!plugin.getDescription().getVersion().contains("BETA")) {
									log.severe("Config version has BETA but plugin doesnt");
									log.severe("Please delete your config and let it regenerate to prevent errors");
									plugin.disablePlugin();
									return;
								}
								int pluginVersionBeta = Integer.parseInt(plugin.getDescription().getVersion().substring(11));
								if (configVersionBeta < pluginVersionBeta) {
									//backupConfig(file);
									log.info("Config update detected, Updating now......");
									this.updateConfig();
								}
								if (configVersion > pluginVersion) {
									log.severe("Config BETA is higher than plugin BETA");
									log.severe("Please delete your config and let it regenerate to prevent errors");
									plugin.disablePlugin();
								}
								if (configVersionBeta == pluginVersionBeta) {
									log.info("Config file up to date!, BETA configuration loaded");
								}
							}
						} else {
							log.severe("Unable to find path: Beta in config.yml");
							log.severe("Delete your config.yml if you cannot find 'Beta'");
							plugin.disablePlugin();
						}
					}
				} else {
					log.severe("Unable to find path: Version in config.yml");
					log.severe("Delete your config.yml if you cannot find 'Version'");
					plugin.disablePlugin();
				}
			}
		}
		// REMEMBER TO REMOVE ^
	}
	
	/**
	 * Transfers all data in outdated config into the embedded config before it is copied into the plugin directory
	 */
	// Bellow way is too complicated
	/*private void updateConfig() {
		File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration embeddedConfig = plugin.getConfig();
		FileConfiguration outdatedConfig = YamlConfiguration.loadConfiguration(configFile);
		//Map of paths and their values or more paths from the new config.
		Map<String, Object> embeddedValues = embeddedConfig.getValues(true);
		//Map of paths and their values or more paths from the old config.
		Map<String, Object> outdatedValues = outdatedConfig.getValues(true);
		
		for (String path : embeddedValues.keySet()) {
			// If get(path) is a ConfigSection continue
			if (embeddedValues.get(path) instanceof ConfigurationSection) {
				Map<String, Object> embeddedSubValues = ((ConfigurationSection) embeddedValues.get(path)).getValues(true);
				for (String pathSub : embeddedSubValues.keySet()) {
				}
			} else {
				if(outdatedValues.containsKey(path)) {
					// If values are the same start with the next for
					if (this.isSameValue(embeddedValues, outdatedValues, path)) continue;
					// This means outdatedValues has the same path but different values, so we will write it into the embedded config
					embeddedValues.put(path, outdatedValues.get(path)); //HashMaps dont allow duplicate keys so the original value is overrided
				}
				// oudatedValues does not have this path, therefore it is new
			}
		}
	}*/
	
	// This needs to be completed
	private void writeNewConfig() {
		File outputFile = new File(plugin.getDataFolder(), "newconfig.yml");
		FileConfiguration outdatedConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
		Set<String> paths = outdatedConfig.getKeys(true);
		InputStream is = plugin.getResource("config.yml");
		try {
			DataInputStream input = new DataInputStream(is);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			DataOutputStream output = new DataOutputStream(new FileOutputStream(outputFile));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#") || line.startsWith("Version") || line.startsWith("Beta")) {
					writer.write(line + "\n");
				}
			}

			reader.close();
			input.close();
			writer.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	// METHODS WORKS , JUST CANNOT COPY #COMMNETS#
	// VERY IMPORTANT TO GET EMBEDDED CONFIG U NEED TO USE plugin.getConfig().getDefaults();
	private void updateConfig() {
		File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		MemoryConfiguration embeddedConfig = new MemoryConfiguration(plugin.getConfig().getDefaults());
		MemoryConfiguration mem = new MemoryConfiguration(embeddedConfig);
		FileConfiguration outdatedConfig = YamlConfiguration.loadConfiguration(configFile); // Gets the config in the current plugin directory
		Set<String> embeddedPaths = embeddedConfig.getKeys(true);
		ArrayList<String> updatedPaths = new ArrayList<String>();
		
		/*if (debug)*/ this.printConfigKeys();
		
		for (String path : embeddedPaths) {
			if (path.equalsIgnoreCase("version") || path.equalsIgnoreCase("beta") || embeddedConfig.getConfigurationSection(path) != null) {
				continue;
			}
			if (outdatedConfig.contains(path)) {
				if (embeddedConfig.get(path).equals(outdatedConfig.get(path))) {
					continue;
				} 
				mem.addDefault(path, outdatedConfig.get(path)); // Loses comments
				embeddedConfig.addDefault(path, outdatedConfig.get(path));
			} else {
				//Outdated config doesnt have this path, it will now write
				updatedPaths.add(path);
			}
		}
		
		//this.backupConfig(configFile);
		try {
			// Adds the config defaults and saves it to config.yml
			YamlConfiguration newConfig = new YamlConfiguration();
			newConfig.addDefaults(mem.getDefaults());
			log.info("New config paths: ");
			for (String path : embeddedConfig.getDefaults().getKeys(true)) {
				log.info(path);
			}
			newConfig.options().copyDefaults(true);
			newConfig.save(new File(plugin.getDataFolder(), "testconfig.yml"));
			log.info("Paths updated: ");
			for (String path : updatedPaths) {
				log.info(path);
			}
		} catch (IOException e) {
			log.severe("Unable to save the updated config.yml");
			e.printStackTrace();
		}
	}
	
	private boolean isSameValue(Map<String, Object> embeddedValues, Map<String, Object> outdatedValues, String path) {
		Object eValue = embeddedValues.get(path);
		Object oValue = outdatedValues.get(path);
		if (eValue != null && oValue != null && eValue.equals(oValue)) {
			return true;
		}
		return false;
	}
	
	// VERY IMPORTANT TO GET EMBEDDED CONFIG U NEED TO USE plugin.getConfig().getDefaults();
	private void printConfigKeys() {
		YamlConfiguration oldConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
		Configuration newConfig = plugin.getConfig().getDefaults();
		
		log.info("New Config Version: " + newConfig.getString("Version") + " BETA " + newConfig.getString("Beta"));
		log.info("Old Config Version: " + oldConfig.getString("Version") + " BETA " + oldConfig.getString("Beta"));
		log.info("Here is a list of new config keys: ");
		for (String key : newConfig.getKeys(true)) {
			log.info(key);
		}
		log.info("Here is a list of old config keys: ");
		for (String key : oldConfig.getKeys(true)) {
			log.info(key);
		}
	}
	
	private void backupConfig(File file) {
		String date = new SimpleDateFormat("HH-mm-ss_dd-MM-yyyy").format(new Date());
		file.renameTo(new File(plugin.getBackupFolder() + File.separator +"OLD_Config_" + date + ".yml"));
		//createConfig(); commented to test out updateConfig() 
		//file.delete();
		log.info("Old configuration file moved to backups folder");
		log.info("Remember to reconfigure the new configuration before running mcRP");
	}
	
}
