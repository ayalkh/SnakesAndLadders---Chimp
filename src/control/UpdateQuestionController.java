package control;

import java.io.IOException;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

public class UpdateQuestionController {

	@FXML
	private ComboBox<Integer> CorrectAnswer;

	@FXML
	private TextArea answer1Tex;

	@FXML
	private TextArea answer2Tex;

	@FXML
	private TextArea answer3Tex;

	@FXML
	private TextArea answer4Tex;

	@FXML
	private ImageView back;

	@FXML
	private ComboBox<Integer> diffLevel;

	@FXML
	private ImageView home;

	@FXML
	private TextArea questionText;

	@FXML
	private ImageView updateButton;
	int questionId;

	public void setQuestion(Question question) {
		diffLevel.getItems().addAll(1, 2, 3);
		questionId = question.getQuestionID();

		CorrectAnswer.getItems().addAll(1, 2, 3, 4);
		// Use the question details to pre-fill the form
		questionText.setPromptText(question.getQuestion());
		answer1Tex.setPromptText(question.getAnswers().get(0));
		answer2Tex.setPromptText(question.getAnswers().get(1));
		answer3Tex.setPromptText(question.getAnswers().get(2));
		answer4Tex.setPromptText(question.getAnswers().get(3));
		System.out.println("correctanswer : " + question.getCorrectAnswer());
		CorrectAnswer.setValue(question.getCorrectAnswer());
		diffLevel.setValue(question.getLevel());
		// Adjust based on your actual ComboBox options and data structure
	}

	@FXML
	void handleUpdateButton(MouseEvent event) {
		if (questionText.getText().trim().isEmpty() || answer1Tex.getText().trim().isEmpty()
				|| answer2Tex.getText().trim().isEmpty() || answer3Tex.getText().trim().isEmpty()
				|| answer4Tex.getText().trim().isEmpty()) {
			showAlert("Error", "All fields are required",
					"Please ensure all question and answer fields are filled out.");
			return;
		}

		if (diffLevel.getValue() == null || CorrectAnswer.getValue() == null) {
			showAlert("Error", "Selection Required", "Please select a difficulty level and the correct answer.");
			return;
		}

		try {
			int levelValue = diffLevel.getValue();
			int correctAnswerIndex = CorrectAnswer.getValue() - 1; // Adjust based on how your answers are indexed

			int questionId = this.questionId; // Assuming you have the question ID of the question you want to edit

			Question updatedQuestion = new Question(questionId, questionText.getText(), Arrays
					.asList(answer1Tex.getText(), answer2Tex.getText(), answer3Tex.getText(), answer4Tex.getText()),
					correctAnswerIndex, levelValue, "Team Name" // Ensure this is correctly set or updated
			);

			if (SysData.getInstance().updateQuestion(questionId, updatedQuestion)) {
				showAlert("Success", "Update Successful", "The question has been updated successfully.");

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
					Parent players = loader.load();

					Stage newStage = new Stage();
					newStage.setScene(new Scene(players));
					newStage.show();
					newStage.setTitle("Questions List ");
					// Close the current stage
					Stage currentStage = (Stage) home.getScene().getWindow();
					currentStage.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showAlert("Error", "Update Failed", "Failed to update the question.");
			}
		} catch (NumberFormatException e) {
			showAlert("Error", "Invalid Input", "Please ensure all inputs are valid.");
		}
	}

	private void showAlert(String title, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@FXML
	void initialize() {
		back.setCursor(Cursor.HAND);
		back.setOnMouseEntered(event -> back.setOpacity(0.8));
		back.setOnMouseExited(event -> back.setOpacity(1.5));

		home.setCursor(Cursor.HAND);
		home.setOnMouseEntered(event -> home.setOpacity(0.8));
		home.setOnMouseExited(event -> home.setOpacity(1.5));

		updateButton.setCursor(Cursor.HAND);
		updateButton.setOnMouseEntered(event -> updateButton.setOpacity(0.8));
		updateButton.setOnMouseExited(event -> updateButton.setOpacity(1.5));
//		diffLevel.getItems().addAll(1, 2, 3);
//
//		CorrectAnswer.getItems().addAll(1, 2, 3, 4);
	}

	@FXML
	void handleBack(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Questions List ");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void handleHomeButton(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("SNAKES & LADDERS GAME !!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
