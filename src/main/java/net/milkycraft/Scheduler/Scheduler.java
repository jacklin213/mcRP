package net.milkycraft.Scheduler;

import net.milkycraft.Utility.GeneralTimer;
import net.milkycraft.Utility.PlayerTimer;
import net.milkycraft.Utility.Time;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

// TODO: Auto-generated Javadoc
/*	Copyright (c) 2012, Nick Porillo milkywayz@mail.com
 *
 *	Permission to use, copy, modify, and/or distribute this software for any purpose 
 *  with or without fee is hereby granted, provided that the above copyright notice 
 *  and this permission notice appear in all copies.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE 
 *	INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE 
 *  FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 *	OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, 
 *  ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

public class Scheduler {

	/**
	 * Create an instance of Schedule Reference main class for plugin Define a
	 * playername for cooldown to affect Define a Time for the cooldown *Since
	 * it deals with bukkit methods. Strickly synchronous.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param player
	 *            the player
	 * @param time
	 *            the time
	 * @return an instanceof Schedule
	 */
	public static Schedule schedule(Plugin plugin, String player, Time time) {
		return new Schedule(player, time, System.currentTimeMillis(), Bukkit
				.getScheduler().scheduleSyncDelayedTask(plugin,
						new PlayerTimer(), time.getMinecraftLong()), false);
	}

	/**
	 * Create an instance of Schedule <br>
	 * Reference main class for plugin <br>
	 * Define a time for the cooldown <br>
	 * *Note: This cooldown is global for what your using it for, it doesnt
	 * matter which player <br>
	 * *Strickly an async schedule, if you want async use:
	 * <p>
	 * scheduleGeneralCooldown(new Schedule(null, new Group(time), time,
	 * System.currentTimeMillis(), Bukkit.getScheduler()
	 * .scheduleAsyncDelayedTask(plugin, new PlayerTimer(),
	 * time.getMinecraftLong()), true);
	 * 
	 * @param plugin
	 *            the plugin
	 * @param time
	 *            the time
	 * @return an instance of Schedule
	 */
	public static Schedule schedule(Plugin plugin, Time time) {
		return new Schedule(null, time, System.currentTimeMillis(), Bukkit
				.getScheduler().scheduleSyncDelayedTask(plugin,
						new PlayerTimer(), time.getMinecraftLong()), false);
	}

	/**
	 * Schedules a new player cooldown Uses the Schedule passed along for
	 * information schedulePlayerCooldown(schedule(this, "milkywayz",
	 * Time.derp);
	 * 
	 * @param schedule
	 *            the schedule
	 */
	public static void schedulePlayerCooldown(Schedule schedule) {
		PlayerTimer.ptask.add(schedule);
	}

	/**
	 * Schedules a new general cooldown Uses the schedule passed along for
	 * information.
	 * 
	 * @param schedule
	 *            the schedule
	 */
	public static void scheduleGeneralCooldown(Schedule schedule) {
		if (schedule.getPlayerName() == null) {
			GeneralTimer.gtask.add(schedule);
		}
	}
}
