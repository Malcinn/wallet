package pl.lodz.uni.math.app.controller.util;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainWindowControllerValidator {

	private static final Logger log = LogManager.getLogger(MainWindowControllerValidator.class);

	private static final String EMPTY_STRING = "";

	private static final String INCORRECT_VALUE_INFO = "Incorrect value";

	public static boolean checkDataInComboBox(ComboBox comboBox, Label label) {
		if (comboBox.getValue() != null) {
			label.setText(EMPTY_STRING);
			return true;
		}
		label.setText(INCORRECT_VALUE_INFO);
		return false;
	}

	public static boolean checkDataInDatePicker(DatePicker datePicker, Label label) {
		if (datePicker.getValue() != null) {
			label.setText(EMPTY_STRING);
			return true;
		}
		label.setText(INCORRECT_VALUE_INFO);
		return false;
	}

	public static boolean checkDataInTextField(TextField textField, Label label) {
		if (textField.getText() != null) {
			if (!textField.getText().trim().equals("")) {
				label.setText(EMPTY_STRING);
				return true;
			}
		}
		label.setText(INCORRECT_VALUE_INFO);
		return false;
	}

	public static boolean checkDataInTextFieldAsBigDecimal(TextField textField, Label label) {
		if (textField.getText() != null) {
			String value = textField.getText().trim();
			if (!value.equals("")) {
				try {
					value = value.replace(',', '.');
					BigDecimal b = new BigDecimal(value);
					textField.setText(value);
					label.setText(EMPTY_STRING);
					return true;
				} catch (NumberFormatException e) {
					log.error("Error ocurred while trying parse string to BigDecimal. Message: " + e.getMessage());
				}
			}
		}
		label.setText(INCORRECT_VALUE_INFO);
		return false;
	}
}
