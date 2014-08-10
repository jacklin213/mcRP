package me.jacklin213.mcrp.skills;

import java.util.Map;

import me.jacklin213.mcrp.classes.ClassType;
import me.jacklin213.mcrp.skills.Skill.SkillType;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Maps;

public enum SkillEnum {
	// <--Legacy Skills-->
	BLESS(SkillType.BOTH),
	CONFUSE(SkillType.BOTH),
	GILS(SkillType.ACTIVE),
	MARTYBOOM(SkillType.ACTIVE),
	MIGHT(SkillType.PASSIVE),
	STEALTH(SkillType.PASSIVE),
	SUPERJUMP(SkillType.PASSIVE),
	SUPERPUNCH(SkillType.ACTIVE),
	SUPERSPEED(SkillType.PASSIVE),
	// <--Class Skills -->
	//Warrior
	IRONWILL(SkillType.PASSIVE, ClassType.WARRIOR),
	MOBMAGNET(SkillType.ACTIVE, ClassType.WARRIOR),
	POWERSLASH(SkillType.ACTIVE, ClassType.WARRIOR),
	//Bowman
	EXPLOSIVEARROW(SkillType.ACTIVE, ClassType.BOWMAN),
	KNOCKBACK(SkillType.ACTIVE, ClassType.BOWMAN),
	SWIFTWIND(SkillType.PASSIVE, ClassType.BOWMAN)
	;
	
	private final static Map<String, SkillEnum> NAMES = Maps.newHashMap();
	private final ClassType classType;
	private final SkillType skillType;
	
	//Constructors
	private SkillEnum(final SkillType skillType) {
		this(skillType, ClassType.LEGACY);
	}
	
	private SkillEnum(final SkillType skillType, final ClassType classType) {
		this.classType = classType;
		this.skillType = skillType;
	}
	
	/**
     * Attempts to get the SkillEnum with the given name.
     * <p>
     * This is a normal lookup, names must be the precise name they are given
     * in the enum.
     *
     * @param name Name of the SkillEnum to get
     * @return SkillEnum if found, or null
     */
    public static SkillEnum getSkillEnum(final String name) {
        return NAMES.get(name);
    }
	
    /**
     * Attempts to match the SkillEnum with the given name.
     * <p>
     * This is a match lookup; names will be converted to uppercase, then
     * stripped of special characters in an attempt to format it like the
     * enum.
     *      *
     * @param name Name of the SkillEnum to get
     * @return SkillEnum if found, or null
     */
    public static SkillEnum matchSkillEnum(final String name) {
        Validate.notNull(name, "Name cannot be null");

        SkillEnum result = null;

        try {
            result = getSkillEnum(name);
        } catch (NumberFormatException ex) {}

        if (result == null) {
            String filtered = name.toUpperCase();

            filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
            result = NAMES.get(filtered);
        }

        return result;
    }
    
    public ClassType getClassType() {
		return classType;
	}

	public SkillType getSkillType() {
		return skillType;
	}
    
    static {
        for (SkillEnum skillEnum : values()) {
        	NAMES.put(skillEnum.name(), skillEnum);
        }
    }
}
