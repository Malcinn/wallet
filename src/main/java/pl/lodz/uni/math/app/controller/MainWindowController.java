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
import java.util.MissingFormatArgumentException;
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

	private static final String FXML = ".fxml";

	private static final String CATEGORIES_WINDOW = "CategoriesWindow";

	private static final String WALLETS_WINDOW = "WalletsWindow";

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

	private MainWindowRightVboxController mainWindowRightVboxController = null;

	private MainWindowLeftTopVBoxController mainWindowLeftTopVBoxController = null;

	public void initialize(URL location, ResourceBundle resources) {
		initalizeVariables();
		mainWindowLeftTopVBoxController.updateDataInLeftTopVBox(null, null, null, null, null, null);
		mainWindowRightVboxController.updateTableViewData();
		categoriesButtonActions();
		walletsButtonActions();
	}

	private void initalizeVariables() {
		this.mainWindowLeftTopVBoxController = new MainWindowLeftTopVBoxController(typeComboBox, typeLabelInfo,
				dateDatePicker, dateLabelInfo, descriptionTextField, descriptionLabelInfo, amountTextField,
				amountLabelInfo, categoryComboBox, categoryLabelInfo, walletComboBox, walletLabelInfo, addButton,
				updateButton, removeButton, categoryDAO, walletDAO, operationDAO, this);
		this.mainWindowRightVboxController = new MainWindowRightVboxController(operationDAO, operationsTableView,
				resultsLabel, labelInfo, mainWindowLeftTopVBoxController);
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
				mainWindowRightVboxController.updateTableViewData();
				mainWindowLeftTopVBoxController.updateDataInLeftTopVBox(null, null, null, null, null, null);
			}
		});
	}

	public Operation getCurrentSelectedOperationFromTableView() {
		return mainWindowRightVboxController.getCurrentSelectedOperationFromTableView();
	}

	public void updateInfoLabelText(String emptyString) {
		mainWindowRightVboxController.updateInfoLabelText(emptyString);

	}

	public void updateTableViewData() {
		mainWindowRightVboxController.updateTableViewData();
	}

}
