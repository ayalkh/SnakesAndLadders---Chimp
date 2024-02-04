package control;

import model.Question;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

}
