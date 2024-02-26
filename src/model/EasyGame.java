package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.scene.image.ImageView;

public class EasyGame {
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
        placeSpecialTiles(); // If you have question tiles or surprise tiles
        // Add other initialization as needed
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
        int[] ladderLengths = {1, 2, 3, 4}; // Ladder lengths

        for (int length : ladderLengths) {
            boolean placed = false;
            while (!placed) {
                int startPosition = getRandomLadderStartPosition(length);
                int endPosition = startPosition + (length * size);

                int startRow = getRow(startPosition);
                int endRow = getRow(endPosition);

                // Adjust constraints for ladder start position based on ladder length
                boolean isStartPositionValid = startRow >= 0 && startRow <= size - length;

                // Check constraints for snake and ladder overlap
                boolean isOverlapFree = !(snakesMap.containsKey(startPosition) ||
                                          snakesMap.containsKey(endPosition) ||
                                          laddersMap.containsKey(endPosition));

                if (isStartPositionValid && isOverlapFree) {
                    // Place the ladder
                    Ladder ladder = new Ladder(startPosition, endPosition, length);
                    laddersMap.put(startPosition, ladder);
                    board[startRow][getColumn(startPosition)].setLadder(ladder); // Set ladder on the tile
                    placed = true;
                }
            }
        }
    }

    private int getRow(int position) {
        return (position - 1) / size;
    }

    private int getColumn(int position) {
        return (position - 1) % size;
    }
    private int getRandomLadderStartPosition(int ladderLength) {
        int maxRowForStart = size - ladderLength; // The maximum row a ladder of this length can start
        int startPosition;
        int row, column; // Declare 'row' and 'column' outside the loop to ensure visibility

        do {
            int maxPosition = maxRowForStart * size; // The maximum position on the board for the ladder start
            startPosition = random.nextInt(maxPosition) + 1; // Get a random start position within bounds
            row = getRow(startPosition); // Calculate the row based on the start position
            column = getColumn(startPosition); // Calculate the column based on the start position

            // Check the conditions for placing the ladder
        } while (
            column == size - 1 || // The ladder cannot start at the right edge of the board
            row > maxRowForStart // The ladder cannot start too high such that it would extend beyond the board
        );
        
        return startPosition;
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
            int row = (startPosition ) / size;
            int col = (startPosition ) % size;
            board[row][col].setSnake(snake); // Set snake on the tile
        }
    }
 

	private int calculateEndPosition(int startPosition, String color) {
        int endPosition = startPosition; // Default

        switch (color.toLowerCase()) {
            case "yellow":
                endPosition -= size; // Move back one row
                break;
            case "green":
                endPosition -= 2 * size; // Move back two rows
                break;
            case "blue":
                endPosition -= 3 * size; // Move back three rows
                break;
            case "red":
                endPosition = 0; // Back to start
                break;
        }
        return Math.max(0, endPosition); // Ensure end position is not less than 1
    }
    private Map<String, Integer> generateSnakePositions() {
        Map<String, Integer> positions = new HashMap<>();
        Set<Integer> occupiedPositions = new HashSet<>();

        // Assume the board has 'size * size' number of squares
        int maxPosition = size * size;

        // Generate positions for each snake, ensuring no overlapping
        positions.put("yellow", getRandomPosition(size, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("yellow"));
        
        positions.put("green", getRandomPosition(2*size, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("green"));
        
        positions.put("blue", getRandomPosition(3*size, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("blue"));
        
        // Generate a list of all possible positions
        Set<Integer> allPositions = new HashSet<>();
        for (int i = 2; i <= maxPosition; i++) { // Start from 2 to avoid the first position
            allPositions.add(i);
        }
        
        // Remove occupied positions
        allPositions.removeAll(occupiedPositions);

        // The red snake always starts from a random position that is not an occupied position
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



}
