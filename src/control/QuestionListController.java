package control;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

public class QuestionListController {

	@FXML
	private ListView<String> questionsListView;

	private List<Question> questions;
	@FXML
	private Button addButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button deleteButton;
	@FXML
	private Button back;

	@FXML
	public void initialize() {
		loadQuestions();

		populateListView();
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
		if (selectedQuestion != null) {
			try {
				// Load the QuestionView FXML file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView.fxml"));
				Parent root = loader.load();

				// Get the controller and set the question
				QuestionViewControl controller = loader.getController();
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
	void handleDeleteQuestion(ActionEvent event) {
		// Check if a question is selected in the ListView
		String selectedItem = questionsListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null && !selectedItem.isEmpty()) {
			// Extract the question ID from the selected item
			int questionId = Integer.parseInt(selectedItem.split(":")[0].trim());

			// Call deleteQuestion method from SysData
			boolean isDeleted = SysData.getInstance().deleteQuestion(questionId);
			if (isDeleted) {
				System.out.println("Question deleted successfully.");

				// Update the ListView after deletion
				loadQuestions();
				populateListView();
			} else {
				System.out.println("Failed to delete the question.");
			}
		} else {
			System.out.println("No question selected.");
		}
	}

	@FXML
	private void handleAddQuestion(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addQuestionPage.fxml"));
			Parent root = loader.load();

			addQuestiosPageController controller = loader.getController();
			if (controller != null) {
				controller.setQuestionAddedCallback(this::onQuestionAdded);

				// Pass the current stage to the addQuestionsPageController
				Stage currentStage = (Stage) addButton.getScene().getWindow();

				controller.setCurrentStage(currentStage);
			}

			Stage addQuestionStage = new Stage();
			addQuestionStage.setScene(new Scene(root));
			addQuestionStage.setTitle("ADD Question");
			addQuestionStage.show();
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
	void handleBack(ActionEvent event) {
		try {
			// Load the WelcomePage FXML file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent root = loader.load();

			// Use the existing stage instead of creating a new one
			Stage currentStage = (Stage) back.getScene().getWindow();
			currentStage.setTitle("Welcome Page");
			currentStage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading WelcomePage.fxml: " + e.getMessage());
		}
	}

}
