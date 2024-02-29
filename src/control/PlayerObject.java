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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;

// ... other imports
public class PlayerObject {

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
	private ImageView yellow;
	private String diffLevel;
	private int numberOfPlayers;
	// A Set to keep track of selected Images
	private Set<ImageView> selectedImages = new HashSet<>();
	private List<String> playerNames = new ArrayList<>();
	private List<String> selectedColors = new ArrayList<>();

	public void setNumberOfPlayers(int number) {
		this.numberOfPlayers = number;
	}

	@FXML
	void initialize() {
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

	}

//	@FXML
//	private void handlePhotoClick(MouseEvent event) {
//		ImageView clickedPhoto = (ImageView) event.getSource();
//		if (!selectedImages.contains(clickedPhoto)) {
//			clickedPhoto.setVisible(false); // Or set to disabled, etc.
//			selectedImages.add(clickedPhoto);
//
//			// Check if this was the last selection needed
//			if (selectedImages.size() == numberOfPlayers) {
//				openNewWindow();
//			}
//		}
//
//	}

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

			// Check if this was the last selection needed
			if (selectedImages.size() == Main.easygame.getNumberofplayers()) {
				openNewWindow();
			}
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