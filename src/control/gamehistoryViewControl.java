package control;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Question;
import model.game;

public class gamehistoryViewControl {
	
	    @FXML
	    private TextField gameid;

	    @FXML
	    private TextField winner; 

	    
	    @FXML
	    private TextField level; 

	    @FXML
	    private Button back;

	    // Method to set the game and its details
	    public void setGame(game game) {
	    	gameid.setText(String.valueOf(game.getId()));
	        level.setText("level: " + game.getLevel()); 
	        winner.setText("winner: " + game.getWinner());
	 
	    }
	    @FXML
	    void handleBack(ActionEvent event) {
	        try {
	            // Load QuestionsList.fxml
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HistoryBoard2.fxml"));
	            Parent root = loader.load();

	            // Reuse the existing stage
	            Stage stage = (Stage) back.getScene().getWindow();
	            stage.setScene(new Scene(root));
	            stage.setTitle("game history List");
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error loading historyboard2.fxml: " + e.getMessage());
	        }
	    }


}
