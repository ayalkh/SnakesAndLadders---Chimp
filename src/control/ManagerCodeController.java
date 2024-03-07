package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerCodeController {

	@FXML
	private ImageView homeButton;
	@FXML
	private ImageView enterButton;

	@FXML
	private Label labelButton;

	@FXML
	private PasswordField passCode;
	@FXML
	private ImageView backButton;

	@FXML
	void clickOnEnterButton(MouseEvent event) {
		String password = passCode.getText();
		if (password.equals("123")) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questions.fxml"));
				Parent root = loader.load();

				// Create a new stage for the new scene
				Stage stage = new Stage();
				stage.setScene(new Scene(root, 756, 548));
				stage.setTitle("Question");

				// Optional: if you want to block interaction with other windows
				stage.initModality(Modality.APPLICATION_MODAL);

				stage.show();

				// Closing the current window
				Stage currentStage = (Stage) enterButton.getScene().getWindow();
				currentStage.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else {
			Alerts.alertBox(Alert.AlertType.ERROR, "Invalid Code", "Wrong Code Entered",
					"The code you entered is incorrect. Please try again.");
			passCode.setText("");
		}
	}

	@FXML
	void handleBackButton(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent root = loader.load();

			// Create a new stage for the new scene
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 1192, 680));
			stage.setTitle("SNAKES & LADDERS GAME");

			// Optional: if you want to block interaction with other windows
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.show();

			// Closing the current window
			Stage currentStage = (Stage) enterButton.getScene().getWindow();
			currentStage.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		assert enterButton != null : "fx:id=\"enterButton\" was not injected: check your FXML file 'ManagerCode.fxml'.";
		assert labelButton != null : "fx:id=\"labelButton\" was not injected: check your FXML file 'ManagerCode.fxml'.";
		assert passCode != null : "fx:id=\"passCode\" was not injected: check your FXML file 'ManagerCode.fxml'.";
		enterButton.setCursor(Cursor.HAND);
		enterButton.setOnMouseEntered(event -> enterButton.setOpacity(0.8));
		enterButton.setOnMouseExited(event -> enterButton.setOpacity(1.5));

		labelButton.setCursor(Cursor.HAND);
		labelButton.setOnMouseEntered(event -> enterButton.setOpacity(0.8));
		labelButton.setOnMouseExited(event -> enterButton.setOpacity(1.5));

		homeButton.setCursor(Cursor.HAND);
		homeButton.setOnMouseEntered(event -> homeButton.setOpacity(0.8));
		homeButton.setOnMouseExited(event -> homeButton.setOpacity(1.5));

		backButton.setCursor(Cursor.HAND);
		backButton.setOnMouseEntered(event -> backButton.setOpacity(0.8));
		backButton.setOnMouseExited(event -> backButton.setOpacity(1.5));

	}

}
