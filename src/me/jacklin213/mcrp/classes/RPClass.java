package me.jacklin213.mcrp.classes;

import java.util.HashMap;

import me.jacklin213.mcrp.skills.Skill;

/**
 * Abstract class which all Classes will {@code extends} to.
 * @author jacklin213
 *
 */
public abstract class RPClass {
	
	private HashMap<String, Skill> skills = new HashMap<String, Skill>();
	
	private String name;
	private String[] aliases;
	private ClassType classType;

	//Forces subclasses to have a constructor
	/**
	 * Constructor for this class. Requires subclass name and class type.
	 * @param name The name of the RPClass subclass
	 * @param aliases An Array of Strings which will be recognised as aliases
	 * @param classType The type of the RPClass subclass
	 */
	public RPClass(String name, String[] aliases, ClassType classType) {
		this.name = name;
		this.aliases = aliases;
		this.classType = classType;
		this.registerSkills();
	}
	
	/**
	 * Developers should run {@link #addSkill(Skill)} here to register the skill
	 */
	abstract protected void registerSkills();
	
	// Skills hashmap stuff
	public void addSkill(Skill skill) {
		skills.put(skill.getName(), skill);
	}
	
	public boolean containsSkill(String name) {
		if (this.skills.containsKey(name)) {
			return true;
		}
		return false;
	}
	
	public Skill getSkill(String name) {
		if (this.skills.containsKey(name)) {
			return this.skills.get(name);
		}
		return null;
	}
	
	public HashMap<String, Skill> getSkills() {
		return this.skills;
	}
	// End Skills hashmap stuff

	public String getName() {
		return name;
	}

	public String[] getAliases() {
		return aliases;
	}

	public ClassType getClassType() {
		return classType;
	}
}