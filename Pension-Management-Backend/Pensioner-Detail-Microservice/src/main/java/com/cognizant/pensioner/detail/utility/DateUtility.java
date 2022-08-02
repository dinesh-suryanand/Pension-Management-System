package com.cognizant.pensioner.detail.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtility {
	
	private DateUtility() {
	    throw new IllegalStateException("Date Utility class");
	}
	
	public static java.util.Date getUtilDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date utilDate = new java.util.Date();
		try {
			utilDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utilDate;
	}
	
	public static java.sql.Date getSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
}
