package pl.lodz.uni.math.app.controller.mainwindow;

import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInComboBox;
import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInDatePicker;
import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInTextField;
import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInTextFieldAsBigDecimal;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.lodz.uni.math.app.controller.util.DateUtils;
import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.OperationType;
import pl.lodz.uni.math.app.model.domain.Wallet;

public class InputDataController {

	private ComboBox<OperationType> typeComboBox = null;;

	private Label typeLabelInfo = null;

	private DatePicker dateDatePicker = null;

	private Label dateLabelInfo = null;

	private TextField descriptionTextField = null;

	private Label descriptionLabelInfo = null;

	private TextField amountTextField = null;

	private Label amountLabelInfo = null;

	private ComboBox<String> categoryComboBox = null;

	private Label categoryLabelInfo = null;

	private ComboBox<String> walletComboBox = null;

	private Label walletLabelInfo = null;

	private CategoryDAO categoryDAO = null;

	private WalletDAO walletDAO = null;

	public InputDataController(ComboBox<OperationType> typeComboBox, Label typeLabelInfo, DatePicker dateDatePicker,
			Label dateLabelInfo, TextField descriptionTextField, Label descriptionLabelInfo, TextField amountTextField,
			Label amountLabelInfo, ComboBox<String> categoryComboBox, Label categoryLabelInfo,
			ComboBox<String> walletComboBox, Label walletLabelInfo, CategoryDAO categoryDAO, WalletDAO walletDAO) {
		super();
		this.typeComboBox = typeComboBox;
		this.typeLabelInfo = typeLabelInfo;
		this.dateDatePicker = dateDatePicker;
		this.dateLabelInfo = dateLabelInfo;
		this.descriptionTextField = descriptionTextField;
		this.descriptionLabelInfo = descriptionLabelInfo;
		this.amountTextField = amountTextField;
		this.amountLabelInfo = amountLabelInfo;
		this.categoryComboBox = categoryComboBox;
		this.categoryLabelInfo = categoryLabelInfo;
		this.walletComboBox = walletComboBox;
		this.walletLabelInfo = walletLabelInfo;
		this.categoryDAO = categoryDAO;
		this.walletDAO = walletDAO;
	}

	public void update(OperationType operationType, Date date, String description, String amount, String category,
			String wallet) {
		updateTypeComboBox(operationType);
		dateDatePicker.setValue(DateUtils.javaSqlDateToLocalDate(date));
		descriptionTextField.setText(description);
		amountTextField.setText(amount);
		updateCategoryComboBox(category);
		updateWalletComboBox(wallet);
	}

	private void updateTypeComboBox(OperationType operationType) {
		typeComboBox.getItems().clear();
		typeComboBox.getItems().addAll(OperationType.IN, OperationType.OUT);
		if (operationType != null)
			typeComboBox.setValue(operationType);
	}

	private void updateCategoryComboBox(String categoryName) {
		categoryComboBox.getItems().clear();
		List<String> categoriesNames = new ArrayList<>();
		for (Category category : categoryDAO.getCategories()) {
			categoriesNames.add(category.getName());
		}
		categoryComboBox.getItems().addAll(categoriesNames);
		if (categoryName != null && !categoriesNames.equals(""))
			categoryComboBox.setValue(categoryName);
	}

	private void updateWalletComboBox(String walletName) {
		walletComboBox.getItems().clear();
		List<String> walletsNames = new ArrayList<>();
		for (Wallet wallet : walletDAO.getWallets()) {
			walletsNames.add(wallet.getName());
		}
		walletComboBox.getItems().addAll(walletsNames);
		if (walletName != null && !walletName.equals(""))
			walletComboBox.setValue(walletName);
	}

	public boolean checkData() {
		return checkDataInComboBox(typeComboBox, typeLabelInfo) && checkDataInDatePicker(dateDatePicker, dateLabelInfo)
				&& checkDataInTextField(descriptionTextField, descriptionLabelInfo)
				&& checkDataInTextFieldAsBigDecimal(amountTextField, amountLabelInfo)
				&& checkDataInComboBox(categoryComboBox, categoryLabelInfo)
				&& checkDataInComboBox(walletComboBox, walletLabelInfo);
	}

	public Operation getOperation() {
		return new Operation(typeComboBox.getValue(), DateUtils.localDateToJavaSqlDate(dateDatePicker.getValue()),
				descriptionTextField.getText().trim(), new BigDecimal(amountTextField.getText().trim()),
				categoryDAO.getCategory(categoryComboBox.getValue()), walletDAO.getWallet(walletComboBox.getValue()));
	}
}
