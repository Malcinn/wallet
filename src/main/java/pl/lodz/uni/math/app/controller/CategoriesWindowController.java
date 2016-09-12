package pl.lodz.uni.math.app.controller;

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
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.services.DAOFacade;

public class CategoriesWindowController implements Initializable {

	private static final String ID_COLUMN = "id";
	
	private static final String NAME_COLUMN = "name";
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
	TableView tableView;

	private DAOFacade daoFacade = new DAOFacade();

	private CategoryDAO categoryDAO = daoFacade.getCategoryDAO(DAOFacade.DATA_SOURCE_TYPE);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTableViewData();
		addButtonActions();
	}

	private void updateTableViewData() {
		tableView.getColumns().clear();
		TableColumn idTableColumn = new TableColumn(ID_COLUMN);
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>(ID_COLUMN));
		TableColumn nameTableColumn = new TableColumn<>(NAME_COLUMN);
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<>(NAME_COLUMN));
		tableView.getColumns().addAll(idTableColumn, nameTableColumn);

		ObservableList<Category> categoriesObservableList = FXCollections
				.observableArrayList(categoryDAO.getCategories());
		tableView.setItems(categoriesObservableList);
	}

	private void addButtonActions() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				categoryDAO.addCategory(new Category(textFieldName.getText().trim()));
				updateTableViewData();
			}
		});
	}

}
