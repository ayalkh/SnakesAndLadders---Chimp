package control;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Dice;
import model.EasyGame;
import model.GameLevel;
import model.GameSession;
import model.Ladder;
import model.Player;
import model.QuestionTile;
import model.Snake;

public class GameBoardEasyController {
	private EasyGame easyGame; // The game logic

	private Player currentplayer; // Moved inside the class, not at declaration

	@FXML
	private Label playerNameLabel;
	@FXML
	private TextField playerNameTextField;
	@FXML
	private ImageView stopwatchImageView;
	@FXML
	private Label timerLabel;
	@FXML
	private AnchorPane Overlay;
	private Button[][] buttonMatrix;
	@FXML
	private ImageView object;
	@FXML
	private ImageView object1;
	@FXML
	private Button diceButton;

	public void initialize() {
		easyGame = EasyGame.getInstance();
		currentplayer = easyGame.getGameplayers().get(0); // Now it's safe to initialize.
		Overlay.getChildren().clear(); // Clear any existing images

		initializeBoard();
		updateBoardWithSnakes();
		updateBoardWithLadders();
		// updateBoardWithQuestionTiles();
	}


	private final double TILE_WIDTH = 45.0; // Set the width of your tiles here
	private final double TILE_HEIGHT = 45.0; // Set the height of your tiles here
	private boolean isPlayer1Turn = true; // Starts with player 1
	private List<String> selectedColors = new ArrayList<>();

	@FXML
	private ImageView blueObject;
	@FXML
	private ImageView greenObject;

	@FXML
	private ImageView greyObject;
	@FXML
	private ImageView purpleObject;

	@FXML
	private ImageView redObject;

	@FXML
	private ImageView yellowObject;
	private GameSession gameSession;

	// 49buttons
	@FXML
	private Button i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6, i2j0, i2j1, i2j2,
			i2j3, i2j4, i2j5, i2j6, i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6, i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6,
			i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6, i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6;

	private void initializeBoard() {
		buttonMatrix = new Button[][] { { i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6 },
				{ i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6 }, { i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6 },
				{ i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6 }, { i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6 },
				{ i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6 }, { i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6 } };
	}

	private void updateBoardWithLadders() {
		for (Ladder ladder : easyGame.getLaddersMap().values()) {
			ImageView ladderImageView = ladder.getImageView();

			// Calculate the grid position for the bottom of the ladder
			Point2D ladderBottomGridPosition = calculateGridPosition(ladder.getStartPosition());
			System.out.println(" this is the grid position  of the ladder" + ladder.getLength() + " : "
					+ ladderBottomGridPosition);

			// Convert grid position to pixel position for the bottom
			Point2D ladderBottomPixel = calculatePixelPosition(ladderBottomGridPosition);
			System.out.println(
					" this is the pixel position  of the ladder" + ladder.getLength() + " : " + ladderBottomPixel);

			// Calculate the grid position for the top of the ladder
			Point2D ladderTopGridPosition = calculateGridPosition(ladder.getEndPosition());

			// Convert grid position to pixel position for the top
			Point2D ladderTopPixel = calculatePixelPosition(ladderTopGridPosition);
			System.out.println(
					" this is the start of the ladder" + ladder.getLength() + " : " + ladder.getStartPosition());
			System.out.println(" this is the end of the ladder" + ladder.getLength() + " : " + ladder.getEndPosition());

			// Since the images are pre-sized, we assume they are the correct height.
			// Thus, we only need to center them horizontally on the tiles.
			// We get the center X of the bottom tile and subtract half the width of the
			// ladder image.
			double ladderImageCenterX = ladderBottomPixel.getX() + TILE_WIDTH / 2
					- ladderImageView.getBoundsInParent().getWidth() / 2;

			// The Y position should be set so that the bottom of the ladder image
			// aligns with the bottom of the start position tile.
			double ladderImageBottomY = ladderBottomPixel.getY() + TILE_HEIGHT
					- ladderImageView.getBoundsInParent().getHeight();

			// Set the ImageView of the ladder at the calculated positions
			ladderImageView.setLayoutX(ladderImageCenterX); // Centered X position
			ladderImageView.setLayoutY(ladderImageBottomY); // Bottom aligned Y position

			// Add the ImageView to the overlay
			Overlay.getChildren().add(ladderImageView);
		}
	}

	private Point2D calculateGridPosition(int boardPosition) {
		int size = easyGame.getSize(); // Assuming size is the dimension of the board
		int row = (boardPosition - 1) / size;
		int col = (boardPosition - 1) % size;

		// Adjust column index for zigzag pattern
		if (row % 2 != 0) { // If the row is even when 0-indexed, invert the column calculation
			col = (size - 1) - col;
		}

		// Adjust row index to start from the bottom
		row = (size - 1) - row;

		return new Point2D(col, row);
	}

	private Point2D calculatePixelPosition(Point2D gridPosition) {
		double x = gridPosition.getX() * TILE_WIDTH;
		double y = gridPosition.getY() * TILE_HEIGHT;
		return new Point2D(x, y);
	}

	@FXML
	private void rollDiceAndMove() {
		// Roll the dice to get a number between 1 and 4
		Dice result = new Dice(GameLevel.EASY);
		int diceRoll = result.rollDice();

		// Move the player
		movePlayer(diceRoll);

		// Switch turn to the next player
		currentplayer = easyGame.getGameplayers()
				.get((easyGame.getGameplayers().indexOf(currentplayer) + 1) % easyGame.getNumberofplayers());
	}

