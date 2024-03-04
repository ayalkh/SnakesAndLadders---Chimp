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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.EasyGame;
import model.Player;

// ... other imports
public class PlayerObject {
    private EasyGame easyGame;

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

		for (Player player : easyGame.getGameplayers()) {
			playerNames.add(player.getName());

		}
		System.out.println("players size :" + playerNames.size());
		System.out.println("current player index :" + currentPlayerIndex);

		updatePlayerNameLabel();
		currentPlayerIndex++;

	}

	@FXML
	private void handlePhotoClick(MouseEvent event) {

		ImageView clickedPhoto = (ImageView) event.getSource();
		if (!selectedImages.contains(clickedPhoto)) {
			clickedPhoto.setVisible(false); // Or set to disabled, etc.
			selectedImages.add(clickedPhoto);

			// Add color to selectedColors list based on the clicked image
			if (clickedPhoto == red) {
				selectedColors.add("red");
			} else if (clickedPhoto == blue) {
				selectedColors.add("blue");
			} else if (clickedPhoto == green) {
				selectedColors.add("green");
			} else if (clickedPhoto == yellow) {
				selectedColors.add("yellow");
			} else if (clickedPhoto == purple) {
				selectedColors.add("purple");
			} else if (clickedPhoto == grey) {
				selectedColors.add("grey");
			}


			// change Label of the current player when choosing the object
			updatePlayerNameLabel();
			currentPlayerIndex++;

			// Check if this was the last selection needed
			if (selectedImages.size() == easyGame.getNumberofplayers()) {
				openNewWindow();
			}

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

	private void openNewWindow() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameBoard-easy.fxml"));
			Parent root = loader.load();
			GameBoardEasyController controller = loader.getController();
			controller.setSelectedColors(selectedColors);

			// Create a new stage for the new scene
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Players Info");

			// Optional: if you want to block interaction with other windows
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.show();

			// If you want to close the current window:
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

		// Handle the ComboBox value
		this.diffLevel = value;
	}

}