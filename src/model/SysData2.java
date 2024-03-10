package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SysData2 {

	static final String GAMES_FILENAME = "Games.json";
	static final String GAMES_JSONOBJECT = "games";
	static final int MAX_CAPACITY_games = 100;
	
    private static int gameID = 0;  // Static variable to hold the next question ID

	private static SysData2 instance;
	private List<game> gamesList;
	private JSONArray gamesListJson;
	

	public static SysData2 getInstance() {
		if (instance == null) {
			instance = new SysData2();
		  instance.gamesListJson = new JSONArray();
		  instance.gamesList = new ArrayList<>();
	       
		}
		return instance;
	}

	/**
	 * function to write data to json file
	 * 
	 * @param objectList the objects in json array
	 * @param objectName before array of questions in json
	 * @param fileName   name of json file
	 */
	@SuppressWarnings("unchecked")
	public void writeJsonFile(JSONArray objectList, String objectName, String fileName) {
		try (FileWriter file = new FileWriter(fileName)) {
			JSONObject obj = new JSONObject();
			obj.put(objectName, objectList);
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to read a json file (name of file is passed as an argument)
	 * 
	 * @param objectName
	 * @param fileName
	 */
	private JSONArray readJsonFile(String fileName) {
	    try (FileReader reader = new FileReader(fileName)) {
	        JSONParser parser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) parser.parse(reader);

	        return (JSONArray) jsonObject.get(GAMES_JSONOBJECT);
	    } catch (FileNotFoundException e) {
	        // If the file is not found, create a new empty JSON array and write it back
	        writeJsonFile(new JSONArray(), GAMES_JSONOBJECT, fileName);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return new JSONArray(); // Return an empty JSON array if any exceptions occur
	}


	// --------------------------------------------------- Question Functions ---------------------------------------------------

	
	/**
	 * A function to read the questions, it calls readJsonfile function, and passes
	 * the suitable parameters to it, the function adds questions to jsonArray
	 * called questionsListJson, and this function converts the objects from JSON to
	 * Question sets an id to each question, and adds them to a list called
	 * questionsList
	 * 
	 */
	   public void readQuestions() {
	        gamesList = new ArrayList<>();
	        gamesListJson = readJsonFile(GAMES_FILENAME);
	        if (gamesListJson == null) {

	            this.gamesListJson = new JSONArray();
	            // If no questions are present, initialize questionID to 1
	            SysData2.gameID = 1; 
	        } else {

	            game g;
	            int maxId = 0; // Variable to track the highest ID
	            for (Object o : this.gamesListJson) {
	                JSONObject exploreObject = (JSONObject) o;
	                g = new game(exploreObject);
	                gamesList.add(g);
	                maxId = Math.max(maxId, g.getId()); // Update maxId if necessary
	            }
	            // Set the next ID to be the max ID found plus one
	            SysData2.gameID = maxId + 1; 
	        }
	    }

	/**
	 * Gets a question and adds it to questionsList, and then writes it to questions
	 * json file
	 * 
	 * @param question
	 * @return 
	 */
	   @SuppressWarnings("unchecked")
	public boolean addGame(game game) {
		    // Load existing questions from the JSON file first
		    this.gamesListJson = readJsonFile(GAMES_FILENAME);

		    // Check if the maximum capacity is reached
		    if (this.gamesList.size() >= MAX_CAPACITY_games) {
		        this.gamesList.remove(0);
		        this.gamesListJson.remove(0); // Ensure to remove from JSON array as well
		    }
		    
		    readQuestions();

		    // Set ID and add the new question to the list and JSON array
		    game.setId(SysData.getNextQuestionID());
		    System.out.println(SysData.getNextQuestionID());
		    this.gamesList.add(game);
		    this.gamesListJson.add(game.toJSON());
		    // Write the updated JSON array (with the new question) back to the file
		    try {
		        writeJsonFile(this.gamesListJson, GAMES_JSONOBJECT,GAMES_FILENAME);
		        SysData.incrementQuestionID();
		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}


	/**
	 * Gets a question ID and updates the question after getting its index in the
	 * List, then json file is re-written
	 * 
	 * @param qID
	 * @param question
	 */
	
	public game getGameByID(int gameID) {
		readQuestions();
		System.out.println(this.gamesList);

		for (game g : this.gamesList) {
			if (g.getId() == gameID)
				return g;
		}

		return null;
	}

	public int getGameIndexByID(int gameID) {
		for (int i = 0; i < this.gamesList.size(); i++) {
			if (gamesList.get(i).getId() == gameID) {
				return i;
			}
		}
		return -1;
	}

	public static int getGameID() {
		return gameID;
	}

	public static void setGameID(int gameID) {
		SysData2.gameID = gameID;
	}

	public List<game> getGamesList() {
		return gamesList;
	}

	public void setGamesList(List<game> gamesList) {
		this.gamesList = gamesList;
	}

	public JSONArray getGamesListJson() {
		return gamesListJson;
	}

	public void setGamesListJson(JSONArray gamesListJson) {
		this.gamesListJson = gamesListJson;
	}
	public static int getNextgameID() {
	    return gameID; 
	}
}