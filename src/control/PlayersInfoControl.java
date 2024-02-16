
package control;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class PlayersInfoControl {

	@FXML
	private ResourceBundle resources;
	private String selectedValue;
	@FXML
	private URL location;
	@FXML
	private ComboBox<String> comboBox;
	private int numberOfPlayers;

	@FXML
	private ImageView redObject;

	@FXML
	private ImageView greenObject;

	public void setNumberOfPlayers(int number) {
		this.numberOfPlayers = number;
		// You can now use numberOfPlayers in this controller
	}

	@FXML
	void clickRedObject(MouseEvent event) {

	}

	@FXML
	void initialize() {
		comboBox.setItems(FXCollections.observableArrayList("easy", "medium", "hard"));

		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// newValue holds the newly selected item
				selectedValue = newValue;
				saveSelectedValue(newValue);
			}
		});
		// Change cursor when mouse enters the ImageView
		redObject.setOnMouseEntered(event -> redObject.setCursor(Cursor.HAND));

		// Optionally, set cursor back to default when mouse exits the ImageView
		redObject.setOnMouseExited(event -> redObject.setCursor(Cursor.DEFAULT));

		redObject.setOnMouseClicked(event -> {
			// Set the visibility to false when the ImageView is clicked
			redObject.setVisible(false);
		});

		greenObject.setOnMouseClicked(event -> {
			// Set the visibility to false when the ImageView is clicked
			greenObject.setVisible(false);
		});

	}

	private void saveSelectedValue(String value) {
		// Implement your saving logic here
		System.out.println("Selected value: " + value);
	}
}
