package pl.lodz.uni.math.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainWindowController implements Initializable {

	@FXML
	Button addOperationButton;
	
	@FXML
	Button categoriesButton;
	
	@FXML
	Button walletsButton;
	
	@FXML
	Button statisticsButton;
	
	@FXML
	VBox leftVBox;
	
	@FXML
	VBox rightTopVBox;
	
	@FXML
	VBox rightDownVBox;
	
	public void initialize(URL location, ResourceBundle resources) {
		addOperationButton.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("asdasd");
			}
		});
	}

}
