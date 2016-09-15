package pl.lodz.uni.math.app.controller.mainwindow;

import static java.io.File.separator;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.lodz.uni.math.app.controller.util.MainWindowControllerFinalVariables;
import pl.lodz.uni.math.app.model.domain.QueryParameters;

public class ToolBarController {

	private static final Logger log = LogManager.getLogger(ToolBarController.class);

	private VBox mainVBox;

	private Button searchButton;

	private Button resetButton;

	private Button categoriesButton;

	private Button walletsButton;

	private TableViewController tableViewController = null;

	private InputDataController inputDataController = null;

	private SearchController searchController = null;

	public ToolBarController(VBox mainVBox, Button searchButton, Button resetButton, Button categoriesButton,
			Button walletsButton, TableViewController tableViewController, InputDataController inputDataController,
			SearchController searchController) {
		super();
		this.mainVBox = mainVBox;
		this.searchButton = searchButton;
		this.resetButton = resetButton;
		this.categoriesButton = categoriesButton;
		this.walletsButton = walletsButton;
		this.tableViewController = tableViewController;
		this.inputDataController = inputDataController;
		this.searchController = searchController;
		initalize();
	}

	private void initalize() {
		categoriesButtonActions();
		walletsButtonActions();
		searchButtonActions();
		resetButtonActions();
	}

	private void categoriesButtonActions() {
		categoriesButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showWindow(event, MainWindowControllerFinalVariables.CATEGORIES_WINDOW);
			}
		});
	}

	private void walletsButtonActions() {
		this.walletsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showWindow(event, MainWindowControllerFinalVariables.WALLETS_WINDOW);
			}
		});
	}

	private void searchButtonActions() {
		this.searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				QueryParameters queryParameters = searchController.getQueryParameters();
				tableViewController.update(queryParameters);
			}
		});
	}

	private void resetButtonActions() {
		this.resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tableViewController.update();
			}
		});
	}
	public void showWindow(ActionEvent event, String windowName) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					separator + "fxml" + separator + windowName + MainWindowControllerFinalVariables.FXML));
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
				tableViewController.update();
				inputDataController.update(null, null, null, null, null, null);
				searchController.updateData();
			}
		});
	}

}
