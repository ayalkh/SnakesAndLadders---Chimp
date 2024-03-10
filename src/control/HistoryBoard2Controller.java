package control;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Question;
import model.SysData;
import model.game;

public class HistoryBoard2Controller {
	@FXML
	private ListView<String> gamesListView;

	private List<game> games;
	@FXML
	private Button addButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button deleteButton;
	@FXML
	private Button back;

	@FXML
	public void initialize() {
		loadGames();

		populateListView();
	}

	private void loadGames() {
		games = new ArrayList<>();
		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader("Games.json")) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray jsongames = (JSONArray) jsonObject.get("games");

			for (Object o : jsongames) {
				JSONObject jsongame = (JSONObject) o;
				game game = new game(jsongame);

				games.add(game);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateListView() {
		for (game g : games) {
			gamesListView.getItems().add(g.getId() + ": winner:" + g.getWinner());
		}

		gamesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			String selectedIdStr = newValue.split(":")[0].trim(); // Extract ID
			int selectedId = Integer.parseInt(selectedIdStr);
			opengameView(selectedId);
		});
	}

	private void opengameView(int gameid) {
		// Fetch the question by ID
		game selectedgame = getgameById(gameid);
		if (selectedgame != null) {
			try {
				// Load the QuestionView FXML file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gamehistoryView.fxml"));
				Parent root = loader.load();

				// Get the controller and set the question
				gamehistoryViewControl controller = loader.getController();
				controller.setGame(selectedgame);
				// Use the existing stage instead of creating a new one
				Stage currentStage = (Stage) gamesListView.getScene().getWindow();
				currentStage.setTitle("game Details");
				currentStage.setScene(new Scene(root));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error opening question view: " + e.getMessage());
			}
		} else {
			System.out.println("Question not found with ID: " + gameid);
		}
	}

	private game getgameById(int id) {
		for (game q : games) {
			if (q.getId() == id) {
				return q;
			}
		}
		return null;
	}

	
	

	@FXML
	void handleBack(ActionEvent event) {
		try {
			// Load the WelcomePage FXML file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomePage.fxml"));
			Parent root = loader.load();

			// Use the existing stage instead of creating a new one
			Stage currentStage = (Stage) back.getScene().getWindow();
			currentStage.setTitle("Welcome Page");
			currentStage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading WelcomePage.fxml: " + e.getMessage());
		}
	}

}


