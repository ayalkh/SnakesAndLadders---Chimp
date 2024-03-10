package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		BackgroundMusicPlayer.getInstance().playMusic("backGroungMusic.mp3");
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
			Scene scene = new Scene(root, 1192, 680);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("SNAKES & LADDERS GAME");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		launch(args);
	}
}

//music credit : Run Amok by Kevin MacLeod | https://incompetech.com/
//Music promoted by https://www.chosic.com/free-music/all/
//Creative Commons CC BY 3.0
//https://creativecommons.org/licenses/by/3.0/