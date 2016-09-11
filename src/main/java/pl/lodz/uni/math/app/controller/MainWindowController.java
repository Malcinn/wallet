package pl.lodz.uni.math.app.controller;

import static java.io.File.separator;

import java.io.IOException;
import java.net.URL;
import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindowController implements Initializable {

	private static final String CATEGORIES_WINDOW = "CategoriesWindow";

	private static final String WALLETS_WINDOW = "WalletsWindow";

	private static final String FXML = ".fxml";

	@FXML
	VBox mainVBox;
	
	@FXML
	Button addOperationButton;

	@FXML
	Button categoriesButton;

	@FXML
	Button walletsButton;

	@FXML
	Button statisticsButton;

	@FXML
	VBox rightVBox;

	@FXML
	VBox leftTopVBox;

	@FXML
	VBox leftDownVBox;

	public void initialize(URL location, ResourceBundle resources) {
		categoriesButtonActions();
		walletsButtonActions();
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
		this.
		walletsButton.setOnAction(new EventHandler<ActionEvent>() {
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
			
		/*	stage.setOnShown(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					mainVBox.setDisable(true);
				}
			});
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					mainVBox.setDisable(false);
				}
			});*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
