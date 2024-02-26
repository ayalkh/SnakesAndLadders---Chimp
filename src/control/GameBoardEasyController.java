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
import model.ObjectColor;
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
	@FXML
	private AnchorPane Overlay;
	private EasyGame easyGame; // The game logic
	private Button[][] buttonMatrix;
	@FXML
	private ImageView object;
	@FXML
	private ImageView object1;
	@FXML
	private Button diceButton;
	private int currentPlayerPosition = 0; // Starting at button 1
	private Random random = new Random();
	private int currentPlayer1Position = 0;
	private int currentPlayer2Position = 0;
	private final double TILE_WIDTH = 45; // Set the width of your tiles here
	private final double TILE_HEIGHT = 45; // Set the height of your tiles here
	private boolean isPlayer1Turn = true; // Starts with player 1
	private List<ObjectColor> selectedColors = new ArrayList<>();

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

	public void initialize() {
		easyGame = new EasyGame(); // Initialize the game
		initializeBoard();
		updateBoardWithSnakes();
		updateBoardWithLadders();
	}

	private void initializeBoard() {
		buttonMatrix = new Button[][] { { i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6 },
				{ i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6 }, { i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6 },
				{ i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6 }, { i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6 },
				{ i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6 }, { i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6 } };
	}

	private void updateBoardWithLadders() {
		for (Ladder ladder : easyGame.getLaddersMap().values()) {
			ImageView ladderImageView = ladder.getImageView(); // Assuming Ladder class has getImageView method

			// Calculate the grid position for the bottom and top of the ladder
			Point2D ladderBottomGridPosition = calculateGridPosition(ladder.getStartPosition());
			Point2D ladderTopGridPosition = calculateGridPosition(ladder.getEndPosition());

			// Convert grid position to pixel position
			Point2D ladderBottomPixel = calculatePixelPosition(ladderBottomGridPosition);
			Point2D ladderTopPixel = calculatePixelPosition(ladderTopGridPosition);

			// Set the ImageView of the ladder at the bottom position
			ladderImageView.setLayoutX(ladderBottomPixel.getX());
			// Adjust the Y position so that the top of the ladder image reaches the top
			// position
			ladderImageView.setLayoutY(ladderTopPixel.getY() - (ladder.getLength() - 1) * TILE_HEIGHT);

			// Add the ImageView to the overlay
			Overlay.getChildren().add(ladderImageView);
		}
	}

	private void updateBoardWithSnakes() {
		Overlay.getChildren().clear(); // Clear any existing images

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

	private Point2D calculateGridPosition(int boardPosition) {
		int row = (boardPosition - 1) / easyGame.getSize();
		int col = (boardPosition - 1) % easyGame.getSize();
		// Adjust for zero-based index
		return new Point2D(col, easyGame.getSize() - row - 1);
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
		isPlayer1Turn = !isPlayer1Turn;
	}

	public void movePlayer(int steps) {
		// Update the current player position
		System.out.println((isPlayer1Turn ? "Player 1" : "Player 2") + " got: " + steps + "steps! ");

		int currentPlayerPosition = isPlayer1Turn ? currentPlayer1Position : currentPlayer2Position;
		currentPlayerPosition += steps;

		// Check if landed on a snake
		if (easyGame.getSnakesMap().containsKey(currentPlayerPosition)) {
			System.out.println(easyGame.getSnakesMap().keySet());
			Snake snake = easyGame.getSnakesMap().get(currentPlayerPosition);
			String snakeColor = snake.getColor().toLowerCase();

			switch (snakeColor) {
			case "yellow":
				currentPlayerPosition -= 7; // Move back one row
				break;
			case "green":
				currentPlayerPosition -= 14; // Move back two rows
				break;
			case "blue":
				currentPlayerPosition -= 21; // Move back three rows
				break;
			case "red":
				currentPlayerPosition = 0; // Back to start
				break;
			}

			currentPlayerPosition = Math.max(0, currentPlayerPosition); // Ensure not less than 0
			System.out.println("Hit a " + snakeColor + " snake! Moved to position: " + (currentPlayerPosition + 1));
		}
		// button
		if (currentPlayerPosition >= 49) {
			currentPlayerPosition = 48; // Zero-based index for 49th button
			Alerts.alertBox(AlertType.INFORMATION, "Congratulations", "You Win",
					isPlayer1Turn ? "Player 1 has won!!" : "Player 2 has won!!");
		}
		if (currentPlayerPosition >= 49) {
			currentPlayerPosition = 48; // Zero-based index for 49th button
			Alerts.alertBox(AlertType.INFORMATION, "Congratulations", "You Win",
					isPlayer1Turn ? "Player 1 has won!!" : "Player 2 has won!!");
		}
		// Convert the currentPlayerPosition to matrix indices
		int row = currentPlayerPosition / 7;
		int col = currentPlayerPosition % 7;

		if (row % 2 == 1) {
			col = 6 - col;
		}

		// Update player positions
		if (isPlayer1Turn) {
			currentPlayer1Position = currentPlayerPosition;
		} else {
			currentPlayer2Position = currentPlayerPosition;
		}

		// Clear the previous position of the current player
		clearPreviousPlayerPosition(isPlayer1Turn);

		// Place the object on the new button
		if (selectedColors.get(0) == ObjectColor.RED)
			object = redObject;
		else if (selectedColors.get(0) == ObjectColor.BLUE)
			object = blueObject;
		else if (selectedColors.get(0) == ObjectColor.GREEN)
			object = greenObject;
		else if (selectedColors.get(0) == ObjectColor.YELLOW)
			object = yellowObject;
		else if (selectedColors.get(0) == ObjectColor.PURPLE)
			object = purpleObject;
		else if (selectedColors.get(0) == ObjectColor.GREY)
			object = greyObject;

		if (selectedColors.get(1) == ObjectColor.RED)
			object1 = redObject;
		else if (selectedColors.get(1) == ObjectColor.BLUE)
			object1 = blueObject;
		else if (selectedColors.get(1) == ObjectColor.GREEN)
			object1 = greenObject;
		else if (selectedColors.get(1) == ObjectColor.YELLOW)
			object1 = yellowObject;
		else if (selectedColors.get(1) == ObjectColor.PURPLE)
			object1 = purpleObject;
		else if (selectedColors.get(1) == ObjectColor.GREY)
			object1 = greyObject;

		buttonMatrix[row][col].setGraphic(isPlayer1Turn ? object : object1);
	}

	private void clearPreviousPlayerPosition(boolean isPlayer1) {
		int previousPosition = isPlayer1 ? currentPlayer1Position : currentPlayer2Position;
		int row = previousPosition / 7;
		int col = previousPosition % 7;
		if (row % 2 == 1) {
			col = 6 - col;
		}

		// Only clear the graphic if it matches the current player's object
		ImageView currentPlayerObject = isPlayer1 ? object : object1;
		if (buttonMatrix[row][col].getGraphic() == currentPlayerObject) {
			buttonMatrix[row][col].setGraphic(null);
		}
	}

	private String capitalize(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	public int getCurrentPlayerPosition() {
		return currentPlayerPosition;
	}

	public void setCurrentPlayerPosition(int currentPlayerPosition) {
		this.currentPlayerPosition = currentPlayerPosition;
	}

	public void setSelectedColors(List<ObjectColor> colors) {
		// Initialize all objects to be invisible
		redObject.setVisible(false);
		blueObject.setVisible(false);
		greenObject.setVisible(false);
		purpleObject.setVisible(false);
		greyObject.setVisible(false);
		yellowObject.setVisible(false);
		selectedColors = colors;

		if (selectedColors.get(1) == ObjectColor.RED)
			object1 = redObject;
		else if (selectedColors.get(1) == ObjectColor.BLUE)
			object1 = blueObject;
		else if (selectedColors.get(1) == ObjectColor.GREEN)
			object1 = greenObject;
		else if (selectedColors.get(1) == ObjectColor.YELLOW)
			object1 = yellowObject;
		else if (selectedColors.get(1) == ObjectColor.PURPLE)
			object1 = purpleObject;
		else if (selectedColors.get(1) == ObjectColor.GREY)
			object1 = greyObject;
		// Set visible only the objects that match the selected colors
		for (ObjectColor color : colors) {
			switch (color) {
			case RED:
				redObject.setVisible(true);
				break;
			case BLUE:
				blueObject.setVisible(true);
				break;
			case GREEN:
				greenObject.setVisible(true);
				break;
			case PURPLE:
				purpleObject.setVisible(true);
				break;
			case GREY:
				greyObject.setVisible(true);
				break;
			case YELLOW:
				yellowObject.setVisible(true);
				break;
			}
		}
	}

	private void updatePlayerPositions() {
		// Update the player position based on their current state
		int player1Row = currentPlayer1Position / 7;
		int player1Col = currentPlayer1Position % 7;
		if (player1Row % 2 == 1) {
			player1Col = 6 - player1Col;
		}

		int player2Row = currentPlayer2Position / 7;
		int player2Col = currentPlayer2Position % 7;
		if (player2Row % 2 == 1) {
			player2Col = 6 - player2Col;
		}

		// Clear previous positions
		clearPreviousPlayerPosition(true);
		clearPreviousPlayerPosition(false);

		// Set the graphics for the new positions
		buttonMatrix[player1Row][player1Col].setGraphic(object);
		buttonMatrix[player2Row][player2Col].setGraphic(object1);
	}
}