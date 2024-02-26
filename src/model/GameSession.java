package model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
	private int numberOfPlayers;
	private List<Player> players = new ArrayList<Player>();
	private GameLevel difficultyLevel;
	private List<String> playerNames; // Add this property

	public GameSession() {
		this.players = new ArrayList<>();
		this.playerNames = new ArrayList<>();
	}

	// Constructor
	public GameSession(int numberOfPlayers, GameLevel difficultyLevel) {
		this.numberOfPlayers = numberOfPlayers;
		this.difficultyLevel = difficultyLevel;
		this.players = new ArrayList<>(numberOfPlayers); // Initialize with the number of players
	}

	public void setPlayerNames(List<String> playerNames) {
		this.playerNames = playerNames;
	}

	public List<String> getPlayerNames() {
		return playerNames;
	}

	public void initializePlayers() {
		this.players.clear();
		for (String name : playerNames) {
			Player player = new Player();
			player.setName(name); // Assuming Player class has a setName method
			player.setColor(null); // Initially, the object is not selected
			player.setPosition(0);
			this.players.add(player);
		}
	}

	// Getters and Setters
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		this.players = new ArrayList<>(numberOfPlayers); // Reinitialize the list with the new number
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public GameLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(GameLevel difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	// Helper method to add a player
	public void addPlayer(Player player) {
		players.add(player);
	}

	@Override
	public String toString() {
		return "GameSession [numberOfPlayers=" + numberOfPlayers + ", players=" + players + ", difficultyLevel="
				+ difficultyLevel + ", playerNames=" + playerNames + "]";
	}

	// Helper method to get a player
	public Player getPlayer(int index) {
		if (index >= 0 && index < players.size()) {
			return players.get(index);
		}
		return null; // or throw an exception based on your error handling strategy
	}

}
