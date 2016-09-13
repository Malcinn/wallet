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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.domain.Wallet;
import pl.lodz.uni.math.app.model.services.DAOFacade;

public class WalletsWindowController implements Initializable {

	private static final Logger log = LogManager.getLogger(WalletsWindowController.class);

	private static final String ID_COLUMN = "id";
		
	private static final String NAME_COLUMN = "name";

	private static final String INFO = "Not selected wallet.";

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

	private WalletDAO walletDAO = daoFacade.getWalletDAO(DAOFacade.DATA_SOURCE_TYPE);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addButtonActions();
		updateButtonActions();
		removeButtonActions();
		updateTableViewData();
	}

	private void addButtonActions() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = textFieldName.getText().trim();
				if (!name.equals("")) {
					walletDAO.addWallet(new Wallet(name));
					updateTableViewData();
					labelInfoSetText("");
				} else {
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
					Wallet wallet = getCurrentSelectedWalletFromTableView();
					wallet.setName(textFieldName.getText().trim());
					walletDAO.updateWallet(wallet);
					updateTableViewData();
					labelInfoSetText("");
				} catch (NullPointerException e) {
					labelInfoSetText(INFO);
					log.error("Not selected wallet. Message: " + e.getMessage());
				}
			}
		});
	}

	private void removeButtonActions() {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Wallet wallet = getCurrentSelectedWalletFromTableView();
				if (wallet != null) {
					walletDAO.removeWallet(wallet);
					updateTableViewData();
					labelInfoSetText("");
				} else {
					labelInfoSetText(INFO);
				}
			}
		});
	}

	private void updateTableViewData() {
		tableView.getColumns().clear();
		TableColumn idTableColumn = new TableColumn(ID_COLUMN);
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>(ID_COLUMN));
		TableColumn nameTableColumn = new TableColumn<>(NAME_COLUMN);
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<>(NAME_COLUMN));

		tableView.getColumns().addAll(idTableColumn, nameTableColumn);

		ObservableList<Wallet> walletsObservableList = FXCollections
				.observableArrayList(walletDAO.getWallets());
		tableView.setItems(walletsObservableList);
	}

	private void addOnClickActionOnTableViewRow() {
		tableView.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Wallet wallet = getCurrentSelectedWalletFromTableView();
				try {
					textFieldName.setText(wallet.getName());
					labelInfoSetText("");
				} catch (NullPointerException e) {
					labelInfoSetText(INFO);
					log.error("No wallet to select. Message: " + e.getMessage());
				}
			}
		});
	}

	private Wallet getCurrentSelectedWalletFromTableView() {
		try {
			Wallet wallet = (Wallet) tableView.getSelectionModel().getSelectedItem();
			return wallet;
		} catch (Exception e) {
			log.error("Error ocurred while gettin current selected item from tableView. Message: " + e.getMessage());
		}
		return null;
	}

	private void labelInfoSetText(String text) {
		labelInfo.setText(text);
	}
}
