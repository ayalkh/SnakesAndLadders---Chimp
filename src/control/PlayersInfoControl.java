
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayersInfoControl {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private TextField text1, text2, text3, text4, text5, text6;
	@FXML
	private Label player1;

	@FXML
	private Label player2;

	@FXML
	private Label player3;

	@FXML
	private Label player4;

	@FXML
	private Label player5;

	@FXML
	private Label player6;

	@FXML
	private Button button;

	private String selectedValue;
	private int numberOfPlayers;

	@FXML
	void initialize() {
		comboBox.setItems(FXCollections.observableArrayList("easy", "medium", "hard"));
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedValue = newValue;
			}
		});

		// Initialize visibility of text fields
		updateTextFieldsVisibility();
		button.setOnAction(event -> goToPlayerObjectPage());
	}

	public void setNumberOfPlayers(int number) {
		this.numberOfPlayers = number;
		updateTextFieldsVisibility();
	}

	private void updateTextFieldsVisibility() {
		List<TextField> textFields = Arrays.asList(text1, text2, text3, text4, text5, text6);
		List<Label> labels = Arrays.asList(player1, player2, player3, player4, player5, player6);
		// Hide all first
		textFields.forEach(tf -> tf.setVisible(false));

		// Then set the necessary ones to visible based on the number of players
		for (int i = 0; i < numberOfPlayers; i++) {
			textFields.get(i).setVisible(true);
		}
		// Hide all labels first
		for (Label label : labels) {
			label.setVisible(false);
		}

		// Then set the necessary ones to visible based on the number of players
		for (int i = 0; i < numberOfPlayers; i++) {
			labels.get(i).setVisible(true);
		}
	}

	private void goToPlayerObjectPage() {
	    // Collect data from ComboBox and TextFields
	    String comboBoxValue = comboBox.getValue();
	    List<String> playerNames = new ArrayList<>();

	    boolean allFieldsFilled = true;
	    for (TextField textField : Arrays.asList(text1, text2, text3, text4, text5, text6)) {
	        if (textField.isVisible() && textField.getText().trim().isEmpty()) {
	            allFieldsFilled = false;
	            break;
	        }
	        if (textField.isVisible()) {
	            playerNames.add(textField.getText().trim());
	        }
	    }

	    // Check if all required fields are filled and level is selected
	    if (!allFieldsFilled || comboBoxValue == null) {
	        String alertMessage = !allFieldsFilled ? "Please fill in all the player names." : "Please select a difficulty level.";
	        showAlert("Missing Information", alertMessage);
	    } else {
	        loadPlayerObjectView(comboBoxValue, playerNames);
	    }
	}

	private void showAlert(String title, String content) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	}

	private void loadPlayerObjectView(String comboBoxValue, List<String> playerNames) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersObjects.fxml"));
			Parent root = loader.load();

			PlayerObject controller = loader.getController();
			controller.setComboBoxValue(comboBoxValue);
			controller.setPlayerNames(playerNames);
			PlayerObject num = loader.getController();
			num.setNumberOfPlayers(numberOfPlayers);

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current window
			((Stage) button.getScene().getWindow()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}