package me.jacklin213.mcrp.classes;

import me.jacklin213.mcrp.skills.bowman.Knockback;
import me.jacklin213.mcrp.skills.bowman.SwiftWind;

public class Bowman extends RPClass {
	
	private static final String[] ALIASES = {"Archer"};
	
	public Bowman() {
		super("Bowman", ALIASES, ClassType.BOWMAN);
	}

	@Override
	protected void registerSkills() {
		//this.addSkill(new ExplosiveArrow());
		this.addSkill(new Knockback());
		this.addSkill(new SwiftWind());
	}

}
