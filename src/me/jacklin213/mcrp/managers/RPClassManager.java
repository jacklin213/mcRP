package me.jacklin213.mcrp.managers;

import java.util.HashMap;

import me.jacklin213.mcrp.classes.Bowman;
import me.jacklin213.mcrp.classes.Mage;
import me.jacklin213.mcrp.classes.Novice;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.classes.Warrior;

public class RPClassManager {
	
	private HashMap<String, RPClass> classes = new HashMap<String, RPClass>();
	
	public RPClassManager() {
		this.registerRPClass(new Novice());
		this.registerRPClass(new Bowman());
		this.registerRPClass(new Mage());
		this.registerRPClass(new Warrior());
	}
	
	private void registerRPClass(RPClass rpClass) {
		this.classes.put(rpClass.getName(), rpClass);
		for (String classAlias : rpClass.getAliases()) {
			if (classAlias != null) this.classes.put(classAlias, rpClass);
		}
	}
	
	public HashMap<String, RPClass> getRPClasses() {
		return this.classes;
	}
	
	public RPClass getRPClass(String name) {
		if (classes.containsKey(name)) {
			return classes.get(name);
		} else {
			return null;
		}
	}
}
