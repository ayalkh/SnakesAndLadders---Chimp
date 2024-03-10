package model;
import java.util.ArrayList;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class game {
private int id;
private int level;
private String winner;




	    

	


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public int getLevel() {
			return level;
		}


		public void setLevel(int level) {
			this.level = level;
		}


		public String getWinner() {
			return winner;
		}


		public void setWinner(String winner) {
			this.winner = winner;
		}


		public game(int id, int level, String winner) {
			super();
			this.id = id;
			this.level = level;
			
			this.winner = winner;
		}
		public game(JSONObject jsonObject) {
			try {
				fromJSON(jsonObject);
			} catch (Exception e) {

			}

		}
		public void fromJSON(JSONObject jsonObject) {
		    this.id = ((Long) jsonObject.get("id")).intValue();
		    this.level = ((Long) jsonObject.get("level")).intValue();

			

			this.winner = jsonObject.get("winner").toString();
		
		}

	
		@SuppressWarnings("unchecked")
		public JSONObject toJSON() {
		    JSONObject gameObj = new JSONObject();
		    JSONArray jsonArray = new JSONArray();

		    gameObj.put("gameID", this.id); // This line was missing
		    gameObj.put("level", this.level);
		    gameObj.put("winer", this.winner);
		   
		    
		    return  gameObj;
		}
	



		

	
 
	    }

	  


