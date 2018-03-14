package com.rohith.app.authclient.util;

public class AEHClientDateUtil {

	private static final long ONE_DAY_IN_MILLI = 3600000 * 24;

	private static final int ONE_DAY_IN_SEC = 3600 * 24;

	public static long addDays(long time, int days) {

		if (days <= 0) {
			return time;
		} else {

			return (time + (days * ONE_DAY_IN_MILLI));
		}

	}

	public static int addDays(int days) {

		if (days <= 0) {

			return ONE_DAY_IN_SEC;
		} else {

			return (days * ONE_DAY_IN_SEC);
		}
	}

}
