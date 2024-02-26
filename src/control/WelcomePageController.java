package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WelcomePageController {
	@FXML
	private Button viewQuestion;
	@FXML
	private Button startGame;
	@FXML
	private ImageView start;

	@FXML
	private ImageView viewQuestionImage;

	@FXML
	void initialize() {
		start.setCursor(Cursor.HAND);
		viewQuestionImage.setCursor(Cursor.HAND);

		// Set opacity effects for 'start'
		start.setOnMouseEntered(event -> start.setOpacity(0.8));
		start.setOnMouseExited(event -> start.setOpacity(1.5));

		// Set opacity effects for 'viewQuestionImage'
		viewQuestionImage.setOnMouseEntered(event -> viewQuestionImage.setOpacity(0.8));
		viewQuestionImage.setOnMouseExited(event -> viewQuestionImage.setOpacity(1.5));
	}

	@FXML
	private void handleViewQuestionImageClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionsList.fxml")); // Change to the
																									// correct path
			Parent questionsListView = loader.load();

			Stage newStage = new Stage();
			newStage.setScene(new Scene(questionsListView));
			newStage.show();

			// Close the current stage
			Stage currentStage = (Stage) viewQuestionImage.getScene().getWindow();
			currentStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleStartImageClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/popUpMessages/NumberOfPlayers.fxml"));
			Parent players = loader.load();

			Stage newStage = new Stage();
			newStage.setScene(new Scene(players));
			newStage.show();

			// Close the current stage
			Stage currentStage = (Stage) start.getScene().getWindow();
			currentStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}