	private void updateBoardWithSnakes() {

		for (Snake snake : easyGame.getSnakesMap().values()) {
			ImageView snakeImageView = snake.getImageView(); // Get the ImageView for the snake

			// Calculate the grid position for the head of the snake
			Point2D headGridPosition = calculateGridPosition(snake.getStartPosition());

			// Convert grid position to pixel position for the top-left corner of the tile
			Point2D headPixel = calculatePixelPosition(headGridPosition);

			// Set the ImageView of the snake to the pixel position
			// Since the image is pre-sized to fit the tile, you can set it directly to the
			// tile's position
			snakeImageView.setLayoutX(headPixel.getX());
			snakeImageView.setLayoutY(headPixel.getY());

			// Add the ImageView to the overlay
			Overlay.getChildren().add(snakeImageView);
		}
	}

	public void movePlayer(int diceRoll) {
	    // Print the dice roll result
	    System.out.println(currentplayer.getName() + " rolled: " + diceRoll);

	    // Calculate the new position
	    int newPosition = currentplayer.getPosition() + diceRoll;

	    // Check for ladder at the new position
	    Ladder ladder = easyGame.getLaddersMap().get(newPosition);
	    if (ladder != null) {

	        newPosition = ladder.getEndPosition();

	        
	        System.out.println(currentplayer.getName() + " climbed a ladder to position: " + newPosition);
	    }

	    // Check for snake at the new position
	    Snake snake = easyGame.getSnakesMap().get(newPosition);
	    if (snake != null) {
	        newPosition = snake.getEndPosition();
	        System.out.println(currentplayer.getName() + " got bitten by a snake, moved to position: " + newPosition);
	    }

	    // Check for question tile at the new position
	    for (QuestionTile qt : easyGame.getQuestions()) {
	        if (qt.getPosition() == newPosition) {
	            System.out.println(currentplayer.getName() + " landed on a question tile at position: " + newPosition);
	            handleQuestionTileEvent(qt);
	            break;
	        }
	    }

	    // Update the player's logical position
	    currentplayer.setPosition(newPosition);

	    // Update the visual position of the player
	    updatePlayerPositionVisuals(newPosition);

	    // Check for win condition
	    if (newPosition >= easyGame.getSize() * easyGame.getSize()) {
	        // Handle winning condition (end game, display message, etc.)
	        System.out.println(currentplayer.getName() + " wins the game!");
	    }
	}

	private void updatePlayerPositionVisuals(int newPosition) {
	    System.out.println("Updating visuals for new position: " + newPosition);
	    // Clear the previous position
	    clearPreviousPlayerPosition(currentplayer);

	    // Convert newPosition to matrix indices
	    int row = newPosition / 7;
	    int col = newPosition % 7;
	    if (row % 2 == 1) {
	        col = 6 - col; // Adjust for zigzag pattern
	    }
	    System.out.println("Row: " + row + " Col: " + col);

	    // Update the button matrix to show the player's new position
	    buttonMatrix[row][col].setGraphic(currentplayer.getObject());
	}

	private void clearPreviousPlayerPosition(Player currentplayer) {
		int previousPosition = currentplayer.getPosition();
		int row = previousPosition / 7;
		int col = previousPosition % 7;
		if (row % 2 == 1) {
			col = 6 - col;
		}

		// Only clear the graphic if it matches the current player's object
		ImageView currentPlayerObject = currentplayer.getObject();
		if (row >= 0 && row < buttonMatrix.length && col >= 0 && col < buttonMatrix[0].length) {
			// Check if buttonMatrix[row][col] is within bounds before accessing
			if (buttonMatrix[row][col].getGraphic() == currentPlayerObject) {
				buttonMatrix[row][col].setGraphic(null);
			}
		}
	}

	private void handleQuestionTileEvent(QuestionTile qt) {
		// Logic to display the question to the player and handle their response
		// This could involve showing a dialog, checking the answer, and applying any
		// game effects
	}

	public void setSelectedColors(List<String> colors) {
		// Initialize all objects to be invisible
		redObject.setVisible(false);
		blueObject.setVisible(false);
		greenObject.setVisible(false);
		purpleObject.setVisible(false);
		greyObject.setVisible(false);
		yellowObject.setVisible(false);
		selectedColors = colors;
//				for (String color : selectedColors)
//					System.out.println(color);

		int i = 0;// Set visible only the objects that match the selected colors
		for (String color : colors) {

			switch (color.toLowerCase()) {
			case "red":
				redObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(redObject);
				i++;
				break;
			case "blue":
				blueObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(blueObject);
				i++;
				break;
			case "green":
				greenObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(greenObject);
				i++;
				break;
			case "purple":
				purpleObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(purpleObject);
				i++;
				break;
			case "grey":
				greyObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(greyObject);
				i++;
				break;
			case "yellow":
				yellowObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(yellowObject);
				i++;
				break;
			}
		}
	}

	private void updateBoardWithQuestionTiles() {
		for (QuestionTile QT : easyGame.getQuestions()) {
			ImageView questiotileImageView = QT.getImageView(); // Assuming Ladder class has getImageView method

			// Calculate the grid position for the bottom and top of the ladder
			Point2D questiontileGridPosition = calculateGridPosition(QT.getPosition());

			// Convert grid position to pixel position
			Point2D questiontilePixelPosition = calculatePixelPosition(questiontileGridPosition);

			// Set the ImageView of the ladder at the bottom position
			questiotileImageView.setLayoutX(questiontilePixelPosition.getX());
			questiotileImageView.setLayoutY(questiontilePixelPosition.getY());
			questiotileImageView.setFitWidth(20);
			questiotileImageView.setFitHeight(20);
			// Add the ImageView to the overlay
			Overlay.getChildren().add(questiotileImageView);
		}
	}
}