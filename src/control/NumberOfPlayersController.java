package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.EasyGame;
import model.HardGame;
import model.MediumGame;

public class NumberOfPlayersController {
	private EasyGame easyGame;
	private MediumGame mediumGame;

	private HardGame hardGame;

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

	@FXML
	private ImageView homeButton;

	@FXML
	private ImageView backButton;
	@FXML
	ImageView toggleMusicButton;

	@FXML
	void handleToggleMusic(MouseEvent event) {
		BackgroundMusicPlayer.getInstance().toggleMusic();
	}

	@FXML
	void whenClickButtonHome(MouseEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent x = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("SNAKES & LADDERS GAME");
			stage.setScene(new Scene(x));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int number;

	@FXML
	void choose2(MouseEvent event) {
		this.number = 2;

		loadPlayersInfoView(event);

	}

	@FXML
	void choose3(MouseEvent event) {

		this.number = 3;

		loadPlayersInfoView(event);
	}

	@FXML
	void choose4(MouseEvent event) {
		this.number = 4;

		loadPlayersInfoView(event);

	}

	@FXML
	void choose5(MouseEvent event) {
		this.number = 5;

		loadPlayersInfoView(event);
	}

	@FXML
	void choose6(MouseEvent event) {
		this.number = 6;

		loadPlayersInfoView(event);

	}

	@FXML
	void initialize() {
		easyGame = EasyGame.getInstance();
		mediumGame = MediumGame.getInstance();
		hardGame = HardGame.getInstance();

		homeButton.setCursor(Cursor.HAND);
		homeButton.setOnMouseEntered(event -> homeButton.setOpacity(0.8));
		homeButton.setOnMouseExited(event -> homeButton.setOpacity(1.5));

		backButton.setCursor(Cursor.HAND);
		backButton.setOnMouseEntered(event -> backButton.setOpacity(0.8));
		backButton.setOnMouseExited(event -> backButton.setOpacity(1.5));

		toggleMusicButton.setCursor(Cursor.HAND);
		toggleMusicButton.setOnMouseEntered(event -> toggleMusicButton.setOpacity(0.8));
		toggleMusicButton.setOnMouseExited(event -> toggleMusicButton.setOpacity(1.5));

		assert group1 != null : "fx:id=\"group1\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group2 != null : "fx:id=\"group2\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group3 != null : "fx:id=\"group3\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group4 != null : "fx:id=\"group4\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		assert group5 != null : "fx:id=\"group5\" was not injected: check your FXML file 'NumberOfPlayers.fxml'.";
		applyMouseEffects(group1);
		applyMouseEffects(group2);
		applyMouseEffects(group3);
		applyMouseEffects(group4);
		applyMouseEffects(group5);

	}

	private void applyMouseEffects(Group group) {

		group.setOnMouseEntered(event -> group.getScene().setCursor(Cursor.HAND));
		group.setOnMouseExited(event -> group.getScene().setCursor(Cursor.DEFAULT));

	}

	private void loadPlayersInfoView(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersInfo.fxml"));
			Parent x = loader.load();

			PlayersInfoControl controller = loader.getController();
			controller.setNumberOfPlayers(number);
			easyGame.setNumberofplayers(number);
			mediumGame.setNumberOfPlayers(number);
			hardGame.setNumberOfPlayers(number);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Players Info");
			// Set the scene with specified width and height
			Scene scene = new Scene(x, 505, 489);

			stage.setScene(scene);

			stage.setResizable(false);

			// Optional: If you want to make sure the window is centered on the screen
			stage.centerOnScreen();

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}