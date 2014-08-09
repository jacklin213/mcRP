package me.jacklin213.mcrp.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import me.jacklin213.mcrp.Character;
import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.database.Database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CharacterManager {
	
	private static mcRP plugin = mcRP.getPluginInstance();
	
	private static Database sql = plugin.getDBLink().getSql();
	private static String table = plugin.getConfig().getString("Storage.Info.TablePrefix") + "players";
	
	/**
	 * Stores character instances by UUID string, and Character class
	 */
	private static HashMap<String, Character> characters = new HashMap<String, Character>();
	
	public static void createCharacter(Player player, RPClass rpClass, String defaultBind) {
		String uuid = player.getUniqueId().toString();
		characters.put(uuid, new Character(player.getName(), uuid, rpClass, plugin.SM.getSkill(defaultBind)));
		sql.modifyQuery("INSERT INTO " + table + " (player, uuid, job) " + "VALUES ('" + player.getName() + "', '" + player.getUniqueId().toString() + "', '" + rpClass.getName() + "')");
		plugin.log.info("Created new Character for " + player.getName());
	}
	
	public static Character getCharacter(String uuid) {
		if (characters.containsKey(uuid)) {
			return characters.get(uuid);
		} 
		return null;
	}
	
	public static void loadCharacter(Player player) {
		ResultSet query = sql.readQuery("SELECT * FROM " + table + " WHERE uuid = '" + player.getUniqueId().toString() + "'");
		try {
			if (!query.next()) {
				createCharacter(player, plugin.RPCM.getRPClass("Novice"), null);
			} else {
				String playerName = query.getString("player");
				String uuid = query.getString("uuid");
				String job = query.getString("job");
				String defaultBind = query.getString("defaultbind");
				characters.put(uuid, new Character(playerName, uuid, plugin.RPCM.getRPClass(job), plugin.SM.getSkill(defaultBind)));
				plugin.log.info("Loaded Character for " + player.getName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveCharacter(String uuid) {
		Player player = Bukkit.getPlayer(UUID.fromString(uuid));
		String playerName;
		if (player != null) playerName = player.getName();
		else 
			playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
		
		if (characters.containsKey(uuid)) {
			Character character = characters.get(uuid);
			if (character == null) plugin.log.severe("NULL character while saving player: " + playerName);
			sql.modifyQuery("UPDATE " + table + " SET player  = '" + character.getName() + "' WHERE uuid = '" + uuid + "'");
			sql.modifyQuery("UPDATE " + table + " SET job  = '" + character.getRPClassName() + "' WHERE uuid = '" + uuid + "'");
			sql.modifyQuery("UPDATE " + table + " SET defaultbind  = '" + character.getDefaultBind() + "' WHERE uuid = '" + uuid + "'");
			characters.remove(uuid);
			plugin.log.info("Saved Character for " + playerName);
		} else {
			plugin.log.severe("Unable to save player: " + playerName);
		}
	}
	
	public static void saveCharacter(Player player) {
		saveCharacter(player.getUniqueId().toString());
	}
	
	public static void saveCharacterAll() {
		for (String uuid : characters.keySet()) {
			saveCharacter(uuid);
		}
		plugin.log.info("All characters have been saved!");
	}
	
}
