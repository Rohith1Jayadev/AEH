package com.rohith.aeh.hub.util.date;

public class AEHHubDateUtil {

	private static final long ONE_MIN_IN_MILLIS = 60000;

	private static final long ONE_HOUR_IN_MILLIS = 3600000;

	private static final long ONE_DAY_IN_MILLIS = 3600000 * 24;

	/**
	 * API for adding minutes to a long time
	 * 
	 * @param time
	 * @param numMinutes
	 * @return
	 */
	public static long addMinutes(long time, int numMinutes) {

		if (numMinutes <= 0) {

			return time;
		}
		return (time + (numMinutes * ONE_MIN_IN_MILLIS));
	}

	/**
	 * API for adding hours to a long time
	 * 
	 * @param time
	 * @param numMinutes
	 * @return
	 */
	public static long addHours(long time, int hours) {

		if (hours <= 0) {

			return time;
		}
		return (time + (hours * ONE_HOUR_IN_MILLIS));
	}

	public static long addDays(long time, int days) {

		if (days <= 0) {

			return time;
		}

		return (time + (days * ONE_DAY_IN_MILLIS));
	}

}
