package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NumberOfPlayersController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Group group1;

	@FXML
	private Group group2;

	@FXML
	private Group group3;

	@FXML
	private Group group4;

	@FXML
	private Group group5;
	int number;

	@FXML
	void choose2(MouseEvent event) {
		number = 2;
		showAlert("Group 1 clicked", "You clicked on group 1!");

	}

	@FXML
	void choose3(MouseEvent event) {
		number = 3;

		try {
			// Load the PlayersInfo view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersInfo.fxml"));
			Parent x = loader.load();

			// Get the current stage using the event's source
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Set the scene to the PlayersInfo view
			stage.setScene(new Scene(x));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void choose4(MouseEvent event) {
		number = 4;
		Alerts.alertBox(AlertType.ERROR, "", "You have to select an answer", null);

	}

	@FXML
	void choose5(MouseEvent event) {
		number = 5;

	}

	@FXML
	void choose6(MouseEvent event) {
		number = 6;

	}

	@FXML
	void initialize() {
		assert group1 != null : "fx:id=\"group1\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group2 != null : "fx:id=\"group2\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group3 != null : "fx:id=\"group3\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group4 != null : "fx:id=\"group4\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group5 != null : "fx:id=\"group5\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";

	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

}