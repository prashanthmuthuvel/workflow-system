package com.workflow.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String parseTimestamp(Date date) {
		return DATE_TIME_FORMAT.format(date);
	}
	
	public static long differenceBetweenTwoDates(Date startDate, Date endDate) {
		return (endDate.getTime() - startDate.getTime()) / 1000;
	}

}
