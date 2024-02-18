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

	// 49buttons
	@FXML
	private Button i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6, i2j0, i2j1, i2j2,
			i2j3, i2j4, i2j5, i2j6, i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6, i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6,
			i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6, i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6;

	public void initialize() {
		easyGame = new EasyGame(); // Initialize the game
		buttonMatrix = new Button[][] { { i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6 },
				{ i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6 }, { i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6 },
				{ i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6 }, { i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6 },
				{ i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6 }, { i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6 } };
		updateBoardWithSnakes();
	}

	private void updateBoardWithSnakes() {
		Overlay.getChildren().clear();

		for (Snake snake : easyGame.getSnakesMap().values()) {
			ImageView snakeImageView = snake.getImageView();

			// Calculate the grid position for the head and tail of the snake
			Point2D headGridPosition = calculateGridPosition(snake.getStartPosition());
			Point2D tailGridPosition = calculateGridPosition(snake.getEndPosition());

			// Convert grid positions to pixel positions
			Point2D headPixel = calculatePixelPosition(headGridPosition);
			Point2D tailPixel = calculatePixelPosition(tailGridPosition);

			// Center the head of the snake in the middle of its tile
			double headCenterX = headPixel.getX() + TILE_WIDTH / 2 - snakeImageView.getFitWidth() / 2;
			double headCenterY = headPixel.getY() + TILE_HEIGHT / 2 - snakeImageView.getFitHeight() / 2;

			// Set the position of the snake image
			snakeImageView.setLayoutX(headCenterX);
			snakeImageView.setLayoutY(headCenterY);

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
		int diceRoll = random.nextInt(4) + 1;

		// Move the player
		movePlayer(diceRoll);

		// Switch turn to the next player
		isPlayer1Turn = !isPlayer1Turn;
	}

	private void movePlayer(int steps) {
		// Update the current player position
		System.out.println("you got :" + steps);

		int currentPlayerPosition = isPlayer1Turn ? currentPlayer1Position : currentPlayer2Position;
		currentPlayerPosition += steps;

		// Check for ladders, snakes, and question squares
		// Implement ladder, snake, and question logic here...

		// If the player's position exceeds the number of buttons, set it to the last
		// button
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

		for (String color : selectedColors)
			System.out.println(color);

		// Place the object on the new button
		if (selectedColors.get(0).toLowerCase() == "red")
			object = redObject;
		else if (selectedColors.get(0).toLowerCase() == "blue")
			object = blueObject;
		else if (selectedColors.get(0).toLowerCase() == "green")
			object = greenObject;
		else if (selectedColors.get(0).toLowerCase() == "yellow")
			object = yellowObject;
		else if (selectedColors.get(0).toLowerCase() == "purple")
			object = purpleObject;
		else if (selectedColors.get(0).toLowerCase() == "grey")
			object = greyObject;

		if (selectedColors.get(1).toLowerCase() == "red")
			object1 = redObject;
		else if (selectedColors.get(1).toLowerCase() == "blue")
			object1 = blueObject;
		else if (selectedColors.get(1).toLowerCase() == "green")
			object1 = greenObject;
		else if (selectedColors.get(1).toLowerCase() == "yellow")
			object1 = yellowObject;
		else if (selectedColors.get(1).toLowerCase() == "purple")
			object1 = purpleObject;
		else if (selectedColors.get(1).toLowerCase() == "grey")
			object1 = greyObject;

		buttonMatrix[row][col].setGraphic(isPlayer1Turn ? object : object1);
	}

	private void clearPreviousPlayerPosition(boolean isPlayer1) {
		// Logic to clear the previous position of the current player
		// This should clear only the player's object, not affecting the other player's
		// object
	}

	private String capitalize(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
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
//		for (String color : selectedColors)
//			System.out.println(color);

		// Set visible only the objects that match the selected colors
		for (String color : colors) {
			switch (color.toLowerCase()) {
			case "red":
				redObject.setVisible(true);
				break;
			case "blue":
				blueObject.setVisible(true);
				break;
			case "green":
				greenObject.setVisible(true);
				break;
			case "purple":
				purpleObject.setVisible(true);
				break;
			case "grey":
				greyObject.setVisible(true);
				break;
			case "yellow":
				yellowObject.setVisible(true);
				break;
			}
		}
	}

}