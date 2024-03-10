package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MediumGame {
	private static MediumGame mediumGameInstance = null;
	private List<QuestionTile> questions = new ArrayList<>();

	private int numberOfPlayers;
	private List<Player> gamePlayers = new ArrayList<>();
	private Tile[][] board;
	private final int size = 10;
	private final Random random = new Random();
	private Map<Integer, Snake> snakesMap = new HashMap<>();
	private Map<Integer, Ladder> laddersMap = new HashMap<>();
	private SurpristTile surprise = new SurpristTile(0);

	public MediumGame() {
		this.board = new Tile[size][size];
		initializeBoard();
		placeSnakes();
		placeLadders();
		placequestions();
		placesurprise();

	}

	public static MediumGame getInstance() {
		if (mediumGameInstance == null) {
			mediumGameInstance = new MediumGame();
		}
		return mediumGameInstance;
	}

	private void initializeBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = new Tile(i, j);
			}
		}
	}

	private void placeLadders() {
		System.out.println("Placing ladders : ");
		laddersMap.clear();
		int[] ladderLengths = { 1, 2, 3, 4, 5, 6 }; // Ladder lengths for medium level

		for (int length : ladderLengths) {
			boolean placed = false;
			while (!placed) {
				int startPosition = getRandomLadderStartPosition(length);
				int endPosition = calculateLaddersEndPosition(startPosition, length);
				System.out.println("Iam in medium game");
				// Check if endPosition is not valid (e.g., out of bounds), then skip this
				// attempt
				if (endPosition == -1) {
					continue;
				}

				// Calculate the start position's row and column based on the zigzag pattern
				int startRow = (startPosition - 1) / size;
				int col = (startPosition - 1) % size;
				if (startRow % 2 == 1) { // Adjust for zigzag pattern
					col = size - 1 - col;
				}

				// Check constraints for snake and ladder overlap
				boolean isOverlapFree = !(snakesMap.containsKey(startPosition) || snakesMap.containsKey(endPosition)
						|| laddersMap.containsKey(endPosition));

				if (isOverlapFree) {
					System.out.println();
//					System.out.println("Ladder start position at : " + startPosition);
//					System.out.println("Ladder end position at : " + endPosition);
					// Place the ladder
					Ladder ladder = new Ladder(startPosition, endPosition, length, "medium");
					laddersMap.put(startPosition, ladder);
					board[startRow][col].setLadder(ladder); // Set ladder on the tile
					placed = true;
				}
			}
		}
	}

	private int calculateLaddersEndPosition(int startPosition, int length) {
		int currentRow = (startPosition - 1) / size;
		int currentColumn = (startPosition - 1) % size;
		if (currentRow % 2 == 1) { // Adjust column for zigzag if on an odd row
			currentColumn = size - 1 - currentColumn;
		}

		int endRow = currentRow + length; // Calculate the end row by adding the ladder length

		// If the ladder's end exceeds the board size, we return -1 to indicate an
		// invalid end position
		if (endRow >= size) {
			return -1;
		}

		// If the end row is odd (due to zigzag), we adjust the column to the mirror
		// position
		int endColumn = (endRow % 2 == 1) ? size - 1 - currentColumn : currentColumn;

		// Calculate the end position index
		int endPosition = (endRow * size) + endColumn + 1;
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
				// If all columns have been tried, we cannot place any more ladders of this
				// length
				if (triedColumns.size() == size) {
					throw new IllegalStateException(
							"Unable to find valid ladder start position. Board may be full or constraints too tight.");
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
		int endPosition = calculateLaddersEndPosition(startPosition, ladderLength);

		// If the endPosition is invalid, the position is not free
		if (endPosition == -1) {
			return false;
		}

		// Check if the startPosition or any tile up to the end of the ladder is
		// occupied
		for (int i = 0; i < ladderLength; i++) {
			int positionToCheck = startPosition + (i * size);

			// Adjust for zigzag
			int rowToCheck = (positionToCheck - 1) / size;
			if (rowToCheck % 2 == 1) {
				positionToCheck = (rowToCheck * size) + (size - 1 - ((positionToCheck - 1) % size));
			}

			if (snakesMap.containsKey(positionToCheck) || laddersMap.containsKey(positionToCheck)) {
				return false; // Position is not free if any part of it is occupied
			}
		}

		return !snakesMap.containsKey(endPosition) && !laddersMap.containsKey(endPosition);
	}

	private void placeSnakes() {
		System.out.println("Placing snakes : ");
		snakesMap.clear();
		List<SnakePosition> snakePositions = generateSnakePositions();

		for (SnakePosition snakePosition : snakePositions) {
			String color = snakePosition.color;
			int startPosition = snakePosition.startPosition;
			int endPosition = calculateEndPosition(startPosition, color);
			System.out.println();
//			System.out.println("Snake start position : " + startPosition);
//			System.out.println("Snake end position : " + endPosition);
			System.out.println();
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

		// Adjustments to generate two red and two green snakes, along with one blue and
		// one yellow
		positions.addAll(generateColoredSnakePositions("red", 2, maxPosition, occupiedPositions));
		positions.addAll(generateColoredSnakePositions("green", 2, maxPosition, occupiedPositions));
		positions.add(generateColoredSnakePosition("blue", maxPosition, occupiedPositions));
		positions.add(generateColoredSnakePosition("yellow", maxPosition, occupiedPositions));

		return positions;
	}

	private List<SnakePosition> generateColoredSnakePositions(String color, int count, int maxPosition,
			Set<Integer> occupiedPositions) {
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
			// Make sure the snake does not go beyond the board
			if ((color.equals("yellow") && startPosition > 10 && startPosition <= maxPosition - 10)
					|| (color.equals("green") && startPosition > 20 && startPosition <= maxPosition - 20)
					|| (color.equals("blue") && startPosition > 30 && startPosition <= maxPosition - 30)
					|| (color.equals("red") && startPosition > 1)) {
				break;
			}
		} while (true);

		occupiedPositions.add(startPosition);
		return new SnakePosition(color, startPosition);
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

	public Map<Integer, Ladder> getLaddersMap() {
		return laddersMap;
	}

	public void setLaddersMap(Map<Integer, Ladder> laddersMap) {
		this.laddersMap = laddersMap;
	}

	private int getRandomPositionFromSet(Set<Integer> availablePositions) {
		int index = random.nextInt(availablePositions.size());
		Iterator<Integer> iter = availablePositions.iterator();
		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();
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

	public static MediumGame getMediumGameInstance() {
		return mediumGameInstance;
	}

	public static void setMediumGameInstance(MediumGame mediumGameInstance) {
		MediumGame.mediumGameInstance = mediumGameInstance;
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

	public int getSize() {
		return size;
	}

	public Random getRandom() {
		return random;
	}

	private void placequestions() {
		questions.clear();

		Set<Integer> occupiedPositions = new HashSet<>();
		int maxPosition = size * size;
		int i = 0;
		while (i < 3) {
			QuestionTile QT = new QuestionTile(getRandomQuestion(),
					getRandomPosition(size, maxPosition, occupiedPositions), i + 1);
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

	private Question getRandomQuestion() {

		return null;
	}

	private void placesurprise() {

		int maxPosition = size * size;
		int Position = 0;

		while (Position > maxPosition - 1 || Position < 1) { // to make sure to put the surprise in a good and valid
																// position
			Position = random.nextInt(maxPosition) + 1;
		}
		surprise.setPosition(Position);

		int row = (surprise.getPosition()) / size;
		int col = (surprise.getPosition()) % size;
		board[row][col].setSurprise(surprise); // Set surprise on the tile

	}

	private boolean checkposition(int position) {
		for (QuestionTile qt : questions) {
			if (qt.getPosition() == position) {
				return false;
			}
		}
		if ((snakesMap.containsKey(position) || snakesMap.containsKey(position) || laddersMap.containsKey(position))) {
			return false;
		}
		return true;
	}

	public SurpristTile getSurprise() {
		return surprise;
	}

	public void setSurprise(SurpristTile surprise) {
		this.surprise = surprise;
	}
}