package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Utilities {
	private Parent root;
	private static Utilities instance;

	public static Utilities getInstance() {
		if (instance == null)
			instance = new Utilities();
		return instance;
	}

	public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
		root = FXMLLoader.load(getClass().getResource(fxmlFile));
		Scene adminScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if (window != null) {
			window.setScene(adminScene);
			window.show();
		}

	}

	public void switchScene2(Stage window, String fxmlFile) throws IOException {
		root = FXMLLoader.load(getClass().getResource(fxmlFile));
		Scene adminScene = new Scene(root);
		if (window != null) {
			window.setScene(adminScene);
			window.show();
		}

	}

	public FXMLLoader getSceneController(String fxmlFile) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlFile));
		return loader;
	}

	public void loadScene(ActionEvent event, FXMLLoader loader) throws IOException {
		root = loader.load();
		Scene adminScene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(adminScene);
		stage.show();
	}

	public String[] getQuestionLevels() {
		String[] levels = { "Easy", "Meduim", "Hard" };
		return levels;
	}

	public ArrayList<String> getTeams() {
		ArrayList<String> teams = new ArrayList<>(Arrays.asList("Spider", "Husky", "Chimps", "Giraffe", "Tiger"));
		return teams;
	}

	// return true if string has only white spaces or less than 2 characters
	public boolean containsWhiteSpacesOnly(String string) {
		if (string.trim().length() > 0)
			return false;
		return true;
	}

	public int convertLeveltoNumber(String level) {
		String[] levels = getQuestionLevels();
		if (levels[0].equals(level))
			return 1;
		else if (levels[1].equals(level))
			return 2;
		else
			return 3;
	}

	
	public boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null)
			return false;

		final int length = searchStr.length();
		if (length == 0)
			return true;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return true;
		}
		return false;
	}

	// Receives path and size, returns an image in the size sent
	public static ImageView getAvatar(String s, int x, int y) {
		Image avatar = new Image(s);
		ImageView imageAvatar = new ImageView(avatar);
		imageAvatar.setFitWidth(x);
		imageAvatar.setFitHeight(y);
		return imageAvatar;
	}

}
