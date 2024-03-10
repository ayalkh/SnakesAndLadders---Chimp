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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import model.EasyGame;
import model.GameLevel;
import model.GameSession;
import model.Ladder;
import model.Player;
import model.Question;
import model.QuestionTile;
import model.Snake;
import model.SysData2;
import model.game;

public class GameBoardEasyController {
	private EasyGame easyGame; // The game logic
	private ArrayList<Question> questions;
	private Player currentplayer; // Moved inside the class, not at declaration
	@FXML
	private ImageView greenAvatar;
	@FXML
	private ImageView greyAvatar;
	@FXML
	private ImageView purpleAvatar;
	@FXML
	private ImageView redAvatar;

	@FXML
	private ImageView blueAvatar;

	@FXML
	private ImageView yellowAvatar;
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
	private Random random = new Random();

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

	@FXML
	private ImageView rollDiceImage;
	@FXML
	ImageView toggleMusicButton;

	@FXML
	void handleToggleMusic(MouseEvent event) {
		BackgroundMusicPlayer.getInstance().toggleMusic();

	}

	// 49buttons
	@FXML
	private Button i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6, i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6, i2j0, i2j1, i2j2,
			i2j3, i2j4, i2j5, i2j6, i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6, i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6,
			i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6, i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6;

	public void initialize() {
		easyGame = EasyGame.getInstance();
		currentplayer = easyGame.getGameplayers().get(0); // Now it's safe to initialize.
		Overlay.getChildren().clear(); // Clear any existing images
		initializeBoard();
		updateBoardWithSnakes();
		updateBoardWithLadders();
		loadquestions();
		updateBoardWithQuestionTiles();

		toggleMusicButton.setCursor(Cursor.HAND);
		toggleMusicButton.setOnMouseEntered(event -> toggleMusicButton.setOpacity(0.8));
		toggleMusicButton.setOnMouseExited(event -> toggleMusicButton.setOpacity(1.5));
		rollDiceImage.setCursor(Cursor.HAND);
	}

	private void initializeBoard() {
		buttonMatrix = new Button[][] { { i0j0, i0j1, i0j2, i0j3, i0j4, i0j5, i0j6 },
				{ i1j0, i1j1, i1j2, i1j3, i1j4, i1j5, i1j6 }, { i2j0, i2j1, i2j2, i2j3, i2j4, i2j5, i2j6 },
				{ i3j0, i3j1, i3j2, i3j3, i3j4, i3j5, i3j6 }, { i4j0, i4j1, i4j2, i4j3, i4j4, i4j5, i4j6 },
				{ i5j0, i5j1, i5j2, i5j3, i5j4, i5j5, i5j6 }, { i6j0, i6j1, i6j2, i6j3, i6j4, i6j5, i6j6 } };
	}

	// not connected
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

	private void updateBoardWithLadders() {
		for (Ladder ladder : easyGame.getLaddersMap().values()) {
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
		for (Snake snake : easyGame.getSnakesMap().values()) {
			ImageView snakeImageView = snake.getImageView(); // Get the ImageView for the snake

			// Calculate the grid position for the head of the snake
			Point2D headGridPosition = calculateGridPosition(snake.getStartPosition());
			// Convert grid position to pixel position for the head
			Point2D headPixel = calculatePixelPosition(headGridPosition);

			// Calculate the grid position for the tail of the snake
			Point2D tailGridPosition = calculateGridPosition(snake.getEndPosition());
			// Convert grid position to pixel position for the tail
			Point2D tailPixel = calculatePixelPosition(tailGridPosition);

			// Set the ImageView of the snake with the head's position
			snakeImageView.setLayoutX(headPixel.getX());
			snakeImageView.setLayoutY(headPixel.getY());

			// Add the ImageView to the overlay
			Overlay.getChildren().add(snakeImageView);
		}
	}

	@FXML
	private void rollDiceAndMove() {
		Dice result = new Dice(GameLevel.EASY);
		int diceRoll = result.rollDice();
		System.out.println("rolling dice result : " + diceRoll);
		// Start dice rolling sound
		music("rollingDice.mp3");
		// Configure rotation animation
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), rollDiceImage);
		rotateTransition.setByAngle(360 * 3); // Rotate 3 times
		rotateTransition.setCycleCount(1);
		rotateTransition.setAutoReverse(false);

		rotateTransition.setOnFinished(event -> {
			// After rotation finishes, execute the remaining operations in the next pulse
			// of the JavaFX Application Thread
			Platform.runLater(() -> {
				// Set the dice image to the result
				Image diceImage = new Image(getClass().getResourceAsStream("/images/dice" + diceRoll + ".png"));
				rollDiceImage.setImage(diceImage);
				System.out.println("DICERESULT:" + diceRoll);

				// Handle specific dice roll outcomes
				if (diceRoll == 5) { // If dice roll is 5, handle the question event
					music("popQuestion.mp3");
					handleQuestionTileEvent(random.nextInt(2) + 1);

				} else {
					movePlayer(diceRoll);
				}

				// Once everything for this player's turn is done, switch to the next player
				switchToNextPlayer();
			});
		});

