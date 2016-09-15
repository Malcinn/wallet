package pl.lodz.uni.math.app.controller.mainwindow;

import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInComboBox;
import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInDatePicker;
import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInTextField;
import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.checkDataInTextFieldAsBigDecimal;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.lodz.uni.math.app.controller.util.DateUtils;
import pl.lodz.uni.math.app.controller.util.SearchControllerUtils;
import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.domain.OperationType;
import pl.lodz.uni.math.app.model.domain.QueryParameters;
import pl.lodz.uni.math.app.model.domain.Wallet;

public class SearchController {

	private static final String TYPE_COLUMN = "type";

	private DatePicker fromDatePickerSearch = null;

	private DatePicker toDatePickerSearch = null;

	private ComboBox<OperationType> typeComboBoxSearch = null;

	private TextField amountFomTextFieldSearch = null;

	private TextField amountToTextFieldSearch = null;

	private ComboBox<String> categoryComboBoxSearch = null;

	private ComboBox<String> walletComboBoxSearch = null;

	private CategoryDAO categoryDAO = null;

	private WalletDAO walletDAO = null;

	private LabelInfoController labelInfoController = null;

	public SearchController(DatePicker fromDatePickerSearch, DatePicker toDatePickerSearch,
			ComboBox<OperationType> typeComboBoxSearch, TextField amountFomTextFieldSearch,
			TextField amountToTextFieldSearch, ComboBox<String> categoryComboBoxSearch,
			ComboBox<String> walletComboBoxSearch, CategoryDAO categoryDAO, WalletDAO walletDAO,
			LabelInfoController labelInfoController) {
		super();
		this.fromDatePickerSearch = fromDatePickerSearch;
		this.toDatePickerSearch = toDatePickerSearch;
		this.typeComboBoxSearch = typeComboBoxSearch;
		this.amountFomTextFieldSearch = amountFomTextFieldSearch;
		this.amountToTextFieldSearch = amountToTextFieldSearch;
		this.categoryComboBoxSearch = categoryComboBoxSearch;
		this.walletComboBoxSearch = walletComboBoxSearch;
		this.categoryDAO = categoryDAO;
		this.walletDAO = walletDAO;
		this.labelInfoController = labelInfoController;
		initalize();
	}

	public void initalize() {
		updateData();
	}

	public QueryParameters getQueryParameters() {
		return new QueryParameters(SearchControllerUtils.getDate(fromDatePickerSearch),
		SearchControllerUtils.getDate(toDatePickerSearch), typeComboBoxSearch.getValue(),
		SearchControllerUtils.getBigDecimal(amountFomTextFieldSearch.getText()),
		SearchControllerUtils.getBigDecimal(amountToTextFieldSearch.getText()),
				categoryDAO.getCategory(categoryComboBoxSearch.getValue()),
				walletDAO.getWallet(walletComboBoxSearch.getValue()));
	}

	public void updateData() {
		updateTypeComboBox();
		updateCategoryComboBox();
		updateWalletComboBox();
	}

	private void updateTypeComboBox() {
		typeComboBoxSearch.getItems().clear();
		typeComboBoxSearch.getItems().addAll(OperationType.IN, OperationType.OUT);
	}

	private void updateCategoryComboBox() {
		categoryComboBoxSearch.getItems().clear();
		List<String> categoriesNames = new ArrayList<>();
		for (Category category : categoryDAO.getCategories()) {
			categoriesNames.add(category.getName());
		}
		categoryComboBoxSearch.getItems().addAll(categoriesNames);
	}

	private void updateWalletComboBox() {
		walletComboBoxSearch.getItems().clear();
		List<String> walletsNames = new ArrayList<>();
		for (Wallet wallet : walletDAO.getWallets()) {
			walletsNames.add(wallet.getName());
		}
		walletComboBoxSearch.getItems().addAll(walletsNames);

	}

}
