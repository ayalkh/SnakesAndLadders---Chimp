package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomePageController {
	@FXML
	private Button viewQuestion;
	@FXML
	private Button startGame;

	@FXML
	private void handleViewQuestionAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent questionsListView = loader.load();

			// Get the current stage using the event's source
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

			// Set the scene to the questionsList view
			stage.setScene(new Scene(questionsListView));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handlestartGame(ActionEvent event) {
		try {
			// Load the QuestionsList view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/popUpMessages/NumberOfPlayers.fxml"));
			Parent players = loader.load();

			// Get the current stage using the event's source
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

			// Set the scene to the questionsList view
			stage.setScene(new Scene(players));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}