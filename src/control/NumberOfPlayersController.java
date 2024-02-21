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
		// If the groups contain ImageView children, you can apply additional effects to
		// them
	}

	private void loadPlayersInfoView(MouseEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersInfo.fxml"));
			Parent x = loader.load();

			PlayersInfoControl playerInfoControl = loader.getController();
			playerInfoControl.setNumberOfPlayers(number);

			// System.out.println(number);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(x));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}