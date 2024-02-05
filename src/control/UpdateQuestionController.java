package control;

import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Question;
import model.SysData;

public class UpdateQuestionController {

    @FXML
    private Button back;

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
    private TextField level;

    @FXML
    private TextField corrAnswer;  // Correctly declare the correct answer TextField

    @FXML
    private Button editButton;
    @FXML
    private TextField team;

    private Question currentQuestion;  // Declare the currentQuestion

    // Other methods...

    @FXML
    void handleEdit(ActionEvent event) {
        try {
            // Validate inputs here if needed

            // Update the currentQuestion object with new values
            currentQuestion.setQuestion(questionText.getText());
            currentQuestion.setAnswers(Arrays.asList(
                answer1Tex.getText(), 
                answer2Tex.getText(), 
                answer3Tex.getText(), 
                answer4Tex.getText()
            ));
            currentQuestion.setLevel(Integer.parseInt(level.getText()));
            currentQuestion.setCorrectAnswer(Integer.parseInt(corrAnswer.getText()) - 1); // Convert back to index

            // Call updateQuestion method from SysData
            boolean isUpdated = SysData.getInstance().updateQuestion(currentQuestion.getQuestionID(), currentQuestion);
            if (isUpdated) {
                System.out.println("Question updated successfully.");
                // Optionally close the window or indicate success to the user
                // For example: ((Stage) editButton.getScene().getWindow()).close();
            } else {
                System.out.println("Failed to update the question.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating question: " + e.getMessage());
        }
    }

    public void setQuestion(Question question) {
    	questionText.setText(question.getQuestion());
        List<String> answers = question.getAnswers();

        answer1Tex.setText(answers.size() > 0 ? answers.get(0) : "");
        answer2Tex.setText(answers.size() > 1 ? answers.get(1) : "");
        answer3Tex.setText(answers.size() > 2 ? answers.get(2) : "");
        answer4Tex.setText(answers.size() > 3 ? answers.get(3) : "");

        level.setText("Difficulty: " + question.getLevel()); // Set difficulty
        corrAnswer.setText("correct answer: " + question.getCorrectAnswer()); 
        team.setText("team: " + question.getTeam());
 
    }
    @FXML
    void handleBack(ActionEvent event) {

    }
}
