package control;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

public class ViewQuestionDetailsController {
	@FXML
	private ImageView updateButton;
	@FXML
	private TextArea answer1Tex;
	@FXML
	private ImageView home;
	@FXML
	private TextArea answer2Tex;

	@FXML
	private TextArea answer3Tex;

	@FXML
	private TextArea answer4Tex;

	@FXML
	private ImageView deleteButton;

	@FXML
	private ImageView back;

	@FXML
	private Label difficultyLabel;

//	@FXML
//	private TextField questionText;

	@FXML
	private TextArea questionText;
	@FXML
	private Label teamLabel;
	@FXML
	ImageView toggleMusicButton;

	@FXML
	void handleToggleMusic(MouseEvent event) {
		BackgroundMusicPlayer.getInstance().toggleMusic();
	}

	private int correctAnswer;
	private int questionID;

	// Method to set the question and its details
	public void setQuestion(Question question) {
		System.out.println(2);

		questionText.setText(question.getQuestion());
		List<String> answers = question.getAnswers();
		answer1Tex.setText(answers.size() > 0 ? answers.get(0) : "");
		answer2Tex.setText(answers.size() > 1 ? answers.get(1) : "");
		answer3Tex.setText(answers.size() > 2 ? answers.get(2) : "");
		answer4Tex.setText(answers.size() > 3 ? answers.get(3) : "");
		correctAnswer = question.getCorrectAnswer();
		questionID = question.getQuestionID();
		System.out.println("questionID in ViewQuestionDetailsController class is :" + questionID);
		difficultyLabel.setText("Difficulty Level : " + question.getLevel()); // Set difficulty
		teamLabel.setText("Team : " + question.getTeam()); // Set team
		System.out.println(question.getTeam());

		if (correctAnswer == 1) {
			answer1Tex.setStyle("-fx-control-inner-background: #e0ffe0; " // Light green background
					+ "-fx-effect: dropshadow(gaussian, rgba(0,255,0,0.8), 10, 0.5, 0, 0);");

			answer2Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer3Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer4Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
		} else if (correctAnswer == 2) {
			answer2Tex.setStyle("-fx-control-inner-background: #e0ffe0; " // Light green background
					+ "-fx-effect: dropshadow(gaussian, rgba(0,255,0,0.8), 10, 0.5, 0, 0);");

			answer1Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer3Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer4Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
		} else if (correctAnswer == 3) {
			answer3Tex.setStyle("-fx-control-inner-background: #e0ffe0; " // Light green background
					+ "-fx-effect: dropshadow(gaussian, rgba(0,255,0,0.8), 10, 0.5, 0, 0);");
			answer1Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer2Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer4Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
		} else {
			answer4Tex.setStyle("-fx-control-inner-background: #e0ffe0; " // Light green background
					+ "-fx-effect: dropshadow(gaussian, rgba(0,255,0,0.8), 10, 0.5, 0, 0);");
			answer3Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer2Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
			answer1Tex.setStyle("-fx-control-inner-background: #ffe0e0;" // Light red background
					+ "-fx-effect: dropshadow(gaussian, rgba(255,0,0,0.8), 10, 0.5, 0, 0);"); // Red glow
		}

	}

	private int questionId;

	public void setQuestionId(int questionId) {
		System.out.println("questionId in ViewQuestionDetailsController is :" + questionId);
		this.questionId = questionId;
		// You can load question details here based on questionId if necessary
	}

