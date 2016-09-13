package pl.lodz.uni.math.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class WalletsWindowController implements Initializable {

	private static final Logger log = LogManager.getLogger(WalletsWindowController.class);

	private static final String NAME_COLUMN = "name";

	private static final String INFO = "Not selected category.";

	@FXML
	VBox vBox;

	@FXML
	ToolBar toolBar;

	@FXML
	Label labelName;

	@FXML
	TextField textFieldName;

	@FXML
	Button addButton;

	@FXML
	Button updateButton;

	@FXML
	Button removeButton;

	@FXML
	TableView tableView;

	@FXML
	Label labelInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addButtonActions();
	}

	private void addButtonActions() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Wallets");
			}
		});
	}
}
