package control;

import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

public class addQuestiosPageController {
	  @FXML
	    private TextField questionText;

	    @FXML
	    private TextField answer1Tex;

	    @FXML
	    private TextField answer3Tex;

	    @FXML
	    private TextField answer2Tex;

	    @FXML
	    private TextField answer4Tex;

	    @FXML
	    private Button addButton;
	    @FXML
	    private TextField level;
	    @FXML
	    private TextField CorrAnswer;
	    @FXML
	    private Button back;
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
	    void handleAdd(ActionEvent event) {
	        try {
	            // Parse the level and correct answer index from the text fields
	            int levelValue = Integer.parseInt(level.getText().trim());
	            int correctAnswerIndex = Integer.parseInt(CorrAnswer.getText().trim()) - 1; // Assuming the answers are 1-indexed in the UI

	            // Validate the correct answer index
	            if (correctAnswerIndex < 0 || correctAnswerIndex >= 4) {
	                // Handle error - show an error message to the user
	                return;
	            }

	            // Create a new Question object with data from the form
	            Question newQuestion = new Question(
	                SysData.getNextQuestionID(),
	                questionText.getText(),
	                Arrays.asList(answer1Tex.getText(), answer2Tex.getText(), answer3Tex.getText(), answer4Tex.getText()),
	                correctAnswerIndex, // Correct answer index (0-indexed)
	                levelValue, // Level
	                "Team Name" // You need to determine how to set the team
	            );

	            // Add the new question to SysData
	            if (SysData.getInstance().addQuestion(newQuestion)) {
	                System.out.println("added successfully");
	                if (questionAddedCallback != null) {
	                    questionAddedCallback.onQuestionAdded(newQuestion);
	                }
	            } else {
	                System.out.println("add failed");
	            }

	            // Close the window after adding the question
	            Stage stage = (Stage) addButton.getScene().getWindow();
	            stage.close();
	        } catch (NumberFormatException e) {
	            // Handle error - show an error message to the user
	        }
	        
	    }
	    private Stage currentStage;

	    public void setCurrentStage(Stage stage) {
	        this.currentStage = stage;
	    }

	    @FXML
	    void handleBack(ActionEvent event) {
	        try {
	            // Check if the currentStage is set
	            if (currentStage == null) {
	                System.out.println("Current stage is not set.");
	                return;
	            }
	         
	            // Load QuestionsList.fxml
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionsList.fxml"));
	            Parent root = loader.load();

	            // Set the new scene to the current stage
	            if (currentStage != null) {
	                currentStage.setScene(new Scene(root));
	                currentStage.setTitle("Questions List");
	            } else {
	                System.out.println("Current stage is null.");
	            }
	            currentStage.show();
	            Stage stage = (Stage) back.getScene().getWindow();
	            stage.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error loading QuestionsList.fxml: " + e.getMessage());
	        }
	    }


}
