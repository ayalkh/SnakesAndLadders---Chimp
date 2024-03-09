package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.EasyGame;
import model.HardGame;
import model.MediumGame;
import model.Player;

// ... other imports
public class PlayerObject {
	private EasyGame easyGame;
	private MediumGame mediumGame;

	private HardGame hardGame;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView blue;

	@FXML
	private ImageView green;

	@FXML
	private ImageView grey;

	@FXML
	private ImageView purple;

	@FXML
	private ImageView red;

	@FXML
	private Label playerNameLabel;
	private String difficultyLevel;
	@FXML
	private ImageView yellow;
	private String diffLevel;
	private int numberOfPlayers;
	// A Set to keep track of selected Images
	private Set<ImageView> selectedImages = new HashSet<>();
	private List<String> playerNames = new ArrayList<>();
	private List<String> selectedColors = new ArrayList<>();
	private int currentPlayerIndex = 0;

	public void setNumberOfPlayers(int number) {
		this.numberOfPlayers = number;
	}

	@FXML
	void initialize() {
		easyGame = EasyGame.getInstance();
		mediumGame = MediumGame.getInstance();
		hardGame = HardGame.getInstance();
		assert blue != null : "fx:id=\"blue\" was not injected: check your FXML file 'PlayersObjects.fxml'.";
		assert green != null : "fx:id=\"green\" was not injected: check your FXML file 'PlayersObjects.fxml'.";
		assert grey != null : "fx:id=\"grey\" was not injected: check your FXML file 'PlayersObjects.fxml'.";
		assert purple != null : "fx:id=\"purple\" was not injected: check your FXML file 'PlayersObjects.fxml'.";
		assert red != null : "fx:id=\"red\" was not injected: check your FXML file 'PlayersObjects.fxml'.";
		assert yellow != null : "fx:id=\"yellow\" was not injected: check your FXML file 'PlayersObjects.fxml'.";

		applyMouseEffects(red);
		applyMouseEffects(blue);
		applyMouseEffects(green);
		applyMouseEffects(yellow);
		applyMouseEffects(purple);
		applyMouseEffects(grey);

		diffLevel = PlayersInfoControl.getDifficultyLevel();
		switch (diffLevel) {
		case "easy":
			for (Player player : easyGame.getGameplayers()) {
				// System.out.println("getGameplayers size :" + easyGame.getGameplayers());
				playerNames.add(player.getName());
			}
			break;
		case "medium":
			for (Player player : mediumGame.getGamePlayers()) {
				// System.out.println("getGameplayers size :" + easyGame.getGameplayers());
				playerNames.add(player.getName());
			}

			break;
		case "hard":
			for (Player player : hardGame.getGameplayers()) {
				// System.out.println("getGameplayers size :" + easyGame.getGameplayers());
				playerNames.add(player.getName());
			}
			break;
		default:
			showAlert("Invalid Difficulty", "The selected difficulty level is not valid.");
			return;
		}
		for (Player player : easyGame.getGameplayers()) {
			System.out.println("getGameplayers size :" + easyGame.getGameplayers());

			playerNames.add(player.getName());

		}
		System.out.println("players size :" + playerNames.size());
		System.out.println("current player index :" + currentPlayerIndex);

		updatePlayerNameLabel();
		currentPlayerIndex++;

	}

	String navigationPath;

