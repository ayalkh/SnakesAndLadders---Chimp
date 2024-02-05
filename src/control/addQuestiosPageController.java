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
	              System.out.println("added successfuly");
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
	    @FXML
	    void handleBack(ActionEvent event) {
	        try {
	            // Load QuestionsList.fxml
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionsList.fxml")); // Adjust the path to your FXML file if necessary
	            Parent root = loader.load();

	            // Get the current stage (window)
	            Stage stage = (Stage) back.getScene().getWindow(); // 'back' is the fx:id of the back button

	            // Set the new scene to the stage
	            stage.setScene(new Scene(root));
	            stage.setTitle("Questions List"); // Optionally set a title for the window
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error loading QuestionsList.fxml: " + e.getMessage());
	        }
	    }


}
