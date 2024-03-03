package control;

import java.net.URL;
import java.util.ResourceBundle;

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
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
	private ImageView homeButton1;

	@FXML
	void handleHomeButton(MouseEvent event) {
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
		assert homeButton1 != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'HistoryBoard.fxml'.";
		homeButton1.setCursor(Cursor.HAND);

		// Set opacity effects for 'start'
		homeButton1.setOnMouseEntered(event -> homeButton1.setOpacity(0.8));
		homeButton1.setOnMouseExited(event -> homeButton1.setOpacity(1.5));
	}

}
