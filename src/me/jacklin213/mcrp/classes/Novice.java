package me.jacklin213.mcrp.classes;

public class Novice extends RPClass {
	
	private static final String[] ALIASES = {"Noob", "Beginner"};
	
	public Novice() {
		super("Novice", ALIASES , ClassType.NOVICE);
	}

	@Override
	protected void registerSkills() {
		// TODO Auto-generated method stub
		
	}

}
