package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Question;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionListController {

    @FXML
    private ListView<String> questionListView;

    private List<Question> questions;

    @FXML
    public void initialize() {
        loadQuestions();
        populateListView();
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("Question.json")) {
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
            questionListView.getItems().add(q.getQuestion());
        }

        questionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            openQuestionView(newValue);
        });
    }

    private void openQuestionView(String questionTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView.fxml"));
            Parent root = loader.load();

            QuestionViewControl controller = loader.getController();
            controller.setQuestion(getQuestionByTitle(questionTitle));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Question getQuestionByTitle(String title) {
        for (Question q : questions) {
            if (q.getQuestion().equals(title)) {
                return q;
            }
        }
        return null;
    }
}
