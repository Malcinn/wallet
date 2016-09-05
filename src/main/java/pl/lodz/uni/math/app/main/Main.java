package pl.lodz.uni.math.app.main;

import static java.io.File.separator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = (VBox) FXMLLoader.load(getClass().getResource(separator + "fxml" + separator + "MainWindow.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets()
				.add(getClass().getResource(separator + "styles" + separator + "styles.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Wallet");
		primaryStage.show();

	}
}
