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
import model.Player;
import model.QuestionTile;
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
	Player currentplayer=Main.easygame.getGameplayers().get(0);
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
		updateBoardWithQuestionTiles();
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
		currentplayer = Main.easygame.getGameplayers().get((Main.easygame.getGameplayers().indexOf(currentplayer) + 1) % Main.easygame.getNumberofplayers());
	}

	public void movePlayer(int steps) {
		// Update the current player position
		 System.out.println((currentplayer.getName()  + " got: " + steps));

		int currentposition=currentplayer.getPosition()+steps;
		

		// Check if landed on a snake
		if (easyGame.getSnakesMap().containsKey(currentposition)) {
			System.out.println(easyGame.getSnakesMap().keySet());
			Snake snake = easyGame.getSnakesMap().get(currentposition);
			String snakeColor = snake.getColor().toLowerCase();

			switch (snakeColor) {
			case "yellow":
				currentposition -= 7; // Move back one row
				break;
			case "green":
				currentposition -= 14; // Move back two rows
				break;
			case "blue":
				currentposition -= 21; // Move back three rows
				break;
			case "red":
				currentposition = 0; // Back to start
				break;
			}

			currentposition = Math.max(0, currentposition); // Ensure not less than 0
			System.out.println("Hit a " + snakeColor + " snake! Moved to position: " + (currentposition + 1));
		}
		// button
		if (currentposition >= 49) {
			currentposition = 48; // Zero-based index for 49th button
			Alerts.alertBox(AlertType.INFORMATION, "Congratulations", "You Win",
					currentplayer.getName()+"  has won!!" );
		}
		// Clear the previous position of the current player
		clearPreviousPlayerPosition(currentplayer);
Main.easygame.getGameplayers().get(Main.easygame.getGameplayers().indexOf(currentplayer)).setPosition
		(currentposition);
		// Convert the currentPlayerPosition to matrix indices
		int row = currentposition / 7;
		int col = currentposition % 7;

		if (row % 2 == 1) {
			col = 6 - col;
		}

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
//				for (String color : selectedColors)
//					System.out.println(color);

				
				int i=0;// Set visible only the objects that match the selected colors
				for (String color : colors) {
					
					switch (color.toLowerCase()) {
					case "red":
						redObject.setVisible(true);
						Main.easygame.getGameplayers().get(i).setColor(color);
						Main.easygame.getGameplayers().get(i).setObject(redObject);
						i++;
						break;
					case "blue":
						blueObject.setVisible(true);
						Main.easygame.getGameplayers().get(i).setColor(color);
						Main.easygame.getGameplayers().get(i).setObject(blueObject);
						i++;
						break;
					case "green":
						greenObject.setVisible(true);
						Main.easygame.getGameplayers().get(i).setColor(color);
						Main.easygame.getGameplayers().get(i).setObject(greenObject);
						i++;
						break;
					case "purple":
						purpleObject.setVisible(true);
						Main.easygame.getGameplayers().get(i).setColor(color);
						Main.easygame.getGameplayers().get(i).setObject(purpleObject);
						i++;
						break;
					case "grey":
						greyObject.setVisible(true);
						Main.easygame.getGameplayers().get(i).setColor(color);
						Main.easygame.getGameplayers().get(i).setObject(greyObject);
						i++;
						break;
					case "yellow":
						yellowObject.setVisible(true);
						Main.easygame.getGameplayers().get(i).setColor(color);
						Main.easygame.getGameplayers().get(i).setObject(yellowObject);
						i++;
						break;
					}
				}
			}
			private void updatePlayerPositions() {
			    // Update the player position based on their current state
			    int player1Row = currentplayer.getPosition() / 7;
			    int player1Col = currentplayer.getPosition() % 7;
			    if (player1Row % 2 == 1) {
			        player1Col = 6 - player1Col;
			    }

			    

			  

			    // Set the graphics for the new positions
			    buttonMatrix[player1Row][player1Col].setGraphic(currentplayer.getObject());
			 
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