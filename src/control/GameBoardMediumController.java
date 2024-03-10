package control;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Dice;
import model.GameLevel;
import model.Ladder;
import model.MediumGame;
import model.Player;
import model.Question;
import model.QuestionTile;
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

	MediaPlayer mediaPlayer;

	public void music(String soundFileName) {
		String path = "/sound/" + soundFileName;
		Media h = new Media(getClass().getResource(path).toExternalForm());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.play();
	}

	@FXML
	private ImageView rollDiceImage;
	@FXML
	ImageView toggleMusicButton;

	@FXML
	void handleToggleMusic(MouseEvent event) {
		BackgroundMusicPlayer.getInstance().toggleMusic();

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
		loadquestions();
		updateBoardWithQuestionTiles();
		updateBoardWithsurpriseTile();

		toggleMusicButton.setCursor(Cursor.HAND);
		toggleMusicButton.setOnMouseEntered(event -> toggleMusicButton.setOpacity(0.8));
		toggleMusicButton.setOnMouseExited(event -> toggleMusicButton.setOpacity(1.5));

		rollDiceImage.setCursor(Cursor.HAND);
	}

	private void initializeBoard() {
		buttonMatrix = new Button[][] { { i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i0j7, i0j8, i0j9 },
				{ i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6, i1j7, i1j8, i1j9 },
				{ i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6, i2j7, i2j8, i2j9 },
				{ i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6, i3j7, i3j8, i3j9 },
				{ i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6, i4j7, i4j8, i4j9 },
				{ i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6, i5j7, i5j8, i5j9 },
				{ i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6, i6j7, i6j8, i6j9 },
				{ i7j0, i7j1, i7j2, i7j3, i7j4, i7j5, i7j6, i7j7, i7j8, i7j9 },
				{ i8j0, i8j1, i8j2, i8j3, i8j4, i8j5, i8j6, i8j7, i8j8, i8j9 },
				{ i9j0, i9j1, i9j2, i9j3, i9j4, i9j5, i9j6, i9j7, i9j8, i9j9 } };
	}

	private void updateBoardWithLadders() {
		for (Ladder ladder : mediumGame.getLaddersMap().values()) {
			ImageView ladderImageView = ladder.getImageView();

			// Calculate the grid position for the bottom of the ladder
			Point2D ladderBottomGridPosition = calculateGridPosition(ladder.getStartPosition());

			// Convert grid position to pixel position for the bottom
			Point2D ladderBottomPixel = calculatePixelPosition(ladderBottomGridPosition);

			// Calculate the grid position for the top of the ladder
			Point2D ladderTopGridPosition = calculateGridPosition(ladder.getEndPosition());

			// Convert grid position to pixel position for the top
			Point2D ladderTopPixel = calculatePixelPosition(ladderTopGridPosition);

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

	private void updateBoardWithQuestionTiles() {
		for (QuestionTile QT : mediumGame.getQuestions()) {
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

	private void updateBoardWithsurpriseTile() {

		ImageView questiotileImageView = mediumGame.getSurprise().getImageView(); // Assuming Ladder class has
																					// getImageView method

		// Calculate the grid position for the bottom and top of the ladder
		Point2D questiontileGridPosition = calculateGridPosition(mediumGame.getSurprise().getPosition());

		// Convert grid position to pixel position
		Point2D questiontilePixelPosition = calculatePixelPosition(questiontileGridPosition);

		// Set the ImageView of the ladder at the bottom position
		questiotileImageView.setLayoutX(questiontilePixelPosition.getX());
		questiotileImageView.setLayoutY(questiontilePixelPosition.getY());
		questiotileImageView.setFitWidth(40);
		questiotileImageView.setFitHeight(40);
		// Add the ImageView to the overlay
		Overlay.getChildren().add(questiotileImageView);
	}

	private void loadquestions() {
		questions = new ArrayList<>();
		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader("Questions.json")) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray jsonQuestions = (JSONArray) jsonObject.get("questions");

			for (Object o : jsonQuestions) {
				JSONObject jsonQuestion = (JSONObject) o;
				Question question = new Question(jsonQuestion);

				questions.add(question);

			}
			if (questions == null) {
				System.err.println("Error: Unable to load questions from JSON file");
				// Handle the error gracefully, e.g., by providing default questions or
				// displaying an error message to the user
			} else {
				System.out.println("Successfully loaded " + questions.size() + " questions");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void rollDiceAndMove() {

		music("rollingDice.mp3");
		Dice result = new Dice(GameLevel.HARD);
		int diceRoll = result.rollDice();
		System.out.println("rolling dice result : " + diceRoll);

		if (diceRoll >= 1 && diceRoll <= 6) {
			// Configure rotation animation only for rolls 1-6
			RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), rollDiceImage);
			rotateTransition.setByAngle(360 * 3); // Rotate 3 times
			rotateTransition.setCycleCount(1);
			rotateTransition.setAutoReverse(false);

			rotateTransition.setOnFinished(event -> {
				// After rotation finishes, execute the remaining operations in the next pulse
				// of the JavaFX Application Thread
				Platform.runLater(() -> {
					// Set the dice image to the result
					try {
						Image diceImage = new Image(getClass().getResourceAsStream("/images/dice" + diceRoll + ".png"));
						rollDiceImage.setImage(diceImage);
					} catch (NullPointerException e) {
						System.out.println("Error: Unable to load dice image.");
					}
					movePlayer(diceRoll);
					// Once everything for this player's turn is done, switch to the next player
					switchToNextPlayer();
				});
			});

			// Start the rotation animation
			rotateTransition.play();
		} else {

			music("popQuestion.mp3");
			handleQuestionTileEvent(random.nextInt(2) + 1);
			switchToNextPlayer();

		}

	}

	private void switchToNextPlayer() {
		currentplayer = mediumGame.getGamePlayers()
				.get((mediumGame.getGamePlayers().indexOf(currentplayer) + 1) % mediumGame.getNumberOfPlayers());
		System.out.println("Now it's " + currentplayer.getName() + "'s turn.");
	}

	public void movePlayer(int diceRoll) {
		String soundPath = "";
		System.out.println();
		System.out.println(currentplayer.getName() + " got : " + diceRoll + " steps ");
		System.out.println(currentplayer.getName() + " previous position is : " + currentplayer.getPosition());
		int newPosition = currentplayer.getPosition() + diceRoll;
		System.out.println("new Position before editing the playerPosition is : " + newPosition);

		// Correctly updating the position
		if (newPosition < 1) {
			newPosition = 0;
		} // Ensure the position does not go below the starting point
		if (newPosition >= 100) {
			newPosition = 100; // Assuming 49 is the winning tile

		}
		if (diceRoll != 0 && newPosition > 0) {
			music("playerMoving.mp3");

		}
		// music("playerMoving.mp3");
		currentplayer.setPositionAfterClimbing(newPosition); // Update this line to set the newPosition
		System.out.println(currentplayer.getName() + " current position is : " + currentplayer.getPosition());

		// Check for ladder at the new position
		Ladder ladder = mediumGame.getLaddersMap().get(newPosition);
		if (ladder != null) {
			newPosition = ladder.getEndPosition();
			System.out.println("player postition before climbing the ladder : " + currentplayer.getPosition());
			currentplayer.setPositionAfterClimbing(newPosition);
			System.out.println("player postition after climbing the ladder : " + currentplayer.getPosition());
			System.out.println(currentplayer.getName() + " climbed a ladder to position: " + newPosition);

		}

		// Check for snake at the new position
		Snake snake = mediumGame.getSnakesMap().get(newPosition);
		if (snake != null) {
			newPosition = snake.getEndPosition();
			System.out.println("player postition before bitten by a snake : " + currentplayer.getPosition());
			currentplayer.setPositionAfterClimbing(newPosition);
			System.out.println("player postition after bitten by a snake : " + currentplayer.getPosition());
			music("snakeBite.mp3");
		}

		int surprise = mediumGame.getSurprise().getPosition();
		if (surprise == newPosition) {
			int randomNumber = random.nextInt(2) + 1;
			// if the random is 1 then the player gets 10 steps forward
			if (randomNumber == 1) {
				Alerts.alertBox(Alert.AlertType.INFORMATION, "CONGRATULATIONS !!! ",
						currentplayer.getName() + " GOT 10 STEPS FORWARD !! ", null);
				newPosition = currentplayer.getPosition() + 10;
				currentplayer.setPosition(10);
			} else {
				Alerts.alertBox(Alert.AlertType.INFORMATION, "OOPS !!! ",
						currentplayer.getName() + " GOT 10 STEPS BACKWARD !! ", null);
				newPosition = currentplayer.getPosition() + 10;
				if (newPosition <= 1)
					currentplayer.setPositionAfterClimbing(1);

			}

		}
		if (diceRoll != 0) {
			// Check for question tile at the new position
			for (QuestionTile QT : mediumGame.getQuestions()) {// check if the player stepped is on a question tile//
				if (newPosition == QT.getPosition()) {
					System.out.println("pop question");
					music("popQuestion.mp3");
					handleQuestionTileEvent(QT.getLevel());
					if (newPosition >= 100) {
						// Handle winning condition (end game, display message, etc.)
						Alerts.alertBox(Alert.AlertType.INFORMATION, "CONGRATULATIONS !!! ",
								"Player " + currentplayer.getName() + " WON the game !! ",
								"Triumphantly victorious !!");
						music("winning.mp3");

					}
					return;
				}
			}

		}
		updatePlayerPositionVisuals(newPosition);
		if (newPosition >= 49) {
			music("winning.mp3");
			// Handle winning condition (end game, display message, etc.)
			Alerts.alertBox(Alert.AlertType.INFORMATION, "CONGRATULATIONS !!! ",
					"Player " + currentplayer.getName() + " WON the game !! ", "Triumphantly victorious !!");

			try {
				// Load the QuestionView FXML file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HistoryBoard.fxml"));
				Parent root = loader.load();

				// Use the existing stage instead of creating a new one
				Stage currentStage = new Stage();

				currentStage.setTitle("History Board");
				currentStage.setScene(new Scene(root));
				currentStage.show(); // This line actually displays the stage
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error opening history view: " + e.getMessage());
			}

		}
	}

	private void handleQuestionTileEvent(int level) {
		// Filter questions by level
		List<Question> levelQuestions = questions.stream().filter(question -> question.getLevel() == level)
				.collect(Collectors.toList());
		if (levelQuestions.isEmpty()) {
			System.out.println("No questions available for level " + level);
			return;
		}
		// Select a random question from the filtered list
		Question selectedQuestion = levelQuestions.get(random.nextInt(levelQuestions.size()));
		System.out.println(selectedQuestion);

		try {
			// Load the question pop-up FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Questionpop.fxml"));
			Parent root = loader.load();

			// Get the controller and pass the selected question
			QuestionpopController popControl = loader.getController();
			popControl.setQuestion(selectedQuestion);

			// Setting up and displaying the stage
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

			// React based on the player's answer
			if (popControl.isCorrect()) {
				System.out.println(currentplayer.getName() + " answered correctly.");
				if (level == 3) { // If the question was hard and answered correctly
					movePlayer(1); // Move the player forward 1 tile
				} else {
					movePlayer(0);
				}

			} else {
				// If the player answered incorrectly, you might want to penalize them
				System.out.println(currentplayer.getName() + " answered incorrectly.");
				// Move the player back based on the difficulty of the question
				if (level == 1) { // Easy question
					movePlayer(-1); // Move back 1 tile
				} else if (level == 2) { // Medium question
					movePlayer(-2); // Move back 2 tiles
				} else if (level == 3) { // Hard question
					movePlayer(-3); // Move back 3 tiles
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading question dialogue: " + e.getMessage());
		}

	}

	private void updatePlayerPositionVisuals(int newPosition) {

		int maxPosition = 10 * 10; // since your board is 10*10

		// Ensure the new position does not exceed the maximum position
		if (newPosition > maxPosition) {
			newPosition = maxPosition;
		}

		// Convert the 1-indexed position to 0-indexed for calculation purposes
		int zeroIndexedPosition = newPosition - 1;
		System.out.println("zeroIndexedPosition is : " + zeroIndexedPosition);

		// Determine the row and column based on zeroIndexedPosition
		int row = zeroIndexedPosition / 10;
		int col = zeroIndexedPosition % 10;
		System.out.println("col is : " + col);
		System.out.println("row is : " + row);

		// Adjust for zigzag pattern by checking if the row number is odd
		if (row % 2 != 0) {// number of row is odd
			col = 9 - col;
		}

		// Debugging output
		System.out.println("Player new position: " + newPosition + " (Row: " + row + ", Col: " + col + ")");
		System.out.println();
		// Clear any existing player object from the previous position
		clearPreviousPlayerPosition(currentplayer);

		// Ensure we don't try to access out of bounds indices
		if (row >= 0 && row < 10 && col >= 0 && col < 10) {

			buttonMatrix[row][col].setGraphic(currentplayer.getObject());

		} else {
			System.out.println("Calculated position out of bounds: Row " + row + ", Col " + col);
		}
	}

	private void clearPreviousPlayerPosition(Player currentplayer) {
		int previousPosition = currentplayer.getPosition();
		int row = previousPosition / 10;
		int col = previousPosition % 10;
		if (row % 2 == 1) {
			col = 9 - col;
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

}
