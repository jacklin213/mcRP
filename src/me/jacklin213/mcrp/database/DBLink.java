package me.jacklin213.mcrp.database;

import me.jacklin213.mcrp.mcRP;

public class DBLink {
	
	private static mcRP plugin = mcRP.getPluginInstance();

	private Database sql;

	private String host;
	private int port;
	private String db;
	private String user;
	private String pass;
	
	public DBLink(String host, int port, String db, String user, String pass) {
		this.host = host;
		this.port = port;
		this.db = db;
		this.user = user;
		this.pass = pass;
	}
	
	public void load() {
		if (plugin.getConfig().getString("Storage.Type").equalsIgnoreCase("mysql")) {
			sql = new MySQL(plugin.log, "Establishing MySQL Connection...", host, port, user, pass, db);
			((MySQL) sql).open();
			plugin.log.info("Database connection established.");

			if (!sql.tableExists(plugin.getConfig().getString("Storage.Info.TablePrefix" + "players"))) {
				plugin.log.info("Creating Storage.Info.TablePrefix" + "players");
				String query = "CREATE TABLE `" + "Storage.Info.TablePrefix" + "players" + "` ("
						+ "`id` int(32) NOT NULL AUTO_INCREMENT,"
						+ "`player` varchar(255),"
						+ "`uuid` varchar(255),"
						+ "`job` varchar(255),"
						+ "'defaultbind' varchar(255)"
						+ " PRIMARY KEY (id));";
				sql.modifyQuery(query);
			}
		} else {
			sql = new SQLite(plugin.log, "Establishing SQLite Connection.", "mcrp.db", plugin.getDataFolder().getAbsolutePath());
			((SQLite) sql).open();

			if (!sql.tableExists("Storage.Info.TablePrefix" + "players")) {
				plugin.log.info("Creating Storage.Info.TablePrefix" + "players");
				String query = "CREATE TABLE `" + "Storage.Info.TablePrefix" + "players" + "` ("
						+ "`id` INTEGER PRIMARY KEY,"
						+ "`player` TEXT(255),"
						+ "`uuid` TEXT(255),"
						+ "`job` TEXT(255),"
						+ "'defaultbind' TEXT(255);";
				sql.modifyQuery(query);
			}
		}
	}

	public Database getSql() {
		return sql;
	}

}