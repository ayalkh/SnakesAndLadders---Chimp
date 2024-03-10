package control;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Question;

public class QuestionsController {

	@FXML
	private ListView<String> questionsListView;

	private List<Question> questions;
	@FXML
	private ImageView addButton;

	@FXML
	private ImageView back;
	@FXML
	private ImageView homeButton;
	@FXML
	ImageView toggleMusicButton;

	@FXML
	void handleToggleMusic(MouseEvent event) {
		BackgroundMusicPlayer.getInstance().toggleMusic();
	}

	@FXML
	public void initialize() {

		loadQuestions();
		populateListView();

		toggleMusicButton.setCursor(Cursor.HAND);
		toggleMusicButton.setOnMouseEntered(event -> toggleMusicButton.setOpacity(0.8));
		toggleMusicButton.setOnMouseExited(event -> toggleMusicButton.setOpacity(1.5));

		addButton.setCursor(Cursor.HAND);
		addButton.setOnMouseEntered(event -> addButton.setOpacity(0.8));
		addButton.setOnMouseExited(event -> addButton.setOpacity(1.5));

		back.setCursor(Cursor.HAND);
		back.setOnMouseEntered(event -> back.setOpacity(0.8));
		back.setOnMouseExited(event -> back.setOpacity(1.5));

		homeButton.setCursor(Cursor.HAND);
		homeButton.setOnMouseEntered(event -> homeButton.setOpacity(0.8));
		homeButton.setOnMouseExited(event -> homeButton.setOpacity(1.5));

	}

	private void loadQuestions() {
		questions = new ArrayList<>();
		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader("Questions.json")) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray jsonQuestions = (JSONArray) jsonObject.get("questions");

			for (Object o : jsonQuestions) {
				JSONObject jsonQuestion = (JSONObject) o;
				Question question = new Question(jsonQuestion);

				questions.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateListView() {
		for (Question q : questions) {
			questionsListView.getItems().add(q.getQuestionID() + ": " + q.getQuestion());
		}

		questionsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedIdStr = newValue.split(":")[0].trim(); // Extract ID
			int selectedId = Integer.parseInt(selectedIdStr);
			openQuestionView(selectedId);
		});
	}

	private void openQuestionView(int questionId) {
		// Fetch the question by ID
		Question selectedQuestion = getQuestionById(questionId);
		System.out.println("Attempting to open question view for ID: " + questionId); // Debugging line
		System.out.println("Attempting to open question view for ID from class: " + selectedQuestion.getQuestionID()); // Debugging
																														// line

		if (selectedQuestion != null) {
			try {
				// Load the QuestionView FXML file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView2.fxml"));
				Parent root = loader.load();

				// Get the controller and set the question
				ViewQuestionDetailsController controller = loader.getController();
				controller.setQuestionId(questionId);
				controller.setQuestion(selectedQuestion);

				// Use the existing stage instead of creating a new one
				Stage currentStage = (Stage) questionsListView.getScene().getWindow();
				currentStage.setTitle("Question Details");
				currentStage.setScene(new Scene(root));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error opening question view: " + e.getMessage());
			}
		} else {
			System.out.println("Question not found with ID: " + questionId);
		}
	}

	private Question getQuestionById(int id) {
		for (Question q : questions) {
			if (q.getQuestionID() == id) {
				return q;
			}
		}
		return null;
	}

	@FXML
	private void handleAddQuestion() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addQuestionPage.fxml"));
			Parent players = loader.load();

			Stage newStage = new Stage();
			newStage.setScene(new Scene(players));
			newStage.show();

			// Close the current stage
			Stage currentStage = (Stage) addButton.getScene().getWindow();
			currentStage.setTitle("ADD Question");
			currentStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onQuestionAdded(Question question) {
		// Add the question to the local list and update the ListView
		questions.add(question);
		populateListView();
	}

	@FXML
	void handleBack() {
		navigateTo("/popUpMessages/ManagerCode.fxml", "Manager Page LOG-IN");
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

	@FXML
	void handleHome() {
		navigateTo("/view/WelcomePage.fxml", "SNAKES & LADDERS GAME !!");
	}

}
