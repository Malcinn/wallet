package pl.lodz.uni.math.app.controller;

import static java.io.File.separator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import pl.lodz.uni.math.app.controller.util.DateUtils;
import pl.lodz.uni.math.app.controller.util.OperationTableView;
import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.dao.OperationDAO;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.OperationType;
import pl.lodz.uni.math.app.model.domain.Wallet;
import pl.lodz.uni.math.app.model.services.DAOFacade;

import static pl.lodz.uni.math.app.controller.util.MainWindowControllerValidator.*;

public class MainWindowController implements Initializable {

	private static final Logger log = LogManager.getLogger(MainWindowController.class);

	private static final String CATEGORIES_WINDOW = "CategoriesWindow";

	private static final String WALLETS_WINDOW = "WalletsWindow";

	private static final String FXML = ".fxml";

	private static final String ID_COLUMN = "id";

	private static final String TYPE_COLUMN = "type";

	private static final String DATE_COLUMN = "date";

	private static final String DESCRIPTION_COLUMN = "description";

	private static final String AMOUNT_COLUMN = "amount";

	private static final String CATEGORY_COLUMN = "category";

	private static final String WALLET_COLUMN = "wallet";

	private static final String LABEL_INFO = "No selected operation.";
	@FXML
	VBox mainVBox;

	@FXML
	Button categoriesButton;

	@FXML
	Button walletsButton;

	@FXML
	Button statisticsButton;

	@FXML
	VBox rightVBox;

	@FXML
	TableView<OperationTableView> operationsTableView;

	@FXML
	Label resultsLabel;

	@FXML
	Label labelInfo;

	@FXML
	VBox leftTopVBox;

	@FXML
	ComboBox<OperationType> typeComboBox;

	@FXML
	Label typeLabelInfo;

	@FXML
	DatePicker dateDatePicker;

	@FXML
	Label dateLabelInfo;

	@FXML
	TextField descriptionTextField;

	@FXML
	Label descriptionLabelInfo;

	@FXML
	TextField amountTextField;

	@FXML
	Label amountLabelInfo;

	@FXML
	ComboBox<String> categoryComboBox;

	@FXML
	Label categoryLabelInfo;

	@FXML
	ComboBox<String> walletComboBox;

	@FXML
	Label walletLabelInfo;

	@FXML
	Button addButton;

	@FXML
	Button updateButton;

	@FXML
	Button removeButton;

	@FXML
	VBox leftDownVBox;

	private DAOFacade daoFacade = new DAOFacade();

	private OperationDAO operationDAO = daoFacade.getOperationDAO(DAOFacade.DATA_SOURCE_TYPE);

	private CategoryDAO categoryDAO = daoFacade.getCategoryDAO(DAOFacade.DATA_SOURCE_TYPE);

	private WalletDAO walletDAO = daoFacade.getWalletDAO(DAOFacade.DATA_SOURCE_TYPE);

	public void initialize(URL location, ResourceBundle resources) {
		updateDataInLeftTopVBox(null, null, null, null, null, null);
		updateOperationsTableView();
		categoriesButtonActions();
		walletsButtonActions();
		addButtonActions();
		updateButtonActions();
		removeButtonActions();
		addOnClickActionOnTableViewRow();
	}

