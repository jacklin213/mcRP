package net.milkycraft.Utility;

import java.util.Iterator;
import net.milkycraft.Scheduler.ScheduledTask;

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

public class PlayerTimer implements Runnable, Timer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		fix();
		// Runs whenever a cooldown is over
	}

	/**
	 * Fix.
	 */
	public static void fix() {
		Iterator<ScheduledTask> itr = ptask.iterator();
		while (itr.hasNext()) {
			ScheduledTask s = itr.next();
			if (s.getEndTime() <= System.currentTimeMillis()) {
				itr.remove();
			}
		}
	}

	/**
	 * Checks if is cooling down.
	 * 
	 * @param player
	 *            the player
	 * @param time
	 *            the time
	 * @return the boolean
	 */
	public static Boolean isCoolingDown(String player, Time time) {
		Iterator<ScheduledTask> itr = ptask.iterator();
		while (itr.hasNext()) {
			ScheduledTask s = itr.next();
			if (s.getPlayerName() == player && s.getDuration() == time) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the remaining time on the cooldown / delay Always returns an
	 * integer - About 95% accurate. Negatives no longer possible as they will
	 * return 0 and possible cause of the negative will be fixed
	 * 
	 * @param name
	 *            the name
	 * @param timer
	 *            the timer
	 * @return Seconds
	 */
	public static Integer getRemainingTime(String name, Time timer) {
		int time;
		int secs = 0;
		Iterator<ScheduledTask> itr = ptask.iterator();
		while (itr.hasNext()) {
			ScheduledTask s = itr.next();
			if (s.getPlayerName() == name && s.getDuration() == timer) {
				time = (int) (timer.getMinecraftLong() - (s.getTime() - System
						.currentTimeMillis()));
				secs = timer.getInt() - (time / 1000);
			}
		}
		try {
			assert secs >= 0;
		} catch (AssertionError e) {
			fix();
			return 0;
		}
		return secs;
	}

	/**
	 * Gets the long left.
	 * 
	 * @param name
	 *            the name
	 * @param timer
	 *            the timer
	 * @return the long left
	 */
	public static Long getLongLeft(String name, Time timer) {
		long time;
		long secs = 0L;
		Iterator<ScheduledTask> itr = ptask.iterator();
		while (itr.hasNext()) {
			ScheduledTask s = itr.next();
			if (s.getPlayerName() == name && s.getDuration() == timer) {
				time = timer.getMinecraftLong()
						- (s.getTime() - System.currentTimeMillis());
				secs = timer.getInt() - (time / 1000);
			}
		}
		return secs;
	}

	/**
	 * Adds ticks to cooldown, 20 ticks = 1 second - Not a tested method.
	 * 
	 * @param id
	 *            the id
	 * @param ticks
	 *            the ticks
	 */
	public static void addToCooldown(Integer id, Long ticks) {
		Iterator<ScheduledTask> itr = ptask.iterator();
		while (itr.hasNext()) {
			ScheduledTask s = itr.next();
			if (s.getTaskId() == id) {
				s.setEndTime(s.getEndTime() + ticks);
			}
		}
	}

	/**
	 * Subtract ticks from cooldown, 20 ticks = 1 second - Not a tested method.
	 * 
	 * @param id
	 *            the id
	 * @param ticks
	 *            the ticks
	 */
	public static void substractFromCooldown(Integer id, Long ticks) {
		Iterator<ScheduledTask> itr = ptask.iterator();
		while (itr.hasNext()) {
			ScheduledTask s = itr.next();
			if (s.getTaskId() == id) {
				s.setEndTime(s.getEndTime() - ticks);
			}
		}
	}
}
