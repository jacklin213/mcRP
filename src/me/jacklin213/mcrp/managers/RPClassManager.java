package me.jacklin213.mcrp.managers;

import java.util.HashMap;

import me.jacklin213.mcrp.classes.Novice;
import me.jacklin213.mcrp.classes.RPClass;
import me.jacklin213.mcrp.classes.Warrior;

public class RPClassManager {
	
	private HashMap<String, RPClass> classes = new HashMap<String, RPClass>();
	
	public RPClassManager() {
		this.registerRPClass(new Warrior());
		this.registerRPClass(new Novice());
	}
	
	private void registerRPClass(RPClass rpClass) {
		this.classes.put(rpClass.getName(), rpClass);
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
