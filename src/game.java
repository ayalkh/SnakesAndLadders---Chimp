
import java.util.ArrayList;
	import java.util.List;
import java.util.Random;
public class game {

private GameBoard gameBoard;
private Player currentPlayer;
private ArrayList<Player> players;
private CountdownTmer countdowntimer;
private Dice dice;




	    // Constructor
	    public game(ArrayList<Player> players, GameBoard gameBoard,CountdownTmer countdowntimer,Dice dice) {
	        this.players = players;
	        this.gameBoard = gameBoard;
	        this.countdowntimer=countdowntimer;
	        Random random = new Random();
	        this.currentPlayer = players.get(random.nextInt(players.size()));//random number related to the dice's size//);
	        this.dice=dice;
	    }
	    

	    public GameBoard getGameBoard() {
			return gameBoard;
		}


		public void setGameBoard(GameBoard gameBoard) {
			this.gameBoard = gameBoard;
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
	    

	      while(!countdowntimer.startCountdown()) { //while the time is not over
	        int diceResult = this.dice.rollDice();

	        // Move the player on the game board
	        this.currentPlayer.move(diceResult);
	        if(this.currentPlayer.getPosition()>=this.gameBoard.getSize()) {
	        	endGame();
	        }
	      }
	        //player played or time is over

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
	    public void endGame() {
	    	Player winner=this.currentPlayer;
	    }
	       
	    }
