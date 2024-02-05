package control; // Change this to your actual package name

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomePageController extends Application {
    @FXML
    private Button viewQuestion;
    @FXML
    private void handleViewQuestionAction(ActionEvent event) {
        try {
            // Load the QuestionsList view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionsList.fxml"));
            Parent questionsListView = loader.load();

            // Get the current stage using the event's source
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the scene to the questionsList view
            stage.setScene(new Scene(questionsListView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent root = loader.load();


			Scene scene = new Scene(root, 750, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}