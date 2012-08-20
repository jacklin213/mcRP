package net.milkycraft.Utility;

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
public class Time implements Comparable<Time> {

	/*
	 * YOUR COOLDOWN TIMES ARE DEFINED HERE MAKE AS MANY AS YOU PLEASE
	 * Caps are not required, also times dont need to end in 0
	 * This is just a nice interface for minecraft long 
	 */
	public final static Time EXONE = new Time(5.0);
	public final static Time EXTWO = new Time(10.0);
	public final static Time EXTHREE = new Time(15.0);
	public final static Time CONFIGEXAMPLE = new Time(getConfig());

	private double time;

	/**
	 * Instantiates a new time.
	 * 
	 * @param time
	 *            the time
	 */
	public Time(Double time) {
		this.time = time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Time t) {		
		return 0;
	}

	/**
	 * 
	 * @return the minecraft long
	 */
	public Long getMinecraftLong() {
		return (long) (this.time * 20);
	}

	/**
	 * Gets the real long.
	 * 
	 * @return the real long
	 */
	public Long getRealLong() {
		return (long) (this.time * 1000);
	}

	/**
	 * Gets the nano.
	 * 
	 * @return the nano
	 */
	public Long getNano() {
		return (long) (this.time * 1000000000);
	}

	/**
	 * Gets the int.
	 * 
	 * @return the int
	 */
	public Integer getInt() {
		return (int) this.time;
	}

	/**
	 * A total example for configs...
	 * 
	 * @return the config
	 */
	private static Double getConfig() {
		return 4.2;
	}
}
