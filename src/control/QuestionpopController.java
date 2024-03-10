package control;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Question;

public class QuestionpopController {

	@FXML
	private Button answer1Tex;

	@FXML
	private Button answer2Tex;

	@FXML
	private Button answer3Tex;

	@FXML
	private Button answer4Tex;

	@FXML
	private Button continueButton;
	@FXML
	private Label difficultyLabel;

	@FXML
	private Label teamLabel;

	@FXML
	private TextArea questionText;
	private Question currentquestion;
	@FXML
	ImageView toggleMusicButton;

	@FXML
	void handleToggleMusic(MouseEvent event) {
		BackgroundMusicPlayer.getInstance().toggleMusic();
	}

	@FXML
	void initialize() {

		toggleMusicButton.setCursor(Cursor.HAND);
		toggleMusicButton.setOnMouseEntered(event -> toggleMusicButton.setOpacity(0.8));
		toggleMusicButton.setOnMouseExited(event -> toggleMusicButton.setOpacity(1.5));
	}

	private boolean correct;

	@FXML
	public void checkcorrectanswer(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		System.out.println("clicked the answer");
		String selectedAnswer = clickedButton.getText();
		System.out.println("got the selected answer");
		if (currentquestion.getCorrectAnswer() == selectedanswerindex(selectedAnswer) + 1) {
			// Correct answer
			System.out.println("checked the selected answer");
			clickedButton.setStyle("-fx-background-color: green;");
			setCorrect(true);
		} else {
			// Incorrect answer
			System.out.println("checked the selected answer");
			clickedButton.setStyle("-fx-background-color: red;");
			setCorrect(false);
		}
		// Show the continue button
		continueButton.setVisible(true);

		// Disable all answer buttons
		answer1Tex.setDisable(true);
		answer2Tex.setDisable(true);
		answer3Tex.setDisable(true);
		answer4Tex.setDisable(true);
	}

	public void setQuestion(Question question) {

		questionText.setText(question.getQuestion());
		List<String> answers = question.getAnswers();
		currentquestion = question;

		answer1Tex.setText(answers.size() > 0 ? answers.get(0) : "");
		answer2Tex.setText(answers.size() > 1 ? answers.get(1) : "");
		answer3Tex.setText(answers.size() > 2 ? answers.get(2) : "");
		answer4Tex.setText(answers.size() > 3 ? answers.get(3) : "");

		difficultyLabel.setText("Difficulty: " + question.getLevel()); // Set difficulty
		teamLabel.setText("Team: " + question.getTeam()); // Set team

	}

	@FXML
	void handleContinue() {
		// Close the current window
		Stage stage = (Stage) continueButton.getScene().getWindow();
		stage.close();
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	int selectedanswerindex(String answer) {
		int i = 0;
		for (i = 0; i < 4; i++) {
			if (currentquestion.getAnswers().get(i) == answer) {
				return i;
			}
		}
		return 0;

	}
}
