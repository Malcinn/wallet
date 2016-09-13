package pl.lodz.uni.math.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.services.DAOFacade;

public class CategoriesWindowController implements Initializable {

	private static final Logger log = LogManager.getLogger(CategoriesWindowController.class);

	private static final String ID_COLUMN = "id";

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

	private DAOFacade daoFacade = new DAOFacade();

	private CategoryDAO categoryDAO = daoFacade.getCategoryDAO(DAOFacade.DATA_SOURCE_TYPE);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTableViewData();
		addButtonActions();
		updateButtonActions();
		removeButtonActons();
		addOnClickActionOnTableViewRow();
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
				String name = textFieldName.getText().trim();
				if (!name.equals("")) {
					categoryDAO.addCategory(new Category(textFieldName.getText().trim()));
					updateTableViewData();
					labelInfoSetText("");
				}else {
					labelInfoSetText("Bad name format");
				}
			}
		});
	}

	private void updateButtonActions() {
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Category category = getCurrentSelectedCategoryFromTableView();
					category.setName(textFieldName.getText().trim());
					categoryDAO.updateCategory(category);
					updateTableViewData();
					labelInfoSetText("");
				} catch (NullPointerException e) {
					labelInfoSetText(INFO);
					log.error("Not selected Cateogry. Message: " + e.getMessage());
				}
			}
		});
	}

	private void removeButtonActons() {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Category category = getCurrentSelectedCategoryFromTableView();
				if (category != null) {
					categoryDAO.removeCategory(category);
					updateTableViewData();
					labelInfoSetText("");
				} else {
					labelInfoSetText(INFO);
				}
			}
		});
	}

	private void addOnClickActionOnTableViewRow() {
		tableView.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Category category = getCurrentSelectedCategoryFromTableView();
				try {
					textFieldName.setText(category.getName());
					labelInfoSetText("");
				} catch (NullPointerException e) {
					labelInfoSetText(INFO);
					log.error("No category to select. Message: " + e.getMessage());
				}
			}
		});
	}

	private Category getCurrentSelectedCategoryFromTableView() {
		try {
			Category category = (Category) tableView.getSelectionModel().getSelectedItem();
			return category;
		} catch (Exception e) {
			log.error("Error ocurred while gettin current selected item from tableView. Message: " + e.getMessage());
		}
		return null;
	}

	private void labelInfoSetText(String text) {
		labelInfo.setText(text);
	}
}
