package pl.lodz.uni.math.app.controller.mainwindow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import pl.lodz.uni.math.app.controller.util.OperationTableView;
import pl.lodz.uni.math.app.model.dao.OperationDAO;
import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.OperationType;

public class TableViewController {

	private static final Logger log = LogManager.getLogger(TableViewController.class);

	private static final String ID_COLUMN_NAME = "id";

	private static final String TYPE_COLUMN_NAME = "type";

	private static final String DATE_COLUMN_NAME = "date";

	private static final String DESCRIPTION_COLUMN_NAME = "description";

	private static final String AMOUNT_COLUMN_NAME = "amount";

	private static final String WALLET_COLUMN_NAME = "wallet";

	private static final String WALLET_PROPERTY_NAME = "waletName";

	private static final String CATEGORY_COLUMN_NAME = "category";

	private static final String CATEGORY_PROPERTY_NAME = "categoryName";

	public static final String EMPTY_STRING = "";

	public static final String LABEL_INFO_DATA = "No current selected operaiton.";

	private OperationDAO operationDAO = null;

	private TableView<OperationTableView> tableView = null;

	private Label resultsLabel = null;

	private InputDataController inputDataController = null;

	private LabelInfoController labelInfoController = null;

	public TableViewController(OperationDAO operationDAO, InputDataController inputDataController,
			LabelInfoController labelInfoController, TableView<OperationTableView> tableView, Label resultsLabel) {
		super();
		this.operationDAO = operationDAO;
		this.inputDataController = inputDataController;
		this.labelInfoController = labelInfoController;
		this.tableView = tableView;
		this.resultsLabel = resultsLabel;
		initalize();
	}

	public void initalize() {
		addOnClickActionOnTableViewRow();
	}

	public void update() {
		tableViewSetColumns();
		ObservableList<OperationTableView> operationsTableViewObservableList = FXCollections
				.observableArrayList(getOperationTablewViewListFromOperationsList(operationDAO.getOperations()));
		tableView.setItems(operationsTableViewObservableList);

		updateResultsLabel();
	}

	private void tableViewSetColumns() {
		tableView.getColumns().clear();
		TableColumn idTableColumn = new TableColumn(ID_COLUMN_NAME);
		idTableColumn.setCellValueFactory(new PropertyValueFactory(ID_COLUMN_NAME));
		TableColumn typeTableColumn = new TableColumn(TYPE_COLUMN_NAME);
		typeTableColumn.setCellValueFactory(new PropertyValueFactory(TYPE_COLUMN_NAME));
		TableColumn dateTableColumn = new TableColumn(DATE_COLUMN_NAME);
		dateTableColumn.setCellValueFactory(new PropertyValueFactory(DATE_COLUMN_NAME));
		TableColumn descriptionTableColumn = new TableColumn(DESCRIPTION_COLUMN_NAME);
		descriptionTableColumn.setCellValueFactory(new PropertyValueFactory(DESCRIPTION_COLUMN_NAME));
		TableColumn amountTableColumn = new TableColumn(AMOUNT_COLUMN_NAME);
		amountTableColumn.setCellValueFactory(new PropertyValueFactory(AMOUNT_COLUMN_NAME));
		TableColumn walletTableColumn = new TableColumn(WALLET_COLUMN_NAME);
		walletTableColumn.setCellValueFactory(new PropertyValueFactory(WALLET_PROPERTY_NAME));
		TableColumn categoryTableColumn = new TableColumn(CATEGORY_COLUMN_NAME);
		categoryTableColumn.setCellValueFactory(new PropertyValueFactory(CATEGORY_PROPERTY_NAME));

		tableView.getColumns().addAll(idTableColumn, typeTableColumn, dateTableColumn, descriptionTableColumn,
				amountTableColumn, categoryTableColumn, walletTableColumn);
	}

	private List<OperationTableView> getOperationTablewViewListFromOperationsList(List<Operation> operations) {
		List<OperationTableView> resultList = new ArrayList<>();
		for (Operation operation : operationDAO.getOperations()) {
			resultList.add(new OperationTableView(operation));
		}
		return resultList;
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

		resultsLabel.setText(
				"income: " + income.toString() + " outcome: " + outcome.toString() + " sum: " + sum.toString());
	}

	private void addOnClickActionOnTableViewRow() {
		tableView.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {
					Operation operation = getCurrentSelectedOperation();
					inputDataController.update(operation.getType(), operation.getDate(), operation.getDescription(),
							operation.getAmount().toString(), operation.getCategory().getName(),
							operation.getWallet().getName());
					labelInfoController.update(EMPTY_STRING);
				} catch (NullPointerException e) {
					labelInfoController.update(LABEL_INFO_DATA);
					log.error("No category to select. Message: " + e.getMessage());
				}

			}
		});
	}

	public Operation getCurrentSelectedOperation() {
		try {
			Operation operation = (Operation) tableView.getSelectionModel().getSelectedItem();
			return operation;
		} catch (Exception e) {
			log.error("Error ocurred while gettin current selected item from tableView. Message: " + e.getMessage());
		}
		return null;
	}
}
