package me.jacklin213.mcrp;

import java.util.UUID;

import me.jacklin213.mcrp.classes.ClassType;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.skills.Skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Character {
	
	private String name;
	private String uuid;
	private String rpClass;
	private ClassType classType;
	private Skill defaultBind;
	
	public Character(String name, String uuid, RPClass rpClass, Skill defaultBind) {
		this.name = name;
		this.uuid = uuid;
		this.rpClass = rpClass.getName();
		this.classType = rpClass.getClassType();
		this.defaultBind = defaultBind;
	}

	public void setDefaultBind(Skill defaultBind) {
		this.defaultBind = defaultBind;
	}
	
	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}

	public Player getPlayer() {
		Player player = Bukkit.getPlayer(UUID.fromString(uuid));
		return player;
	}
	
	public RPClass getRPClass() {
		return mcRP.getPluginInstance().RPCM.getRPClass(rpClass);
	}
	
	public String getRPClassName() {
		return this.rpClass;
	}

	public ClassType getClassType() {
		return classType;
	}

	public Skill getDefaultBind() {
		return defaultBind;
	}
	
}
