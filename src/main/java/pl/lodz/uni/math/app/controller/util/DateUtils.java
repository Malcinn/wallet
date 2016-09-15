package pl.lodz.uni.math.app.controller.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
	
	public static LocalDate javaUtilDateToLocalDate(Date date) {
		if (date != null)
			return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return null;
	}

	public static Date localDateToJavaUtilDate(LocalDate localDate) {
		if (localDate != null)
			return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		return null;
	}

	public static java.sql.Date localDateToJavaSqlDate(LocalDate localDate) {
		if (localDate != null)
			return java.sql.Date.valueOf(localDate);
		return null;
	}

	public static LocalDate javaSqlDateToLocalDate(java.sql.Date date) {
		if (date != null)
			return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return null;
	}
}
