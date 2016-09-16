package pl.lodz.uni.math.app.controller.mainwindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import pl.lodz.uni.math.app.controller.util.MainWindowControllerFinalVariables;
import pl.lodz.uni.math.app.model.dao.OperationDAO;
import pl.lodz.uni.math.app.model.domain.Operation;

public class ButtonsController {

	private Button addButton = null;

	private Button updateButton = null;

	private Button removeButton = null;

	private InputDataController inputDataController = null;

	private TableViewController tableViewController = null;

	private LabelInfoController labelInfoController = null;

	private OperationDAO operationDAO = null;

	public ButtonsController(Button addButton, Button updateButton, Button removeButton,
			InputDataController inputDataController, TableViewController tableViewController,
			LabelInfoController labelInfoController, OperationDAO operationDAO) {
		super();
		this.addButton = addButton;
		this.updateButton = updateButton;
		this.removeButton = removeButton;
		this.inputDataController = inputDataController;
		this.tableViewController = tableViewController;
		this.labelInfoController = labelInfoController;
		this.operationDAO = operationDAO;
		initalize();
	}

	public void initalize() {
		addButtonActions();
		updateButtonActions();
		removeButtonActions();
	}

	private void addButtonActions() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (inputDataController.checkData()) {
					operationDAO.addOperation(inputDataController.getOperation());
					tableViewController.update();
				}
			}
		});
	}

	private void updateButtonActions() {
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Operation operation = tableViewController.getCurrentSelectedOperation();
				if (inputDataController.checkData()) {
					try {
						updateOperationData(operation);
						if (operationDAO.updateOperation(operation)) {
							labelInfoController.update(MainWindowControllerFinalVariables.EMPTY_STRING);
							tableViewController.update();
						}
					} catch (NullPointerException e) {
						labelInfoController.update(MainWindowControllerFinalVariables.LABEL_INFO_DATA);
					}
				}
			}
		});
	}

	private void removeButtonActions() {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Operation operation = tableViewController.getCurrentSelectedOperation();
				if (operationDAO.removeOperation(operation)) {
					labelInfoController.update(MainWindowControllerFinalVariables.EMPTY_STRING);
					tableViewController.update();
				} else {
					labelInfoController.update(MainWindowControllerFinalVariables.LABEL_INFO_DATA);
				}
			}
		});
	}

	private void updateOperationData(Operation operation) {
		Operation tmpOperation = inputDataController.getOperation();
		operation.setType(tmpOperation.getType());
		operation.setDescription(tmpOperation.getDescription());
		operation.setAmount(tmpOperation.getAmount());
		operation.setDate(tmpOperation.getDate());
		operation.setCategory(tmpOperation.getCategory());
		operation.setWallet(tmpOperation.getWallet());
	}
}
