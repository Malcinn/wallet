package pl.lodz.uni.math.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.lodz.uni.math.app.controller.mainwindow.ButtonsController;
import pl.lodz.uni.math.app.controller.mainwindow.InputDataController;
import pl.lodz.uni.math.app.controller.mainwindow.LabelInfoController;
import pl.lodz.uni.math.app.controller.mainwindow.SearchController;
import pl.lodz.uni.math.app.controller.mainwindow.TableViewController;
import pl.lodz.uni.math.app.controller.mainwindow.ToolBarController;
import pl.lodz.uni.math.app.controller.util.OperationTableView;
import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.dao.OperationDAO;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.domain.OperationType;
import pl.lodz.uni.math.app.model.services.DAOFacade;

public class MainWindowController implements Initializable {

	private static final Logger log = LogManager.getLogger(MainWindowController.class);

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
	TableView<OperationTableView> tableView;

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

	private ToolBarController toolBarController = null;

	private SearchController searchController = null;

	private TableViewController tableViewController = null;

	private LabelInfoController labelInfoController = null;

	private ButtonsController buttonsController = null;

	private InputDataController inputDataController = null;

	public void initialize(URL location, ResourceBundle resources) {
		initalizeVariables();
		inputDataController.update(null, null, null, null, null, null);
		tableViewController.update();
	}

	private void initalizeVariables() {

		this.inputDataController = new InputDataController(typeComboBox, typeLabelInfo, dateDatePicker, dateLabelInfo,
				descriptionTextField, descriptionLabelInfo, amountTextField, amountLabelInfo, categoryComboBox,
				categoryLabelInfo, walletComboBox, walletLabelInfo, categoryDAO, walletDAO);
		this.labelInfoController = new LabelInfoController(amountLabelInfo);
		this.tableViewController = new TableViewController(operationDAO, inputDataController, labelInfoController,
				tableView, resultsLabel);
		this.buttonsController = new ButtonsController(addButton, updateButton, removeButton, inputDataController,
				tableViewController, labelInfoController, operationDAO);
		this.toolBarController = new ToolBarController(mainVBox, categoriesButton, walletsButton, tableViewController,
				inputDataController);

	}

}
