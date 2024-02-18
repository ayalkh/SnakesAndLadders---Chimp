
package control;

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
	private Button diceButton;
	private int currentPlayerPosition = 0; // Starting at button 1
	private Random random = new Random();
	private final double TILE_WIDTH = 45; // Set the width of your tiles here
	private final double TILE_HEIGHT = 45; // Set the height of your tiles here
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

	private void scaleAndRotateSnakeImage(ImageView snakeImageView, Point2D head, Point2D tail) {
		// Calculate the number of tiles snake covers vertically and horizontally
		int verticalTiles = Math.abs((int) head.getY() - (int) tail.getY()) + 1;
		int horizontalTiles = Math.abs((int) head.getX() - (int) tail.getX()) + 1;

		// Set the size of the snake image based on the number of tiles it covers
		snakeImageView.setFitHeight(TILE_HEIGHT * verticalTiles);
		snakeImageView.setFitWidth(TILE_WIDTH * horizontalTiles); // Only set this if you want to scale width as well

		// Determine rotation and flipping
		if (head.getX() == tail.getX()) {
			// Vertical snake
			snakeImageView.setRotate(head.getY() < tail.getY() ? 0 : 180);
		} else {
			// Horizontal snake or diagonal
			// Use atan2 to find the angle required to rotate the snake image to align it
			// with the grid
			double angle = Math.toDegrees(Math.atan2(tail.getY() - head.getY(), tail.getX() - head.getX()));
			snakeImageView.setRotate(angle);
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
		// Roll the dice to get a number between 1 and 6
		int diceRoll = random.nextInt(6) + 1;

		// Move the player
		movePlayer(diceRoll);
	}

	private void movePlayer(int steps) {
		// Update the current player position
		currentPlayerPosition += steps;

		// If the player's position exceeds the number of buttons, set it to the last
		// button
		if (currentPlayerPosition >= 49) {
			currentPlayerPosition = 48; // Zero-based index for 49th button
			Alerts.alertBox(AlertType.INFORMATION, "Failed", "Invalid input", "Player has won !!");
		}

		// Convert the currentPlayerPosition to matrix indices
		int row = currentPlayerPosition / 7; // Determine row number (0-indexed)
		int col = currentPlayerPosition % 7; // Determine column number (0-indexed)

		// Check the direction of the current row
		if (row % 2 == 1) {
			// For odd rows (0-indexed, which are even-numbered rows), the numbers increase
			// from right to left
			col = 6 - col;
		}

		// Clear the object from all buttons
		for (Button[] buttonRow : buttonMatrix) {
			for (Button button : buttonRow) {
				button.setGraphic(null);
			}
		}

		// Place the object on the new button
		buttonMatrix[row][col].setGraphic(object);
	}

	private String capitalize(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

}