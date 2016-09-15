package pl.lodz.uni.math.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

public class MainWindowRightVboxController {

	private static final String EMPTY_STRING = "";
	
	private static final String LABEL_INFO_DATA = "No current selected operaiton.";
	
	private OperationDAO operationDAO  = null;
	
	private TableView tableView = null;

	private Label resultsLabel = null;

	private Label infoLabel = null;
	
	private MainWindowLeftTopVBoxController mainWindowLeftTopVBoxController = null;
	
	public MainWindowRightVboxController(OperationDAO operationDAO, TableView tableView, Label resultsLabel, Label infoLabel) {
		this.operationDAO = operationDAO;
		this.tableView = tableView;
		this.resultsLabel = resultsLabel;
		this.infoLabel = infoLabel;
	}
	
	public void updateTableViewData() {
		tableView.getColumns().clear();
		TableColumn idTableColumn = new TableColumn("id");
		idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn typeTableColumn = new TableColumn("type");
		typeTableColumn.setCellValueFactory(new PropertyValueFactory("type"));
		TableColumn dateTableColumn = new TableColumn("date");
		dateTableColumn.setCellValueFactory(new PropertyValueFactory("date"));
		TableColumn descriptionTableColumn = new TableColumn("description");
		descriptionTableColumn.setCellValueFactory(new PropertyValueFactory("description"));
		TableColumn amountTableColumn = new TableColumn("amount");
		amountTableColumn.setCellValueFactory(new PropertyValueFactory("amount"));
		TableColumn walletTableColumn = new TableColumn("wallet");
		walletTableColumn.setCellValueFactory(new PropertyValueFactory("walletName"));
		TableColumn categoryTableColumn = new TableColumn("category");
		categoryTableColumn.setCellValueFactory(new PropertyValueFactory("categoryName"));

		tableView.getColumns().addAll(idTableColumn, typeTableColumn, dateTableColumn, descriptionTableColumn,
				amountTableColumn, categoryTableColumn, walletTableColumn);

		ObservableList<OperationTableView> operationsTableViewObservableList = FXCollections
				.observableArrayList(getOperationTablewViewListFromOperationsList(operationDAO.getOperations()));
		tableView.setItems(operationsTableViewObservableList);

		updateResultsLabel();
	}
	
	private List<OperationTableView> getOperationTablewViewListFromOperationsList(List<Operation> operations) {
		List<OperationTableView> resultList = new ArrayList<>();
		for (Operation operation : operationDAO.getOperations()) {
			resultList.add(new OperationTableView(operation));
		}
		return resultList;
	}
	
	public void updateResultsLabel() {
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
	
	public void updateInfoLabelText(String text) {
		this.infoLabel.setText(text);
	}

	private void addOnClickActionOnTableViewRow() {
		tableView.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {
					Operation operation = getCurrentSelectedOperationFromTableView();
					updateDataInLeftTopVBox(operation.getType(), operation.getDate(), operation.getDescription(),
							operation.getAmount().toString(), operation.getCategory().getName(),
							operation.getWallet().getName());
					updateInfoLabelText("");
				} catch (NullPointerException e) {
					labelInfo.setText(LABEL_INFO_DATA);
					log.error("No category to select. Message: " + e.getMessage());
				}

			}
		});
	}
	
	private Operation getCurrentSelectedOperationFromTableView() {
		try {
			Operation operation = (Operation) tableView.getSelectionModel().getSelectedItem();
			return operation;
		} catch (Exception e) {
			log.error("Error ocurred while gettin current selected item from tableView. Message: " + e.getMessage());
		}
		return null;
	}
}
