package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HardGame {
    private static HardGame hardGame = null;
    private List<QuestionTile> questions = new ArrayList<>();
    private int numberOfPlayers;
    private List<Player> gamePlayers = new ArrayList<>();
    private Tile[][] board;
    private final int size = 10; 
    private final Random random = new Random();
    private Map<Integer, Snake> snakesMap = new HashMap<>();
    private Map<Integer, Ladder> laddersMap = new HashMap<>();
    
	
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