		// Start the rotation animation
		rotateTransition.play();
	}

	private void switchToNextPlayer() {
		currentplayer = easyGame.getGameplayers()
				.get((easyGame.getGameplayers().indexOf(currentplayer) + 1) % easyGame.getNumberofplayers());
		System.out.println("Now it's " + currentplayer.getName() + "'s turn.");
	}

	MediaPlayer mediaPlayer;

	public void music(String soundFileName) {
		String path = "/sound/" + soundFileName;
		Media h = new Media(getClass().getResource(path).toExternalForm());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.play();
	}

	public void movePlayer(int diceRoll) {
		System.out.println();
		System.out.println(currentplayer.getName() + " got : " + diceRoll + " steps ");
		System.out.println(currentplayer.getName() + " previous position is : " + currentplayer.getPosition());
		int newPosition = currentplayer.getPosition() + diceRoll;
		System.out.println("new Position before editing the playerPosition is : " + newPosition);

		// Correctly updating the position
		if (newPosition < 1) {
			newPosition = 0;
		} // Ensure the position does not go below the starting point
		if (newPosition >= 49) {
			newPosition = 49; // Assuming 49 is the winning tile
		}

		if (diceRoll != 0 && newPosition > 0)

		{
			music("playerMoving.mp3");

		}
		// music("playerMoving.mp3");
		currentplayer.setPositionAfterClimbing(newPosition); // Update this line to set the newPosition
		System.out.println(currentplayer.getName() + " current position is : " + currentplayer.getPosition());

		// Check for ladder at the new position
		Ladder ladder = easyGame.getLaddersMap().get(newPosition);
		if (ladder != null) {
			newPosition = ladder.getEndPosition();
			System.out.println("player postition before climbing the ladder : " + currentplayer.getPosition());
			currentplayer.setPositionAfterClimbing(newPosition);
			System.out.println("player postition after climbing the ladder : " + currentplayer.getPosition());
			System.out.println(currentplayer.getName() + " climbed a ladder to position: " + newPosition);
			music("ladderClimbing.mp3");

		}

		// Check for snake at the new position
		Snake snake = easyGame.getSnakesMap().get(newPosition);
		if (snake != null) {
			newPosition = snake.getEndPosition();
			System.out.println("player postition before bitten by a snake : " + currentplayer.getPosition());
			currentplayer.setPositionAfterClimbing(newPosition);
			System.out.println("player postition after bitten by a snake : " + currentplayer.getPosition());
			music("snakeBite.mp3");
		}
		if (diceRoll != 0) {
			// Check for question tile at the new position
			for (QuestionTile QT : easyGame.getQuestions()) {// check if the player stepped is on a question tile//

				if (newPosition == QT.getPosition()) {
					System.out.println("pop question");
					music("popQuestion.mp3");
					handleQuestionTileEvent(QT.getLevel());
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
			game newGame = new game(SysData2.getNextgameID(), 1, currentplayer.getName());
			if (SysData2.getInstance().addGame(newGame)) {
				System.out.println("game added successfully!");
			}

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

	private void updatePlayerPositionVisuals(int newPosition) {

		int maxPosition = 7 * 7; // since your board is 7x7

		// Ensure the new position does not exceed the maximum position
		if (newPosition > maxPosition) {
			newPosition = maxPosition;
		}

		// Convert the 1-indexed position to 0-indexed for calculation purposes
		int zeroIndexedPosition = newPosition - 1;
		System.out.println("zeroIndexedPosition is : " + zeroIndexedPosition);

		// Determine the row and column based on zeroIndexedPosition
		int row = zeroIndexedPosition / 7;
		int col = zeroIndexedPosition % 7;
		System.out.println("col is : " + col);
		System.out.println("row is : " + row);

		// Adjust for zigzag pattern by checking if the row number is odd
		if (row % 2 != 0) {// number of row is odd
			col = 6 - col; // 6 is used instead of 7 here due to zero-indexing of col
		}

		// Debugging output
		System.out.println("Player new position: " + newPosition + " (Row: " + row + ", Col: " + col + ")");
		System.out.println();
		// Clear any existing player object from the previous position
		clearPreviousPlayerPosition(currentplayer);

		// Ensure we don't try to access out of bounds indices
		if (row >= 0 && row < 7 && col >= 0 && col < 7) {

			buttonMatrix[row][col].setGraphic(currentplayer.getObject());

		} else {
			System.out.println("Calculated position out of bounds: Row " + row + ", Col " + col);
		}
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
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(redObject);
				i++;
				break;
			case "blue":
				blueAvatar.setEffect(resetSaturation);
				blueObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(blueObject);
				i++;
				break;
			case "green":
				greenAvatar.setEffect(resetSaturation);
				greenObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(greenObject);
				i++;
				break;
			case "purple":
				purpleAvatar.setEffect(resetSaturation);
				purpleObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(purpleObject);
				i++;
				break;
			case "grey":
				greyAvatar.setEffect(resetSaturation);
				greyObject.setVisible(true);
				easyGame.getGameplayers().get(i).setColor(color);
				easyGame.getGameplayers().get(i).setObject(greyObject);
				i++;
				break;
			case "yellow":
				yellowAvatar.setEffect(resetSaturation);
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
}