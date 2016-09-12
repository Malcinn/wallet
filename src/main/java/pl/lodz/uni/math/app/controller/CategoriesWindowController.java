package pl.lodz.uni.math.app.controller;

import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.services.DAOFacade;

public class CategoriesWindowController implements Initializable {

	@FXML
	VBox vBox;

	@FXML
	ToolBar toolBar;

	@FXML
	Label labelName;

	@FXML
	TextField textfieldName;

	@FXML
	Button addButton;

	@FXML
	TableView tableView;

	private DAOFacade daoFacade = new DAOFacade();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTableViewData();
		addButtonActions();
	}

	private void updateTableViewData() {
		TableColumn idTableColumn = new TableColumn<>("id");
		TableColumn nameTableColumn = new TableColumn<>("name");
		tableView.getColumns().addAll(idTableColumn, nameTableColumn);
		ObservableList<Category> categoriesObservableList = FXCollections
				.observableArrayList(daoFacade.getCategoryDAO(daoFacade.DATA_SOURCE_TYPE).getCategories());
	}

	private void addButtonActions() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Categories");
			}
		});
	}

}
