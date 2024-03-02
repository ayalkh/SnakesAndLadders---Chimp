
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GameSession;
import model.Player;

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
	private ImageView startButton;

	private String selectedValue;
	private int numberOfPlayers;

	private GameSession gameSession;

	@FXML
	private Label startLabel;

	@FXML
	private ImageView homeButton;

	@FXML
	void whenClickButtonHome(MouseEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent x = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(x));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		comboBox.setItems(FXCollections.observableArrayList("easy", "medium", "hard"));
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedValue = newValue;
			}
		});
		homeButton.setCursor(Cursor.HAND);
		homeButton.setOnMouseEntered(event -> homeButton.setOpacity(0.8));
		homeButton.setOnMouseExited(event -> homeButton.setOpacity(1.5));

		startButton.setCursor(Cursor.HAND);
		startButton.setOnMouseEntered(event -> startButton.setOpacity(0.8));
		startButton.setOnMouseExited(event -> startButton.setOpacity(1.5));

		startLabel.setCursor(Cursor.HAND);
		startLabel.setOnMouseEntered(event -> startLabel.setOpacity(0.8));
		startLabel.setOnMouseExited(event -> startLabel.setOpacity(1.5));
		// Initialize visibility of text fields
		updateTextFieldsVisibility();

		startLabel.setOnMouseClicked(event -> goToPlayerObjectPage());
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

	@FXML
	private void goToPlayerObjectPage() {
		// Collect data from ComboBox and TextFields
		String comboBoxValue = comboBox.getValue();
		List<String> playerNames = new ArrayList<>();

		boolean allFieldsFilled = true;
		Set<String> uniqueNames = new HashSet<>(); // To ensure names are unique

		for (TextField textField : Arrays.asList(text1, text2, text3, text4, text5, text6)) {
			if (textField.isVisible() && textField.getText().trim().isEmpty()) {
				allFieldsFilled = false;
				break;
			}
			if (textField.isVisible()) {
				String playerName = textField.getText().trim();

				// Check if the name is valid (only letters)
				if (!playerName.matches("^[a-zA-Z]+$")) {
					showAlert("Invalid Name", "Player names can only contain letters.");
					textField.setText("");

					return;
				}

				// Check if the name is unique
				if (!uniqueNames.add(playerName)) {
					showAlert("Duplicate Names", "Player names must be different.");
					textField.setText("");
					return;
				}

				playerNames.add(playerName);
			}
		}

		// Check if all required fields are filled and level is selected
		if (!allFieldsFilled || comboBoxValue == null) {
			String alertMessage = !allFieldsFilled ? "Please fill in all the player names."
					: "Please select a difficulty level.";
			showAlert("Missing Information", alertMessage);
		} else {
			for (int i = 0; i < numberOfPlayers; i++) {
				Player player = new Player();
				player.setName(playerNames.get(i));
				Main.easygame.getGameplayers().add(player);
			}
			loadPlayerObjectView();
		}
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@FXML
	private void loadPlayerObjectView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersObjects.fxml"));
			Parent root = loader.load();

			PlayerObject controller = loader.getController();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current window
			((Stage) startButton.getScene().getWindow()).close();
			((Stage) startLabel.getScene().getWindow()).close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		updateTextFieldsVisibility();
	}
}