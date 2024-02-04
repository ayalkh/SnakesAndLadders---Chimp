package control; // Change this to your actual package name

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomePageController extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/WelcomePage.fxml"));
			Parent root = loader.load();

			WelcomePageController controller = loader.getController();
			// You can perform additional setup on the controller if needed

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