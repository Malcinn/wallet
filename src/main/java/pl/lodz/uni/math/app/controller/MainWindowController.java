package pl.lodz.uni.math.app.controller;

import static java.io.File.separator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
	TableView<Operation> operationsTableView;

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
		updateDataInLeftTopVBox();
		updateOperationsTableView();
		categoriesButtonActions();
		walletsButtonActions();
		addButtonActions();
		updateButtonActions();
		removeButtonActions();
		addOnClickActionOnTableViewRow();
	}

	private void updateDataInLeftTopVBox() {
		updateTypeComboBox();
		updateCategoryComboBox();
		updateWalletComboBox();
	}

	private void updateTypeComboBox() {
		typeComboBox.getItems().clear();
		typeComboBox.getItems().addAll(OperationType.IN, OperationType.OUT);
	}

	private void updateCategoryComboBox() {
		categoryComboBox.getItems().clear();
		List<String> categoriesNames = new ArrayList<>();
		for (Category category : categoryDAO.getCategories()) {
			categoriesNames.add(category.getName());
		}
		categoryComboBox.getItems().addAll(categoriesNames);
	}

	private void updateWalletComboBox() {
		walletComboBox.getItems().clear();
		List<String> walletsNames = new ArrayList<>();
		for (Wallet wallet : walletDAO.getWallets()) {
			walletsNames.add(wallet.getName());
		}
		walletComboBox.getItems().addAll(walletsNames);
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
		walletTableColumn.setCellValueFactory(new PropertyValueFactory(WALLET_COLUMN));
		TableColumn categoryTableColumn = new TableColumn(CATEGORY_COLUMN);
		categoryTableColumn.setCellValueFactory(new PropertyValueFactory(CATEGORY_COLUMN));
		operationsTableView.getColumns().addAll(idTableColumn, typeTableColumn, dateTableColumn, descriptionTableColumn,
				amountTableColumn, walletTableColumn, categoryTableColumn);

		ObservableList<Operation> operationsObservableList = FXCollections
				.observableArrayList(operationDAO.getOperations());
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
								new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()),
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
			}
		});
	}

	private void removeButtonActions() {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			}
		});
	}

	private void addOnClickActionOnTableViewRow() {
		operationsTableView.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Operation operation = getCurrentSelectedOperationFromTableView();
				/*
				 * try { textFieldName.setText(category.getName());
				 * labelInfoSetText(""); } catch (NullPointerException e) {
				 * labelInfoSetText(INFO); log.error(
				 * "No category to select. Message: " + e.getMessage()); }
				 */
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
				updateDataInLeftTopVBox();
			}
		});
	}

}
