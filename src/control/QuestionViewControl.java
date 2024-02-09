package control;

import model.Question;


import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class QuestionViewControl {

    @FXML
    private TextField questionText;

    @FXML
    private TextField answer1Tex; 

    @FXML
    private TextField answer2Tex;

    @FXML
    private TextField answer3Tex;

    @FXML
    private TextField answer4Tex;

    @FXML
    private Label difficultyLabel; 

    @FXML
    private Label teamLabel; 
    @FXML
    private Button back;

    // Method to set the question and its details
    public void setQuestion(Question question) {
    	questionText.setText(question.getQuestion());
        List<String> answers = question.getAnswers();

        answer1Tex.setText(answers.size() > 0 ? answers.get(0) : "");
        answer2Tex.setText(answers.size() > 1 ? answers.get(1) : "");
        answer3Tex.setText(answers.size() > 2 ? answers.get(2) : "");
        answer4Tex.setText(answers.size() > 3 ? answers.get(3) : "");

        difficultyLabel.setText("Difficulty: " + question.getLevel()); // Set difficulty
        teamLabel.setText("Team: " + question.getTeam()); // Set team
        
 
    }
    @FXML
    void handleBack(ActionEvent event) {
        try {
            // Load QuestionsList.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionsList.fxml"));
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

   
  }
