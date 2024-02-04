package model;
import java.util.ArrayList;
	import java.util.List;
import java.util.Random;


import model.Countdowntmer;
import model.Player;
public class game {

private int size;
private Player currentPlayer;
private ArrayList<Player> players;
private Countdowntmer countdowntimer;
private Dice dice;





	    // Constructor
	    public game(ArrayList<Player> players, int size,Countdowntmer countdowntimer,Dice dice) {
	        this.players = players;
	        this.size = size;
	        this.countdowntimer=countdowntimer;
	        Random random = new Random();
	        this.currentPlayer = players.get(random.nextInt(players.size()));//random number related to the dice's size//);
	        this.dice=dice;
	    }
	    

	    public int getsize() {
			return this.size;
		}


		public void setsize(int size) {
			this.size = size;
		}


		public Player getCurrentPlayer() {
			return currentPlayer;
		}


		public void setCurrentPlayer(Player currentPlayer) {
			this.currentPlayer = currentPlayer;
		}


		public ArrayList<Player> getPlayers() {
			return players;
		}


		public void setPlayers(ArrayList<Player> players) {
			this.players = players;
		}


	

	

		public Dice getDice() {
			return dice;
		}


		public void setDice(Dice dice) {
			this.dice = dice;
		}


		

	    // Method to manage a player's turn, including rolling the dice and moving the player
	    public void playTurn() {
	    

	     
				while(!this.countdowntimer.startCountdown()) { //while the time is not over
				    int diceResult = this.dice.rollDice();

				    // Move the player on the game board
				    this.currentPlayer.move(diceResult);
				    if(this.currentPlayer.getPosition()>=this.getsize()) {
				    	endGame();
				    }
		   //player played or time is over
				}
	        // Switch to the next player
	        switchToNextPlayer();
	    }

	

	    // Method to move the player on the game board


	    // Method to switch to the next player after playing or after countdown//
	    private void switchToNextPlayer() {
	        int currentIndex = players.indexOf(currentPlayer);
	        int nextIndex = (currentIndex + 1) % players.size();
	        currentPlayer = players.get(nextIndex);
	       
	    }

	    // Method to end the game
	    public Player endGame() {
	    	Player winner=this.currentPlayer;
			return winner;
	    }
	       
	    }

	  


