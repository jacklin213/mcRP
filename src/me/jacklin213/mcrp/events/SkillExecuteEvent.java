package me.jacklin213.mcrp.events;

import me.jacklin213.mcrp.skills.Skill;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillExecuteEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Block block;
	private Location location;
	private Player player;
	private Skill skill;
	private int duration; //In ticks
	private boolean cancelled;

	/**
	 * Called when a skill is executed.
	 * @param player The player that executed the skill
	 * @param skill The skill that is executed
	 */
	public SkillExecuteEvent(Player player, Skill skill) {
		this.player = player;
		this.location = player.getLocation();
		this.block = location.getBlock();
		this.skill = skill;
		this.duration = skill.getDuration();
	}
	
	/**
	 * Gets the duration of the skill in ticks.
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Changes the duration of the skill to a desired amount.
	 * @param duration The desired amount in ticks
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Gets the player that executed the skill.
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the skill that was executed.
	 * @return skill
	 */
	public Skill getSkill() {
		return skill;
	}
	
	/**
	 * Gets block that player is standing on.
	 * @return block
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * Gets location that player in at.
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
