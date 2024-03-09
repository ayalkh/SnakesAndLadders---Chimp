package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.EasyGame;
import model.Ladder;
import model.MediumGame;
import model.Player;
import model.Question;
import model.Snake;

public class GameBoardMediumController {

	@FXML
	private AnchorPane root;
	@FXML
	private Button i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i0j7, i0j8, i0j9, i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6,
			i1j7, i1j8, i1j9, i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6, i2j7, i2j8, i2j9, i3j0, i3j1, i3j2, i3j3, i3j4,
			i3j5, i3j6, i3j7, i3j8, i3j9, i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6, i4j7, i4j8, i4j9, i5j0, i5j1, i5j2,
			i5j3, i5j4, i5j5, i5j6, i5j7, i5j8, i5j9, i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6, i6j7, i6j8, i6j9, i7j0,
			i7j1, i7j2, i7j3, i7j4, i7j5, i7j6, i7j7, i7j8, i7j9, i8j0, i8j1, i8j2, i8j3, i8j4, i8j5, i8j6, i8j7, i8j8,
			i8j9, i9j0, i9j1, i9j2, i9j3, i9j4, i9j5, i9j6, i9j7, i9j8, i9j9;

	@FXML
	private AnchorPane Overlay;

	@FXML
	private ImageView redObject;

	@FXML
	private ImageView yellowObject;

	@FXML
	private ImageView greenObject;

	@FXML
	private ImageView blueObject;

	@FXML
	private ImageView greyObject;

	@FXML
	private ImageView purpleObject;

	@FXML
	private ImageView blueAvatar;

	@FXML
	private ImageView yellowAvatar;

	@FXML
	private ImageView greenAvatar;

	@FXML
	private ImageView purpleAvatar;

	@FXML
	private ImageView greyAvatar;

	@FXML
	private ImageView redAvatar;

	@FXML
	private Button diceButton;

	@FXML
	void rollDiceAndMove(ActionEvent event) {

	}

	private Random random = new Random();

	private final double TILE_WIDTH = 60.0; // Set the width of your tiles here
	private final double TILE_HEIGHT = 60.0; // Set the height of your tiles here
	private List<String> selectedColors = new ArrayList<>();

	private Button[][] buttonMatrix;
	private MediumGame mediumGame; // The game logic
	private ArrayList<Question> questions;
	private Player currentplayer; // Moved inside the class, not at declaration

	public void initialize() {
		mediumGame = MediumGame.getInstance();
		currentplayer = mediumGame.getGamePlayers().get(0); // Now it's safe to initialize.
		System.out.println(currentplayer);
		initializeBoard();
		updateBoardWithSnakes();
		updateBoardWithLadders();
//		loadquestions();
//		updateBoardWithQuestionTiles();

	}
    
    private void initializeBoard() {
        buttonMatrix = new Button[][] {
            {i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i0j7, i0j8, i0j9},
            {i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6, i1j7, i1j8, i1j9},
            {i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6, i2j7, i2j8, i2j9},
            {i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6, i3j7, i3j8, i3j9},
            {i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6, i4j7, i4j8, i4j9},
            {i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6, i5j7, i5j8, i5j9},
            {i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6, i6j7, i6j8, i6j9},
            {i7j0, i7j1, i7j2, i7j3, i7j4, i7j5, i7j6, i7j7, i7j8, i7j9},
            {i8j0, i8j1, i8j2, i8j3, i8j4, i8j5, i8j6, i8j7, i8j8, i8j9},
            {i9j0, i9j1, i9j2, i9j3, i9j4, i9j5, i9j6, i9j7, i9j8, i9j9}
        };
    }
    private void updateBoardWithLadders() {
		for (Ladder ladder : mediumGame.getLaddersMap().values()) {
			ImageView ladderImageView = ladder.getImageView_Med();

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
    private void updateBoardWithSnakes() {
        for (Snake snake : mediumGame.getSnakesMap().values()) {
            ImageView snakeImageView = snake.getImageView_Med(); // Ensure this method exists
	

			Point2D headGridPosition = calculateGridPosition(snake.getStartPosition());
			Point2D headPixel = calculatePixelPosition(headGridPosition);

			Point2D tailGridPosition = calculateGridPosition(snake.getEndPosition());
			Point2D tailPixel = calculatePixelPosition(tailGridPosition);

			System.out.println("Snake start: " + snake.getStartPosition());
			System.out.println("Snake end: " + snake.getEndPosition());

			snakeImageView.setLayoutX(headPixel.getX());
			snakeImageView.setLayoutY(headPixel.getY());

			Overlay.getChildren().add(snakeImageView);
		}
	}

	private Point2D calculateGridPosition(int boardPosition) {
		int size = mediumGame.getSize();
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

	public void setSelectedColors(List<String> colors) {
		// Initialize all objects to be invisible
		redObject.setVisible(false);
		blueObject.setVisible(false);
		greenObject.setVisible(false);
		purpleObject.setVisible(false);
		greyObject.setVisible(false);
		yellowObject.setVisible(false);

		ColorAdjust desaturate = new ColorAdjust();
		desaturate.setSaturation(-1);
		redAvatar.setEffect(desaturate);
		blueAvatar.setEffect(desaturate);
		greenAvatar.setEffect(desaturate);
		purpleAvatar.setEffect(desaturate);
		greyAvatar.setEffect(desaturate);
		yellowAvatar.setEffect(desaturate);
		selectedColors = colors;

		ColorAdjust resetSaturation = new ColorAdjust();
		resetSaturation.setSaturation(0);

		int i = 0;// Set visible only the objects that match the selected colors
		for (String color : colors) {

			switch (color.toLowerCase()) {
			case "red":
				redAvatar.setEffect(resetSaturation);
				redObject.setVisible(true);
				mediumGame.getGamePlayers().get(i).setColor(color);
				mediumGame.getGamePlayers().get(i).setObject(redObject);
				i++;
				break;
			case "blue":
				blueAvatar.setEffect(resetSaturation);
				blueObject.setVisible(true);
				mediumGame.getGamePlayers().get(i).setColor(color);
				mediumGame.getGamePlayers().get(i).setObject(blueObject);
				i++;
				break;
			case "green":
				greenAvatar.setEffect(resetSaturation);
				greenObject.setVisible(true);
				mediumGame.getGamePlayers().get(i).setColor(color);
				mediumGame.getGamePlayers().get(i).setObject(greenObject);
				i++;
				break;
			case "purple":
				purpleAvatar.setEffect(resetSaturation);
				purpleObject.setVisible(true);
				mediumGame.getGamePlayers().get(i).setColor(color);
				mediumGame.getGamePlayers().get(i).setObject(purpleObject);
				i++;
				break;
			case "grey":
				greyAvatar.setEffect(resetSaturation);
				greyObject.setVisible(true);
				mediumGame.getGamePlayers().get(i).setColor(color);
				mediumGame.getGamePlayers().get(i).setObject(greyObject);
				i++;
				break;
			case "yellow":
				yellowAvatar.setEffect(resetSaturation);
				yellowObject.setVisible(true);
				mediumGame.getGamePlayers().get(i).setColor(color);
				mediumGame.getGamePlayers().get(i).setObject(yellowObject);
				i++;
				break;
			}
		}
	}

	private void switchToNextPlayer() {
		currentplayer = mediumGame.getGamePlayers()
				.get((mediumGame.getGamePlayers().indexOf(currentplayer) + 1) % mediumGame.getNumberOfPlayers());
		System.out.println("Now it's " + currentplayer.getName() + "'s turn.");
	}

}
