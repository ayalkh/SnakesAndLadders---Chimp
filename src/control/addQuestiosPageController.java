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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

public class addQuestiosPageController {
	@FXML
	private TextArea questionText;

	@FXML
	private TextArea answer1Tex;

	@FXML
	private TextArea answer2Tex;

	@FXML
	private TextArea answer3Tex;

	@FXML
	private TextArea answer4Tex;

	@FXML
	private TextField level;
	@FXML
	private TextField CorrAnswer;

	@FXML
	private ImageView back;
	@FXML
	private ImageView addButton;

	@FXML
	private ComboBox<Integer> diffLevel;

	@FXML
	private ImageView home;

	@FXML
	private ComboBox<Integer> CorrectAnswer;

	// At the top of the addQuestiosPageController file
	@FunctionalInterface
	interface QuestionAddedCallback {
		void onQuestionAdded(Question question);
	}

	// Inside addQuestiosPageController class
	private QuestionAddedCallback questionAddedCallback;

	public void setQuestionAddedCallback(QuestionAddedCallback callback) {
		this.questionAddedCallback = callback;
	}

	@FXML
	void handleAdd(MouseEvent event) {
		// Validate TextAreas for empty values
		if (questionText.getText().trim().isEmpty() || answer1Tex.getText().trim().isEmpty()
				|| answer2Tex.getText().trim().isEmpty() || answer3Tex.getText().trim().isEmpty()
				|| answer4Tex.getText().trim().isEmpty()) {
			showAlert("Error", "All fields are required.",
					"Please ensure all question and answer fields are filled out.");
			return;
		}

		// Validate ComboBoxes for selected values
		if (diffLevel.getValue() == null || CorrectAnswer.getValue() == null) {
			showAlert("Error", "Selection Required", "Please select a difficulty level and the correct answer.");
			return;
		}

		// Parsing and creating a new Question object after validation
		try {
			// Assuming diffLevel and CorrectAnswer are ComboBox<Integer> and already have a
			// selected value
			int levelValue = diffLevel.getValue();
			int correctAnswerIndex = CorrectAnswer.getValue();
			String teamName = "Chimp";

			// Create a new Question object with data from the form
			Question newQuestion = new Question(SysData.getNextQuestionID(), questionText.getText(),
					Arrays.asList(answer1Tex.getText(), answer2Tex.getText(), answer3Tex.getText(),
							answer4Tex.getText()),
					correctAnswerIndex, // Correct answer index (assuming 0-indexed)
					levelValue, // Level
					teamName // Placeholder for team name
			);

			// Add the new question to SysData
			if (SysData.getInstance().addQuestion(newQuestion)) {
				System.out.println("Question added successfully");
//				// Optionally: Close the window or clear the form
//				Stage stage = (Stage) addButton.getScene().getWindow();
//				stage.close();
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
					Parent players = loader.load();

					Stage newStage = new Stage();
					newStage.setTitle("Questions List ");
					newStage.setScene(new Scene(players));
					newStage.show();

					// Close the current stage
					Stage currentStage = (Stage) addButton.getScene().getWindow();
					currentStage.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Failed to add the question");
				// Optionally: Show an error message to the user
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

	private Stage currentStage;

	public void setCurrentStage(Stage stage) {
		this.currentStage = stage;
	}

	@FXML
	void handleBack(MouseEvent event) {
		// navigateTo("/view/Questions.fxml", "Welcome Page");

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
			Parent players = loader.load();

			Stage newStage = new Stage();
			newStage.setTitle("Questions List ");
			newStage.setScene(new Scene(players));
			newStage.show();

			// Close the current stage
			Stage currentStage = (Stage) addButton.getScene().getWindow();
			currentStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {

		diffLevel.getItems().addAll(1, 2, 3);
		CorrectAnswer.getItems().addAll(1, 2, 3, 4);
		assert CorrAnswer != null
				: "fx:id=\"CorrAnswer\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert answer1Tex != null
				: "fx:id=\"answer1Tex\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert answer2Tex != null
				: "fx:id=\"answer2Tex\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert answer3Tex != null
				: "fx:id=\"answer3Tex\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert answer4Tex != null
				: "fx:id=\"answer4Tex\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert diffLevel != null : "fx:id=\"diffLevel\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert level != null : "fx:id=\"level\" was not injected: check your FXML file 'addQuestionPage.fxml'.";
		assert questionText != null
				: "fx:id=\"questionText\" was not injected: check your FXML file 'addQuestionPage.fxml'.";

		back.setCursor(Cursor.HAND);
		back.setOnMouseEntered(event -> back.setOpacity(0.8));
		back.setOnMouseExited(event -> back.setOpacity(1.5));

		home.setCursor(Cursor.HAND);
		home.setOnMouseEntered(event -> home.setOpacity(0.8));
		home.setOnMouseExited(event -> home.setOpacity(1.5));

		addButton.setCursor(Cursor.HAND);
		addButton.setOnMouseEntered(event -> addButton.setOpacity(0.8));
		addButton.setOnMouseExited(event -> addButton.setOpacity(1.5));

	}

	@FXML
	void handleHomeButton(MouseEvent event) {

		navigateTo("/view/WelcomePage.fxml", "SNAKES & LADDERS GAME");

	}

	void navigateTo(String fxmlPath, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle(title);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading " + fxmlPath + ": " + e.getMessage());
		}
	}

}
