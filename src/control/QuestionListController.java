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
    private ListView<String> questionsListView;

    private List<Question> questions;

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
        Question selectedQuestion = getQuestionById(questionId);
        if (selectedQuestion != null) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView.fxml"));
                Parent root = loader.load();

                QuestionViewControl controller = loader.getController();

                controller.setQuestion(selectedQuestion);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
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
}
