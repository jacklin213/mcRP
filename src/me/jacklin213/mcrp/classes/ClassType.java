package me.jacklin213.mcrp.classes;

/**
 * ClassType Enum is used to identify which {@link RPClass class} a character is in.
 * @author jacklin213
 *
 */
public enum ClassType {
	BOWMAN, MAGICIAN, PIRATE, THEIF, WARRIOR, NOVICE, LEGACY;
	
	/**
	 * Gets a matching ClassType with the defined string
	 * @param string Name of ClassType
	 * @return
	 */
	public static ClassType getType(String string) {
		for (ClassType classType: ClassType.values()) {
			if (classType.toString().equalsIgnoreCase(string)) {
				return classType;
			}
		}
		return null;
	}
}
