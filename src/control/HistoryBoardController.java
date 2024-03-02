package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HistoryBoardController {
	@FXML
	private ImageView homeButton;

	@FXML
	void navigateToHomeInClick(MouseEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent x = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(x));
			stage.close();

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void initialize() {
		assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'HistoryBoard.fxml'.";
		homeButton.setCursor(Cursor.HAND);

		// Set opacity effects for 'start'
		homeButton.setOnMouseEntered(event -> homeButton.setOpacity(0.8));
		homeButton.setOnMouseExited(event -> homeButton.setOpacity(1.5));
	}

}