	private void updateDataInLeftTopVBox(OperationType operationType, Date date, String description, String amount,
			String category, String wallet) {
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

	private void updateOperationsTableView() {
		operationsTableView.getColumns().clear();
		TableColumn idTableColumn = new TableColumn(ID_COLUMN);
		idTableColumn.setCellValueFactory(new PropertyValueFactory(ID_COLUMN));
		TableColumn typeTableColumn = new TableColumn(TYPE_COLUMN);
		typeTableColumn.setCellValueFactory(new PropertyValueFactory(TYPE_COLUMN));
		TableColumn dateTableColumn = new TableColumn(DATE_COLUMN);
		dateTableColumn.setCellValueFactory(new PropertyValueFactory(DATE_COLUMN));
		TableColumn descriptionTableColumn = new TableColumn(DESCRIPTION_COLUMN);
		descriptionTableColumn.setCellValueFactory(new PropertyValueFactory(DESCRIPTION_COLUMN));
		TableColumn amountTableColumn = new TableColumn(AMOUNT_COLUMN);
		amountTableColumn.setCellValueFactory(new PropertyValueFactory(AMOUNT_COLUMN));
		TableColumn walletTableColumn = new TableColumn(WALLET_COLUMN);
		walletTableColumn.setCellValueFactory(new PropertyValueFactory("walletName"));
		TableColumn categoryTableColumn = new TableColumn(CATEGORY_COLUMN);
		categoryTableColumn.setCellValueFactory(new PropertyValueFactory("categoryName"));

		operationsTableView.getColumns().addAll(idTableColumn, typeTableColumn, dateTableColumn, descriptionTableColumn,
				amountTableColumn, categoryTableColumn, walletTableColumn);

		ObservableList<OperationTableView> operationsTableViewObservableList = FXCollections
				.observableArrayList(getOperationTablewViewListFromOperationsList(operationDAO.getOperations()));
		operationsTableView.setItems(operationsTableViewObservableList);

		updateResultsLabel();
	}

	private void updateResultsLabel() {
		BigDecimal outcome = new BigDecimal(0);
		BigDecimal income = new BigDecimal(0);
		BigDecimal sum = null;
		for (Operation operation : operationDAO.getOperations()) {
			if (operation.getType().equals(OperationType.IN))
				income = income.add(operation.getAmount());
			if (operation.getType().equals(OperationType.OUT))
				outcome = outcome.add(operation.getAmount());
		}
		sum = income.subtract(outcome);
		if (sum.compareTo(new BigDecimal(0)) == -1)
			resultsLabel.setTextFill(Color.RED);
		else
			resultsLabel.setTextFill(Color.GREEN);
		
		resultsLabel
				.setText("income: " + income.toString() + " outcome: " + outcome.toString() + " sum: " + sum.toString());
	}

	private List<OperationTableView> getOperationTablewViewListFromOperationsList(List<Operation> operations) {
		List<OperationTableView> resultList = new ArrayList<>();
		for (Operation operation : operationDAO.getOperations()) {
			resultList.add(new OperationTableView(operation));
		}
		return resultList;
	}

	private void categoriesButtonActions() {
		categoriesButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showWindow(event, CATEGORIES_WINDOW);
			}
		});
	}

	private void walletsButtonActions() {
		this.walletsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showWindow(event, WALLETS_WINDOW);
			}
		});
	}

	private void addButtonActions() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (checkDataInLeftTopVBox()) {
					LocalDate localDate = dateDatePicker.getValue();
					if (checkDataInLeftTopVBox()) {
						operationDAO.addOperation(new Operation(typeComboBox.getValue(),
								DateUtils.localDateToJavaSqlDate(dateDatePicker.getValue()),
								descriptionTextField.getText().trim(), new BigDecimal(amountTextField.getText().trim()),
								categoryDAO.getCategory(categoryComboBox.getValue()),
								walletDAO.getWallet(walletComboBox.getValue())));
						updateOperationsTableView();
					}
				}
			}
		});
	}

	protected boolean checkDataInLeftTopVBox() {
		return checkDataInComboBox(typeComboBox, typeLabelInfo) && checkDataInDatePicker(dateDatePicker, dateLabelInfo)
				&& checkDataInTextField(descriptionTextField, descriptionLabelInfo)
				&& checkDataInTextFieldAsBigDecimal(amountTextField, amountLabelInfo)
				&& checkDataInComboBox(categoryComboBox, categoryLabelInfo)
				&& checkDataInComboBox(walletComboBox, walletLabelInfo);
	}

	private void updateButtonActions() {
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Operation operation = getCurrentSelectedOperationFromTableView();
				if (checkDataInLeftTopVBox()) {
					updateOperationData(operation);
					if (operationDAO.updateOperation(operation)) {
						labelInfo.setText("");
						updateOperationsTableView();
					} else {
						labelInfo.setText(LABEL_INFO);
					}
				}
			}
		});
	}

	private void updateOperationData(Operation operation) {
		operation.setType(typeComboBox.getValue());
		operation.setDescription(descriptionTextField.getText());
		operation.setAmount(new BigDecimal(amountTextField.getText()));
		operation.setDate(DateUtils.localDateToJavaSqlDate(dateDatePicker.getValue()));
		operation.setCategory(categoryDAO.getCategory(categoryComboBox.getValue()));
		operation.setWallet(walletDAO.getWallet(walletComboBox.getValue()));
	}

	private void removeButtonActions() {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Operation operation = getCurrentSelectedOperationFromTableView();
				if (operationDAO.removeOperation(operation)) {
					labelInfo.setText("");
					updateOperationsTableView();
				} else {
					labelInfo.setText(LABEL_INFO);
				}
			}
		});
	}

	private void addOnClickActionOnTableViewRow() {
		operationsTableView.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {
					Operation operation = getCurrentSelectedOperationFromTableView();
					updateDataInLeftTopVBox(operation.getType(), operation.getDate(), operation.getDescription(),
							operation.getAmount().toString(), operation.getCategory().getName(),
							operation.getWallet().getName());
					labelInfo.setText("");
				} catch (NullPointerException e) {
					labelInfo.setText(LABEL_INFO);
					log.error("No category to select. Message: " + e.getMessage());
				}

			}
		});
	}

	private Operation getCurrentSelectedOperationFromTableView() {
		try {
			Operation operation = (Operation) operationsTableView.getSelectionModel().getSelectedItem();
			return operation;
		} catch (Exception e) {
			log.error("Error ocurred while gettin current selected item from tableView. Message: " + e.getMessage());
		}
		return null;
	}

	public void showWindow(ActionEvent event, String windowName) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource(separator + "fxml" + separator + windowName + FXML));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle(windowName);
			stage.setScene(new Scene(root));
			stage.show();
			setActionOnOpenCloseStage(stage);
		} catch (IOException e) {
			log.error("Error ocurred while loads an object hierarchy from a FXML document. Message: " + e.getMessage()
					+ " Cause: " + e.getCause());
		}
	}

	private void setActionOnOpenCloseStage(Stage stage) {
		mainVBox.setDisable(!mainVBox.isDisable());
		stage.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				mainVBox.setDisable(!mainVBox.isDisable());
				updateOperationsTableView();
				updateDataInLeftTopVBox(null, null, null, null, null, null);
			}
		});
	}

}
