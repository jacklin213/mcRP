package me.jacklin213.mcrp.classes;

import me.jacklin213.mcrp.skills.warrior.IronWill;
import me.jacklin213.mcrp.skills.warrior.MonsterMagnet;

public class Warrior extends RPClass{

	public Warrior() {
		super("Warrior", null, ClassType.WARRIOR);
	}
	
	@Override
	protected void registerSkills() {
		this.addSkill(new IronWill());
		this.addSkill(new MonsterMagnet());
	}

}
