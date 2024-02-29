package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
	private ImageView instructions;
	@FXML
	private ImageView historyButton;

	@FXML
	void initialize() {
		start.setCursor(Cursor.HAND);
		viewQuestionImage.setCursor(Cursor.HAND);
		instructions.setCursor(Cursor.HAND);
		historyButton.setCursor(Cursor.HAND);

		// Set opacity effects for 'start'
		start.setOnMouseEntered(event -> start.setOpacity(0.8));
		start.setOnMouseExited(event -> start.setOpacity(1.5));

		// Set opacity effects for 'viewQuestionImage'
		viewQuestionImage.setOnMouseEntered(event -> viewQuestionImage.setOpacity(0.8));
		viewQuestionImage.setOnMouseExited(event -> viewQuestionImage.setOpacity(1.5));

		instructions.setOnMouseEntered(event -> instructions.setOpacity(0.8));
		instructions.setOnMouseExited(event -> instructions.setOpacity(1.5));

		historyButton.setOnMouseEntered(event -> historyButton.setOpacity(0.8));
		historyButton.setOnMouseExited(event -> historyButton.setOpacity(1.5));
	}

	@FXML
	private void handleViewQuestionImageClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/popUpMessages/ManagerCode.fxml"));
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

	@FXML
	void instructionsPageOpenWhenClick(MouseEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/popUpMessages/Instructions.fxml"));
			Parent root = loader.load();

			// Create a new stage for the new scene
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Players Info");

			// Optional: if you want to block interaction with other windows
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.close();
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void openHistoryWhenButtonClicked(MouseEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HistoryBoard.fxml"));
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