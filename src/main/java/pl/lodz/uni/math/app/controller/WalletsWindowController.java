package pl.lodz.uni.math.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class WalletsWindowController implements Initializable {

	@FXML
	VBox vBox;

	@FXML
	ToolBar toolBar;

	@FXML
	Button addButton;

	@FXML
	TableView tableView;

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
