package model;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class HardGame {
    private static HardGame hardGame = null;
    private List<QuestionTile> questions = new ArrayList<>();
    private int numberOfPlayers;
    private List<Player> gamePlayers = new ArrayList<>();
    private Tile[][] board;
    private final int size = 13; 
    private final Random random = new Random();
    private Map<Integer, Snake> snakesMap = new HashMap<>();
    private Map<Integer, Ladder> laddersMap = new HashMap<>();
    public HardGame() {
        this.board = new Tile[size][size];
   initializeBoard();
   //placeSnakes();
 //  placeLadders();
//        placeSpecialTiles();
//        placeQuestions();
        // Add other initialization as needed
    }
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile(i, j); 
            }
        }
    }
    private void placeSnakes() {
        snakesMap.clear();
        List<SnakePosition> snakePositions = generateSnakePositions();

        for (SnakePosition snakePosition : snakePositions) {
            String color = snakePosition.color;
            int startPosition = snakePosition.startPosition;
            int endPosition = calculateEndPosition(startPosition, color);

            Snake snake = new Snake(startPosition, endPosition, color, Math.abs(startPosition - endPosition));
            snakesMap.put(startPosition, snake);
            int row = startPosition / size;
            int col = startPosition % size;
            board[row][col].setSnake(snake); // Set snake on the tile
        }
    }

    private List<SnakePosition> generateSnakePositions() {
        List<SnakePosition> positions = new ArrayList<>();
        Set<Integer> occupiedPositions = new HashSet<>();
        int maxPosition = size * size;

        // Adjustments to generate two red and two green snakes, along with one blue and one yellow
        positions.addAll(generateColoredSnakePositions("red", 2, maxPosition, occupiedPositions));
        positions.addAll(generateColoredSnakePositions("green", 2, maxPosition, occupiedPositions));
        positions.addAll(generateColoredSnakePositions("blue",2, maxPosition, occupiedPositions));
        positions.addAll(generateColoredSnakePositions("yellow",2, maxPosition, occupiedPositions));

        return positions;
    }

    private List<SnakePosition> generateColoredSnakePositions(String color, int count, int maxPosition, Set<Integer> occupiedPositions) {
        List<SnakePosition> coloredPositions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            coloredPositions.add(generateColoredSnakePosition(color, maxPosition, occupiedPositions));
        }
        return coloredPositions;
    }

    private SnakePosition generateColoredSnakePosition(String color, int maxPosition, Set<Integer> occupiedPositions) {
        int startPosition;
        do {
            startPosition = getRandomPosition(1, maxPosition, occupiedPositions);
            // Adjusted constraints for a 13x13 board
            if ((color.equals("yellow") && startPosition > 13 && startPosition <= maxPosition - 13) || // Adjust for yellow snakes
                (color.equals("green") && startPosition > 26 && startPosition <= maxPosition - 26) || // Adjust for green snakes
                (color.equals("blue") && startPosition > 39 && startPosition <= maxPosition - 39) || // Adjust for blue snakes
                (color.equals("red") && startPosition > 1 && startPosition <= maxPosition - 1)) { // Minor adjustment for red snakes
                break;
            }
        } while (true);
        
        occupiedPositions.add(startPosition);
        return new SnakePosition(color, startPosition);
    }
    private int calculateEndPosition(int startPosition, String color) {
	    int endPosition = startPosition; // Default

	    // Calculate the number of rows to move back based on the color
	    int rowsToMoveBack = 0;
	    switch (color.toLowerCase()) {
	        case "yellow":
	            rowsToMoveBack = 1;
	            break;
	        case "green":
	            rowsToMoveBack = 2;
	            break;
	        case "blue":
	            rowsToMoveBack = 3;
	            break;
	        case "red":
	        	endPosition = 1; // Move back to start of the board
	    	    return endPosition;

	    }

	    // Calculate the row and column position
	    int currentRow = (startPosition - 1) / size;
	    int currentColumn = (startPosition - 1) % size;

	    // Calculate the end row
	    int endRow = currentRow - rowsToMoveBack;

	    // If moving back stays within the board
	    if (endRow >= 0) {
	        // Check if the current row is even or odd to account for the zigzag pattern
	        boolean isCurrentRowOdd = currentRow % 2 == 0;
	        boolean isEndRowOdd = endRow % 2 == 0;

	        // If the direction changes, we need to mirror the column as well.
	        if (isCurrentRowOdd != isEndRowOdd) {
	            currentColumn = size - 1 - currentColumn;
	        }

	        // Calculate the new end position based on the end row and column
	        endPosition = endRow * size + currentColumn + 1;
	    } else {
	        // If moving back would go off the board, set to the first position
	        endPosition = 1;
	    }

	    return endPosition;
	}



    private class SnakePosition {
        String color;
        int startPosition;

        SnakePosition(String color, int startPosition) {
            this.color = color;
            this.startPosition = startPosition;
        }
    }
    private int getRandomPosition(int min, int max, Set<Integer> occupiedPositions) {
		int position;
		do {
			position = random.nextInt(max - min + 1) + min;
		} while (occupiedPositions.contains(position) || position % size == 0);
		return position;
	}

	 public static HardGame getHardGame() {
		return hardGame;
	}


	public static void setHardGame(HardGame hardGame) {
		HardGame.hardGame = hardGame;
	}


	public List<QuestionTile> getQuestions() {
		return questions;
	}


	public void setQuestions(List<QuestionTile> questions) {
		this.questions = questions;
	}


	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}


	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}


	public List<Player> getGamePlayers() {
		return gamePlayers;
	}


	public void setGamePlayers(List<Player> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}


	public Tile[][] getBoard() {
		return board;
	}


	public void setBoard(Tile[][] board) {
		this.board = board;
	}


	public Map<Integer, Snake> getSnakesMap() {
		return snakesMap;
	}


	public void setSnakesMap(Map<Integer, Snake> snakesMap) {
		this.snakesMap = snakesMap;
	}


	public Map<Integer, Ladder> getLaddersMap() {
		return laddersMap;
	}


	public void setLaddersMap(Map<Integer, Ladder> laddersMap) {
		this.laddersMap = laddersMap;
	}


	public int getSize() {
		return size;
	}


	public Random getRandom() {
		return random;
	}


	public static HardGame getInstance() {
	        if (hardGame == null) {
	        	hardGame = new HardGame();
	        }
	        return hardGame;
	    }
	 
}
