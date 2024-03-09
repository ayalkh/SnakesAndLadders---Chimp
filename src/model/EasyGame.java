package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EasyGame {
	private static EasyGame easyGameInstance = null;

	private List<QuestionTile> questions = new ArrayList<>();
	private int numberofplayers;
	private List<Player> gameplayers = new ArrayList<>();
	private Tile[][] board;
	private final int size = 7; // Easy level board size is 7x7
	private final Random random = new Random();
	private Map<Integer, Snake> snakesMap = new HashMap<>();
	private Map<Integer, Ladder> laddersMap = new HashMap<>();

	public EasyGame() {

		this.board = new Tile[size][size];
		initializeBoard();
		placeSnakes();
		placeLadders();
		placeSpecialTiles();
		placequestions();// If you have question tiles or surprise tiles
		// Add other initialization as needed
	}

	public static EasyGame getInstance() {
		if (easyGameInstance == null) {
			easyGameInstance = new EasyGame();
		}
		return easyGameInstance;
	}

	public int getNumberofplayers() {
		return numberofplayers;
	}

	public void setNumberofplayers(int numberofplayers) {
		this.numberofplayers = numberofplayers;
	}

	public List<Player> getGameplayers() {
		return gameplayers;
	}

	public void setGameplayers(List<Player> gameplayers) {
		this.gameplayers = gameplayers;
	}

	private void initializeBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = new Tile(i, j); // Assuming your Tile constructor takes row and column as arguments
			}
		}
	}

	private void placeLadders() {
		laddersMap.clear();
		int[] ladderLengths = { 1,2,3,4}; // Ladder lengths

		for (int length : ladderLengths) {
			boolean placed = false;
			while (!placed) {
				int startPosition = getRandomLadderStartPosition(length);
	

				int endPosition = calculateLaddersEndPosition(startPosition,length);
				int startRow = (startPosition) / size;
				int col = (startPosition) % size;

				// Adjust constraints for ladder start position based on ladder length
				boolean isStartPositionValid = startRow >= 0 && startRow <= size - length;

				// Check constraints for snake and ladder overlap
				boolean isOverlapFree = !(snakesMap.containsKey(startPosition) || snakesMap.containsKey(endPosition)
						|| laddersMap.containsKey(endPosition));

				if (isStartPositionValid && isOverlapFree) {
					// Place the ladder
					

					Ladder ladder = new Ladder(startPosition, endPosition, length,"easy");
					laddersMap.put(startPosition, ladder);
					board[startRow][col].setLadder(ladder); // Set ladder on the tile
					placed = true;
				}
			}
		}
	}


	private int calculateLaddersEndPosition(int startPosition, int lenght) {
	    int endPosition = startPosition; // Default

	    // Calculate the number of rows to move back based on the color
	    int rowsToMoveup = 0;
	    switch (lenght) {
	        case 1:
	        	rowsToMoveup = 1;
	            break;
	        case 2:
	        	rowsToMoveup = 2;
	            break;
	        case 3:
	        	rowsToMoveup = 3;
	            break;
	        case 4:
	        	rowsToMoveup = 4; // Move back to start of the board
	        	break;
	    }

	    // Calculate the row and column position
	    int currentRow = (startPosition - 1) / size;
	    int currentColumn = (startPosition - 1) % size;

	    // Calculate the end row
	    int endRow = currentRow + rowsToMoveup;

	    // If moving back stays within the board
	    if (endRow <=6 ) {
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
	private int getRandomLadderStartPosition(int ladderLength) {
	    HashSet<Integer> triedColumns = new HashSet<>();
	    int startPosition;
	    int attempts = 0;
	    int maxRowForStart = size - ladderLength; // The highest row from the bottom that a ladder can start

	    while (true) {
	        int column = random.nextInt(size);
	        if (triedColumns.contains(column)) {
	            // If all columns have been tried, we cannot place any more ladders of this length
	            if (triedColumns.size() == size) {
	                throw new IllegalStateException("Unable to find valid ladder start position. Board may be full or constraints too tight.");
	            }
	            continue; // Skip this column as we've already tried all positions in it
	        }

	        // Calculate valid rows for the selected column considering ladder length
	        ArrayList<Integer> validRows = new ArrayList<>();
	        for (int row = 0; row <= maxRowForStart; row++) {
	            int potentialStart = (row * size) + column + 1;
	            if (row % 2 != 0) { // adjust for zigzag
	                potentialStart = (row * size) + (size - 1 - column) + 1;
	            }
	            if (isPositionFree(potentialStart, ladderLength)) {
	                validRows.add(row);
	            }
	        }

	        if (validRows.isEmpty()) {
	            triedColumns.add(column); // No valid positions in this column
	            continue;
	        }

	        // Randomly select a valid row for the ladder start
	        int randomRowIndex = validRows.get(random.nextInt(validRows.size()));
	        startPosition = (randomRowIndex * size) + column + 1;
	        if (randomRowIndex % 2 != 0) { // adjust for zigzag
	            startPosition = (randomRowIndex * size) + (size - 1 - column) + 1;
	        }

	        return startPosition;
	    }
	}

	private boolean isPositionFree(int startPosition, int ladderLength) {
	    // Check if the startPosition or any tile up to the length of the ladder is occupied
	    for (int i = 0; i < ladderLength; i++) {
	        int positionToCheck = startPosition + (i * size);
	        if (snakesMap.containsKey(positionToCheck) || laddersMap.containsKey(positionToCheck)) {
	            return false; // Position is not free if any part of it is occupied
	        }
	    }
	    int endPosition = startPosition + (ladderLength * size);
	    if (endPosition > size * size) {
	        return false; // Ladder end exceeds board size
	    }
	    return !snakesMap.containsKey(endPosition) && !laddersMap.containsKey(endPosition);
	}


	private void placeSpecialTiles() {
		// Place question tiles and surprise tiles, if any
	}

	private void placeSnakes() {
		snakesMap.clear();
		Map<String, Integer> snakePositions = generateSnakePositions();

		for (Map.Entry<String, Integer> entry : snakePositions.entrySet()) {
			String color = entry.getKey();
			int startPosition = entry.getValue();
			int endPosition = calculateEndPosition(startPosition, color);

			Snake snake = new Snake(startPosition, endPosition, color, Math.abs(startPosition - endPosition));
			snakesMap.put(startPosition, snake);
			int row = (startPosition) / size;
			int col = (startPosition) % size;
			board[row][col].setSnake(snake); // Set snake on the tile
		}
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


	private Map<String, Integer> generateSnakePositions() {
		Map<String, Integer> positions = new HashMap<>();
		Set<Integer> occupiedPositions = new HashSet<>();

		// Assume the board has 'size * size' number of squares
		int maxPosition = size * size;

		// Generate positions for each snake, ensuring no overlapping
		positions.put("yellow", getRandomPosition(size, maxPosition, occupiedPositions));
		occupiedPositions.add(positions.get("yellow"));

		positions.put("green", getRandomPosition(2 * size, maxPosition, occupiedPositions));
		occupiedPositions.add(positions.get("green"));

		positions.put("blue", getRandomPosition(3 * size, maxPosition, occupiedPositions));
		occupiedPositions.add(positions.get("blue"));

		// Generate a list of all possible positions
		Set<Integer> allPositions = new HashSet<>();
		for (int i = 2; i <= maxPosition; i++) { // Start from 2 to avoid the first position
			allPositions.add(i);
		}

		// Remove occupied positions
		allPositions.removeAll(occupiedPositions);

		// The red snake always starts from a random position that is not an occupied
		// position
		int redSnakePosition = getRandomPositionFromSet(allPositions);
		positions.put("red", redSnakePosition);
		occupiedPositions.add(redSnakePosition);

		return positions;
	}

	private int getRandomPositionFromSet(Set<Integer> availablePositions) {
		int index = random.nextInt(availablePositions.size());
		Iterator<Integer> iter = availablePositions.iterator();
		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();
	}

	private int getRandomPosition(int min, int max, Set<Integer> occupiedPositions) {
		int position;
		do {
			position = random.nextInt(max - min + 1) + min;
		} while (occupiedPositions.contains(position) || position % size == 0);
		return position;
	}

	public Map<Integer, Ladder> getLaddersMap() {
		return laddersMap;
	}

	public void setLaddersMap(Map<Integer, Ladder> laddersMap) {
		this.laddersMap = laddersMap;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	public int getSize() {
		return size;
	}

	public Random getRandom() {
		return random;
	}

	public Map<Integer, Snake> getSnakesMap() {
		return snakesMap;
	}

	public void setSnakesMap(Map<Integer, Snake> snakesMap) {
		this.snakesMap = snakesMap;
	}

	private void placequestions() {
		questions.clear();

		Set<Integer> occupiedPositions = new HashSet<>();
		int maxPosition = size * size;
		int i = 0;
		while (i < 3) {
			QuestionTile QT = new QuestionTile(getRandomQuestion(),
					getRandomPosition(size, maxPosition, occupiedPositions),i+1);
			if (!(snakesMap.containsKey(QT.getPosition()) || snakesMap.containsKey(QT.getPosition())
					|| laddersMap.containsKey(QT.getPosition()))) {
				questions.add(QT);
				occupiedPositions.add(questions.get(i).getPosition());
				int row = (QT.getPosition()) / size;
				int col = (QT.getPosition()) % size;
				board[row][col].setQuestiontile(QT); // Set snake on the tile
				i++;
			}

		}

	}

	public List<QuestionTile> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionTile> questions) {
		this.questions = questions;
	}

	private Question getRandomQuestion() {

		return null;
	}

}