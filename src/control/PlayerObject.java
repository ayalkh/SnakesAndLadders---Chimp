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
import model.GameSession;
import model.ObjectColor;
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
	private Label playerNameLabel;
	private int currentPlayerIndex = 0;
	@FXML
	private ImageView red;

	@FXML
	private ImageView yellow;
	private String diffLevel;
	private int numberOfPlayers;
	// A Set to keep track of selected Images
	private Set<ImageView> selectedImages = new HashSet<>();

	private List<ObjectColor> selectedColors = new ArrayList<>();

	private GameSession gameSession;
	private List<String> playerNames = new ArrayList<>();

	public void setGameSession(GameSession session) {
		System.out.println("1");
		this.gameSession = session;
		numberOfPlayers = session.getNumberOfPlayers();
		this.playerNames = session.getPlayerNames();

		for (String s : playerNames)
			System.out.println(s);

		if (playerNames == null) {
			System.out.println("Error: Player names list in GameSession is null.");
		}

		updatePlayerNameLabel();
	}

	@FXML
	void initialize() {
		System.out.println("2");

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

	@FXML
	private void handlePhotoClick(MouseEvent event) {
		ImageView clickedPhoto = (ImageView) event.getSource();
		if (!selectedImages.contains(clickedPhoto)) {
			clickedPhoto.setVisible(false);
			selectedImages.add(clickedPhoto);

			// Add color to selectedColors list based on the clicked image
			if (clickedPhoto == red) {
				selectedColors.add(ObjectColor.RED);
			} else if (clickedPhoto == blue) {
				selectedColors.add(ObjectColor.BLUE);
			} else if (clickedPhoto == green) {
				selectedColors.add(ObjectColor.GREEN);
			} else if (clickedPhoto == yellow) {
				selectedColors.add(ObjectColor.YELLOW);
			} else if (clickedPhoto == purple) {
				selectedColors.add(ObjectColor.PURPLE);
			} else if (clickedPhoto == grey) {
				selectedColors.add(ObjectColor.GREY);
			}
			currentPlayerIndex++;
			updatePlayerNameLabel();

			// Check if this was the last selection needed
			if (selectedImages.size() == numberOfPlayers) {
				openNewWindow();
			}
		}
	}

	private void openNewWindow() {
		System.out.println("4");

		for (int i = 0; i < numberOfPlayers; i++) {
			String name = playerNames.get(i);
			ObjectColor color = selectedColors.get(i);
			Player currentPlayer = new Player(name, color);
			gameSession.addPlayer(currentPlayer);
		}

		for (Player p : gameSession.getPlayers()) {
			System.out.println("why??");
			System.out.println(p.toString());
		}
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
		this.diffLevel = value;
	}

	// Method to accept player names
	public void setPlayerNames(List<String> names) {
		this.playerNames.clear();
		this.playerNames.addAll(names);
		updatePlayerNameLabel();
	}

	private void updatePlayerNameLabel() {
		System.out.println("8");
		if (currentPlayerIndex < playerNames.size()) {
			playerNameLabel.setText(playerNames.get(currentPlayerIndex));
		} else {
			playerNameLabel.setText("All players have selected their colors.");
		}
	}

}