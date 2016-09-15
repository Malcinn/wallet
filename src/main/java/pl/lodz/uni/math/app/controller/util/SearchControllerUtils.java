package pl.lodz.uni.math.app.controller.util;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.DatePicker;

public class SearchControllerUtils {

	private static final Logger log = LogManager.getLogger(SearchControllerUtils.class);
	
	public static Date getDate(DatePicker datePicker) {
		try{
			Date date = DateUtils.localDateToJavaSqlDate(datePicker.getValue());
			return date;
		} catch (Exception e) {
			log.error("Error ocurred while parsing date. Message: " + e.getMessage());
		}
		return null;
	}
	
	
	public static BigDecimal getBigDecimal(String text) {
		try {
			BigDecimal bigDecimal = new BigDecimal(text);
			return bigDecimal;
		} catch (Exception e) {
			log.error("Error ocurred while parsing text to BigDecimal. Message: " + e.getMessage());
		}
		return null;
	}
}
