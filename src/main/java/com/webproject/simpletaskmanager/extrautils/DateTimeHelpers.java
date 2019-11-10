package com.webproject.simpletaskmanager.extrautils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeHelpers {
	
	public static Long getDateDiff(Date from, Date to, TimeUnit convertTo) {
		long diff = to.getTime() - from.getTime();
		return convertTo.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public static String getTime(long milliseconds) {
		long seconds = milliseconds / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		return String.format("%02d days, %02d hours, %02d minutes, %02d seconds", 
				days % 24, hours % 60, minutes % 60, seconds % 60);
	}
}