	@FXML
	void handleBack(MouseEvent event) {
		try {
			// Load QuestionsList.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
			Parent root = loader.load();

			// Reuse the existing stage
			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Questions List");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading QuestionsList.fxml: " + e.getMessage());
		}
	}

	@FXML
	void handleHomeButton(MouseEvent event) {
		try {
			// Load QuestionsList.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent root = loader.load();

			// Reuse the existing stage
			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("SNAKES & LADDERS GAME !!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading QuestionsList.fxml: " + e.getMessage());
		}
	}

	public void initialize() {
		System.out.println(1);
		System.out.println("the correct answer in initialize is : " + correctAnswer);

		// Enable text wrapping for all TextArea components
		Stream.of(questionText, answer1Tex, answer2Tex, answer3Tex, answer4Tex)
				.forEach(textArea -> textArea.setWrapText(true));

		// Adjust TextArea height dynamically based on its content for questionText
		adjustTextAreaHeight(questionText, 444, 78);

		// Adjust TextArea height for answer TextAreas, assuming all have the same size
		// as mentioned
		Stream.of(answer1Tex, answer2Tex, answer3Tex, answer4Tex)
				.forEach(answerTextArea -> adjustTextAreaHeight(answerTextArea, 379, 46));

		back.setCursor(Cursor.HAND);
		back.setOnMouseEntered(event -> back.setOpacity(0.8));
		back.setOnMouseExited(event -> back.setOpacity(1.5));

		deleteButton.setCursor(Cursor.HAND);
		deleteButton.setOnMouseEntered(event -> deleteButton.setOpacity(0.8));
		deleteButton.setOnMouseExited(event -> deleteButton.setOpacity(1.5));

		home.setCursor(Cursor.HAND);
		home.setOnMouseEntered(event -> home.setOpacity(0.8));
		home.setOnMouseExited(event -> home.setOpacity(1.5));

		updateButton.setCursor(Cursor.HAND);
		updateButton.setOnMouseEntered(event -> updateButton.setOpacity(0.8));
		updateButton.setOnMouseExited(event -> updateButton.setOpacity(1.5));

		toggleMusicButton.setCursor(Cursor.HAND);
		toggleMusicButton.setOnMouseEntered(event -> toggleMusicButton.setOpacity(0.8));
		toggleMusicButton.setOnMouseExited(event -> toggleMusicButton.setOpacity(1.5));

	}

	private void adjustTextAreaHeight(TextArea textArea, double width, double initialHeight) {
		textArea.setPrefWidth(width); // Ensure the preferred width matches SceneBuilder settings
		textArea.setMinHeight(initialHeight); // Set the minimum height to the initial height
		textArea.setPrefHeight(initialHeight); // Start with the initial height
		textArea.textProperty().addListener((observable, oldValue, newValue) -> {
			// Basic calculation to adjust height; refine as necessary
			double textHeight = computeTextHeight(textArea.getFont(), textArea.getText(), textArea.getPrefWidth());
			double newHeight = Math.max(initialHeight, textHeight + 20); // Add some padding
			textArea.setPrefHeight(newHeight);
		});
	}

	/**
	 * Computes the estimated height of text given a specific font and width. This
	 * is a simplified estimation and might need adjustments.
	 */
	private double computeTextHeight(Font font, String text, double width) {
		Text helper = new Text();
		helper.setText(text);
		helper.setFont(font);
		helper.setWrappingWidth(width);
		return helper.getLayoutBounds().getHeight();
	}

	@FXML
	void handleDeleteQuestion(MouseEvent event) {
		if (correctAnswer < 0) {
			showAlert("Deletion Error", "No question selected for deletion.");
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this question?",
				ButtonType.YES, ButtonType.CANCEL);
		alert.setHeaderText("Confirm Deletion");
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				boolean deletionSuccess = SysData.getInstance().deleteQuestion(questionId); // Assuming you have a
																							// correct mapping to
																							// ID.
				if (deletionSuccess) {
					showAlert("Success", "Question deleted successfully.");
					// Optionally, navigate back or refresh the view.
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
						Parent players = loader.load();

						Stage newStage = new Stage();
						newStage.setScene(new Scene(players));
						newStage.show();
						newStage.setTitle("Questions List");
						// Close the current stage
						Stage currentStage = (Stage) deleteButton.getScene().getWindow();
						currentStage.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					showAlert("Error", "Failed to delete the question.");
				}
			}
		});
	}

	@FXML
	void handleUpdateButton(MouseEvent event) {
		try {

			// Load the FXML for UpdateQuestionController
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updateQuestionPage.fxml"));

			Parent root = loader.load();

			// Get the controller and pass the question details
			UpdateQuestionController updateQuestionController = loader.getController();
			System.out.println("navigate " + questionId);
			Question question = SysData.getInstance().getQuestionByID(questionId); // Retrieve the question based on ID
			updateQuestionController.setQuestion(question); // Pass the question to the update controller

			// Set up and show the new stage
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Update Question");
			stage.show();

			// Optionally, close the current window
			Stage currentStage = (Stage) updateButton.getScene().getWindow();
			currentStage.close();
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception, maybe show an error message
		}

	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

}