	@FXML
	private void handlePhotoClick(MouseEvent event) {
		ImageView clickedPhoto = (ImageView) event.getSource();
		if (!selectedImages.contains(clickedPhoto)) {
			clickedPhoto.setVisible(false); // Indicate selection
			selectedImages.add(clickedPhoto);

			// Determine color based on the clicked image
			String color = determineColor(clickedPhoto);
			if (color != null) {
				selectedColors.add(color);
			}

			// Update label for current player's choice
			updatePlayerNameLabel();
			currentPlayerIndex++;
			switch (this.diffLevel) {

			case "easy":
				if (selectedImages.size() == easyGame.getNumberofplayers()) {
					// Determine navigation path based on difficulty level selected in ComboBox
					navigationPath = determineNavigationPath(this.diffLevel);
					openNewWindow(navigationPath); // Ensure this method accepts a String parameter for the path

				}
				break;
			case "medium":

				if (selectedImages.size() == mediumGame.getNumberOfPlayers()) {
					// Determine navigation path based on difficulty level selected in ComboBox
					navigationPath = determineNavigationPath(this.diffLevel);
					openNewWindow(navigationPath); // Ensure this method accepts a String parameter for the path
				}

				break;
			case "hard":
				// Assume HardGame.getInstance() is similar to EasyGame.getInstance()
				if (selectedImages.size() == hardGame.getNumberOfPlayers()) {
					// Determine navigation path based on difficulty level selected in ComboBox
					navigationPath = determineNavigationPath(this.diffLevel);
					openNewWindow(navigationPath); // Ensure this method accepts a String parameter for the path
				}

				break;
			default:

				showAlert("Invalid Difficulty", "The selected difficulty level is not valid.");
				return;
			}

		}

	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private String determineColor(ImageView clickedPhoto) {
		if (clickedPhoto.equals(red)) {
			return "red";
		} else if (clickedPhoto.equals(blue)) {
			return "blue";
		} else if (clickedPhoto.equals(green)) {
			return "green";
		} else if (clickedPhoto.equals(yellow)) {
			return "yellow";
		} else if (clickedPhoto.equals(purple)) {
			return "purple";
		} else if (clickedPhoto.equals(grey)) {
			return "grey";
		}
		return null; // Or handle differently
	}

	private String determineNavigationPath(String difficultyLevel) {
		switch (difficultyLevel.toLowerCase()) {
		case "easy":
			return "/view/GameBoard-easy.fxml";
		case "medium":
			return "/view/GameBoard_Medium.fxml";
		case "hard":
			return "/view/HardBoard.fxml";
		default:
			return null; // Or handle default case
		}
	}

	private void updatePlayerNameLabel() {
		System.out.println("current player index into update method:" + currentPlayerIndex);
		System.out.println("player names size is " + playerNames.size());
		if (currentPlayerIndex < playerNames.size()) {
			playerNameLabel.setText(playerNames.get(currentPlayerIndex));
		} else {
			playerNameLabel.setText("All players have selected their colors.");

		}
	}

	public void openNewWindow(String path) {
		try {
			System.out.println("Opening new window with path: " + path);

			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			Parent root = loader.load();

			// Dynamically set the controller based on the path
			Object controller = loader.getController();
			if (controller instanceof GameBoardEasyController) {
				((GameBoardEasyController) controller).setSelectedColors(selectedColors);
			} else if (controller instanceof GameBoardMediumController) {
				((GameBoardMediumController) controller).setSelectedColors(selectedColors);
			} else if (controller instanceof GameBoardHardController) {
				((GameBoardHardController) controller).setSelectedColors(selectedColors);
			}

			// Create a new stage for the new scene
			Stage stage = new Stage();
			stage.setScene(new Scene(root));

			// Set title based on difficulty level
			if (path.contains("easy")) {
				stage.setTitle("LETS PLAY - Easy Level !!");
			} else if (path.contains("Medium")) {
				stage.setTitle("LETS PLAY - Medium Level !!");
			} else if (path.contains("hard")) {
				stage.setTitle("LETS PLAY - Hard Level !!");
			}

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

			// Close the current window
			// Assuming 'blue' is a component in your current window
			((Stage) blue.getScene().getWindow()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void applyMouseEffects(ImageView imageView) {

		imageView.setOnMouseEntered(event -> imageView.setCursor(Cursor.HAND));
		imageView.setOnMouseExited(event -> imageView.setCursor(Cursor.DEFAULT));
	}

	public void setComboBoxValue(String value) {
		System.out.println(2);

		// Handle the ComboBox value
		this.diffLevel = value;
	}

	public void setDifficultyLevel(String diffLevel1) {
		this.difficultyLevel = diffLevel1;
	}

}