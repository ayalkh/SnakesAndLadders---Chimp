package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
        int[] ladderLengths = {1, 2, 3, 4}; // Ladder lengths according to the number of rows they span

        for (int length : ladderLengths) {
            boolean placed = false;
            while (!placed) {
                // Generate a random start position that does not conflict with snakes
                int startPosition = getRandomLadderStartPosition(length);
                int endPosition = startPosition + (length * size); // Calculate the end position based on ladder length

                // Check if the calculated end position is valid and does not conflict with snakes
                if (endPosition <= size * size && !snakesMap.containsKey(startPosition) && !snakesMap.containsKey(endPosition)) {
                    Ladder ladder = new Ladder(startPosition, endPosition, length);
                    laddersMap.put(startPosition, ladder);
                    placed = true;
                }
            }
        }
    }
    private int getRandomLadderStartPosition(int ladderLength) {
        int maxPosition = size * size - ladderLength * size; // Ensure the ladder doesn't go off the board
        int startPosition;
        do {
            startPosition = random.nextInt(maxPosition) + 1;
        } while (startPosition % size == 0); // Ensure not at the right edge of the board
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
            // Update the board with the snake - convert startPosition to row & column if necessary
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
                endPosition = 1; // Back to start
                break;
        }
        return Math.max(1, endPosition); // Ensure end position is not less than 1
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
