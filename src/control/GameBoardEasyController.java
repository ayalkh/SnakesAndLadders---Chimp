
package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.EasyGame;
import model.Snake;

public class GameBoardEasyController {
	// Example: Define FXML elements that you want to interact with
	@FXML
	private Label playerNameLabel;
	@FXML
	private TextField playerNameTextField;
	@FXML
	private ImageView stopwatchImageView;
	@FXML
	private Label timerLabel;

	 private EasyGame easyGame; // The game logic
	 private Button[][] buttonMatrix;
	// 49buttons
	@FXML
	private Button i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6, i2j0, i2j1, i2j2,
			i2j3, i2j4, i2j5, i2j6, i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6, i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6,
			i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6, i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6;

	
	  public void initialize() {
	        easyGame = new EasyGame(); // Initialize the game
	        buttonMatrix = new Button[][]{
	            {i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6},
	            {i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6},
	            {i2j0, i2j1, i2j2,i2j3, i2j4, i2j5, i2j6},
	            {i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6},
	            {i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6},
	            {i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6},
	            {i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6}
	        };
	        updateBoardWithSnakes();
	    }
	  
	  private void updateBoardWithSnakes() {
	        for (int i = 0; i < easyGame.getSize(); i++) {
	            for (int j = 0; j < easyGame.getSize(); j++) {
	                if (easyGame.getBoard()[i][j] instanceof Snake) {
	                    Snake snake = (Snake) easyGame.getBoard()[i][j];
	                    int headRow = (snake.getStartPosition() - 1) / easyGame.getSize();
	                    int headCol = (snake.getStartPosition() - 1) % easyGame.getSize();
	                    int tailRow = (snake.getEndPosition() - 1) / easyGame.getSize();
	                    int tailCol = (snake.getEndPosition() - 1) % easyGame.getSize();

	                    displaySnakeOnButton(buttonMatrix[headRow][headCol], "head", snake);
	                    displaySnakeOnButton(buttonMatrix[tailRow][tailCol], "tail", snake);
	                }
	            }
	        }
	    }
	    private void displaySnakeOnButton(Button button, String part, Snake snake) {
	        // Determine the correct image based on the snake's color and the part (head/tail)
	        String snakeColor = snake.getColor().toLowerCase();
	        String imageFileName = "/images/" + snakeColor + "Snake" + capitalize(part) + ".png"; // Assuming you have images named accordingly

	        Image snakeImage = new Image(imageFileName);
	        button.setGraphic(new ImageView(snakeImage));
	    }

	    private String capitalize(String input) {
	        if (input == null || input.isEmpty()) {
	            return input;
	        }
	        return input.substring(0, 1).toUpperCase() + input.substring(1);
	    }
}