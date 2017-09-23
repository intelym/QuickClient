package com.intelym.quick.java.client.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.intelym.quick.java.client.services.Configuration;

public class CommonUtil {

	public static String fromExchangeTime(long exchangeTime, int exchange) {
		if (exchange == Configuration.BSE || exchange == Configuration.BSEFO) {
			String date = convertLongToDateForBSE(exchangeTime);
			return date;
		} else {
			String date = convertLongToDateForNSE(exchangeTime);
			return date;
		}

	}

	// 1980 year it will take
	public static String convertLongToDateForNSE(long longDate) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar c = Calendar.getInstance(timeZone);
		c.setTimeInMillis(longDate * 1000);
		c.add(Calendar.DAY_OF_MONTH, -1);
		c.add(Calendar.YEAR, 10);
		Date date = c.getTime();
		DateFormat format = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatted = format.format(date);
		return formatted;

	}

	// 1970 year it will take
	public static String convertLongToDateForBSE(long longDate) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar c = Calendar.getInstance(timeZone);
		c.setTimeInMillis(longDate * 1000);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date date = c.getTime();
		DateFormat format = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatted = format.format(date);
		return formatted;

	}
}
