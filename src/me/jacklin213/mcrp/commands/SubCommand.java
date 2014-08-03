/*
 * AntiCheat for Bukkit.
 * Copyright (C) 2012-2014 AntiCheat Team | http://gravitydevelopment.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package me.jacklin213.mcrp.commands;

import me.jacklin213.mcrp.mcRP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

	public final mcRP plugin;
	public static final String CHAT_NAME = mcRP.getChatName();
	public static final ChatColor RED = ChatColor.RED;
    public static final ChatColor YELLOW = ChatColor.YELLOW;
    public static final ChatColor GREEN = ChatColor.GREEN;
    public static final ChatColor WHITE = ChatColor.WHITE;
    public static final ChatColor GRAY = ChatColor.GRAY;
    public static final ChatColor GOLD = ChatColor.GOLD;
    public static final ChatColor AQUA = ChatColor.AQUA;
    public static final Server SERVER = Bukkit.getServer();
    public static final String NO_PERMS = RED + "Insufficient Permissions.";
    public static final String NO_RPCLASS = RED + "Class doesn't exist. To see a list of avalible classes do /classes list";
    public static final String PLAYER_ONLY = RED + "This command can only be used by players.";
    public static final String NULL_CHARACTER = RED + "Error loading character. Please contact your server owner!";
    
    private final String name;
    private final String command;
    private final String usage;
    private final String[] help;
    private final String permissionNode;

    public SubCommand(mcRP instance, String name, String command, String usage, String permissionNode, String help[]) {
        this.plugin = instance;
    	this.name = name;
        this.command = command;
        this.usage = usage;
        this.permissionNode = permissionNode;
        this.help = help;
    }

    public void run(CommandSender sender, String[] args) {
        if (!permissionNode.equalsIgnoreCase("none") || sender.hasPermission(permissionNode)) {
            execute(sender, args);
        } else {
        	sender.sendMessage(NO_PERMS + " (" + WHITE + permissionNode + RED + ")");
        }
    }

    abstract protected void execute(CommandSender sender, String[] args);

    public void sendHelp(CommandSender cs) {
        cs.sendMessage(GOLD + "========[" + YELLOW + getName() + GOLD + "]========");
        cs.sendMessage(GOLD + "Usage: " + YELLOW + (cs instanceof Player ? "/" : "") + getUsage());
        cs.sendMessage(GOLD + "Permission: " + AQUA + getPermissionNode());
        for (String string : getHelp()) {
            cs.sendMessage(string);
        }
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getHelp() {
        return help;
    }

    public String getPermissionNode() {
        return permissionNode;
    }
}