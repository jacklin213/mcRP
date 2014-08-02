package me.jacklin213.mcrp.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import me.jacklin213.mcrp.Character;
import me.jacklin213.mcrp.mcRP;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.database.Database;

import org.bukkit.entity.Player;

public class CharacterManager {
	
	private static mcRP plugin;
	
	private static Database sql;
	/**
	 * Stores character instances by UUID string, and Character class
	 */
	private static HashMap<String, Character> characters = new HashMap<String, Character>();
	
	public CharacterManager(mcRP instance) {
		plugin = instance;
		sql = plugin.getDBLink().getSql();
	}
	
	public static void createCharacter(Player player, RPClass rpClass, String defaultBind) {
		new Character(player.getName(), player.getUniqueId().toString() , rpClass, plugin.SM.getSkill(defaultBind));
		sql.modifyQuery("INSERT INTO " + "Storage.Info.TablePrefix" + " (player, uuid, job, defaultBind) " +
				"VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + rpClass.getName() + "')");
		plugin.log.info("Created new Character for " + player.getName());
	}
	
	public static void loadCharacter(Player player) {
		ResultSet query = sql.readQuery("SELECT * FROM" + "Storage.Info.TablePrefix" + "players" + "WHERE uuid = '" + player.getUniqueId().toString() + "'");
		try {
			if (!query.next()) {
				createCharacter(player, plugin.RPCM.getRPClass("Novice"), null);
			} else {
				String playerName = query.getString("player");
				String uuid = query.getString("uuid");
				String job = query.getString("job");
				String defaultBind = query.getString("defaultbind");
				characters.put(uuid, new Character(playerName, uuid, plugin.RPCM.getRPClass(job), plugin.SM.getSkill(defaultBind)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveCharacter(Player player) {
		String uuid = player.getUniqueId().toString();
		if (characters.containsKey(uuid)) {
			Character character = characters.get(uuid);
			sql.modifyQuery("UPDATE " + "Storage.Info.TablePrefix" + " player  = '" + player.getName() + "' WHERE uuid = '" + uuid + "'");
			sql.modifyQuery("UPDATE " + "Storage.Info.TablePrefix" + " job  = '" + character.getRPClassName() + "' WHERE uuid = '" + uuid + "'");
			sql.modifyQuery("UPDATE " + "Storage.Info.TablePrefix" + " defaultbind  = '" + character.getDefaultBind() + "' WHERE uuid = '" + uuid + "'");
		} else {
			plugin.log.severe("Unable to save player: " + player.getName());
		}
	}
	
}
