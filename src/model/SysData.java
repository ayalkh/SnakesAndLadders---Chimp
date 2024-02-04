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

public class SysData {

	static final String QUESTIONS_FILENAME = "Questions.json";
	static final String QUESTIONS_JSONOBJECT = "questions";
	static final int MAX_CAPACITY_QUESTIONS = 100;
	static final String USER_PREFS_FILENAME = "UserPrefs.json";
	static final String QUESTIONS_SUGGESTIONS_JSONOBJECT = "qSuggestions";
	static final String QUESTIONS_HISTORY_JSONOBJECT = "qHistory";
	static final int MAX_CAPACITY_USERPREFS = 10;
    private static int questionID = 0;  // Static variable to hold the next question ID

	private static SysData instance;
	private List<Question> questionsList;
	private JSONArray questionsListJson;
	private List<String> suggestionsList;
	private List<String> historyList;
	private JSONArray historyJson;
	private JSONArray suggestionsJson;

	public static SysData getInstance() {
		if (instance == null) {
			instance = new SysData();
		  instance.questionsListJson = new JSONArray();
		  instance.questionsList = new ArrayList<>();
	        instance.suggestionsList = new ArrayList<>();
	        instance.historyList = new ArrayList<>();
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

	        return (JSONArray) jsonObject.get(QUESTIONS_JSONOBJECT);
	    } catch (FileNotFoundException e) {
	        // If the file is not found, create a new empty JSON array and write it back
	        writeJsonFile(new JSONArray(), QUESTIONS_JSONOBJECT, fileName);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return new JSONArray(); // Return an empty JSON array if any exceptions occur
	}


	// --------------------------------------------------- Question Functions ---------------------------------------------------

	public List<Question> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<Question> questionsList) {
		this.questionsList = questionsList;
	}

	/**
	 * A function to read the questions, it calls readJsonfile function, and passes
	 * the suitable parameters to it, the function adds questions to jsonArray
	 * called questionsListJson, and this function converts the objects from JSON to
	 * Question sets an id to each question, and adds them to a list called
	 * questionsList
	 * 
	 */
	   public void readQuestions() {
	        questionsList = new ArrayList<>();
	        questionsListJson = readJsonFile(QUESTIONS_FILENAME);
	        if (questionsListJson == null) {

	            this.questionsListJson = new JSONArray();
	            // If no questions are present, initialize questionID to 1
	            SysData.questionID = 1; 
	        } else {

	            Question q;
	            int maxId = 0; // Variable to track the highest ID
	            for (Object o : this.questionsListJson) {
	                JSONObject exploreObject = (JSONObject) o;
	                q = new Question(exploreObject);
	                questionsList.add(q);
	                maxId = Math.max(maxId, q.getQuestionID()); // Update maxId if necessary
	            }
	            // Set the next ID to be the max ID found plus one
	            SysData.questionID = maxId + 1; 
	        }
	    }

	/**
	 * Gets a question and adds it to questionsList, and then writes it to questions
	 * json file
	 * 
	 * @param question
	 * @return 
	 */
	   public boolean addQuestion(Question question) {
		    if (this.questionsList.size() >= MAX_CAPACITY_QUESTIONS) {
		        this.questionsList.remove(0);
		        this.questionsListJson.remove(0);
		    }
		        
		    question.setQuestionID(SysData.getNextQuestionID());
		    this.questionsListJson.add(question.toJSON());
		    this.questionsList.add(question);
		    try {
		        writeJsonFile(this.questionsListJson, QUESTIONS_JSONOBJECT, QUESTIONS_FILENAME);
		        SysData.incrementQuestionID(); 
		        return true;
		    } catch (Exception e) {
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
	public boolean updateQuestion(int qID, Question question) {
	    int index = getQuestionIndexByID(qID);
	    if (index != -1) {
	        // Update the question in the list
	        question.setQuestionID(qID); // Ensure the updated question has the correct ID
	        this.questionsList.set(index, question);
	        
	        // Update the JSON representation
	        this.questionsListJson.set(index, question.toJSON());
	        
	        // Write to the JSON file
	        try {
	            writeJsonFile(this.questionsListJson, QUESTIONS_JSONOBJECT, QUESTIONS_FILENAME);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    return false; // Return false if the question ID was not found
	}


	/**
	 * Gets a questionID, retrieves the question, deletes it and then updates
	 * questionsJson file and questionsJsonList
	 * 
	 * @param questionID
	 * @return
	 */
	public boolean deleteQuestion(int questionID) {
	    Question toBeDeleted = getQuestionByID(questionID);
	    if (toBeDeleted == null) {
	        return false; // If the question with the given ID is not found, return false
	    } else {
	        // Remove from the list of Question objects
	        this.questionsList.remove(toBeDeleted);

	        // Find the index of the JSONObject in questionsListJson with the same questionID
	        int indexToRemove = -1;
	        for (int i = 0; i < this.questionsListJson.size(); i++) {
	            JSONObject questionObject = (JSONObject) this.questionsListJson.get(i);
	            if (questionObject != null && questionObject.get("questionID") != null) {
	                int id = ((Number) questionObject.get("questionID")).intValue();
	                if (id == questionID) {
	                    indexToRemove = i;
	                    break;
	                }
	            }
	        }

	        // If a matching index was found, remove the object from the JSON array
	        if (indexToRemove != -1) {
	            this.questionsListJson.remove(indexToRemove);
	        } else {
	            return false; // If no matching JSONObject was found, return false
	        }

	        // Write the updated JSON array back to the file
	        writeJsonFile(this.questionsListJson, QUESTIONS_JSONOBJECT, QUESTIONS_FILENAME);
	        return true;
	    }
	}


	public Question getQuestionByID(int questionID) {
		for (Question q : this.questionsList) {
			if (q.getQuestionID() == questionID)
				return q;
		}
		return null;
	}

	public int getQuestionIndexByID(int questionID) {
		for (int i = 0; i < this.questionsList.size(); i++) {
			if (questionsList.get(i).getQuestionID() == questionID) {
				return i;
			}
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	private void updateQuestionsJsonList() {
		this.questionsListJson = new JSONArray();
		for (Question q : this.questionsList) {
			this.questionsListJson.add(q.toJSON());
		}
	}

	public boolean isDuplicateQuestion(String questionBody) {
		return this.questionsList.stream().filter(o -> o.getQuestion().equals(questionBody)).findFirst().isPresent();
	}
	   /**
     * Gets the next unique question ID.
     * 
     * @return The next question ID.
     */
	public static int getNextQuestionID() {
	    return questionID; 
	}

	public static void incrementQuestionID() {
	    questionID++; // Call this method only when a new question is successfully added
	}
	// --------------------------------------------------- Question search Functions
	// ---------------------------------------------------

	public List<String> getHistory() {
		return this.historyList;
	}

	public Set<String> getSuggestions() {
		return new HashSet<>(this.suggestionsList);
	}

	public void addHistory(String str) {
		if (this.historyList.size() >= MAX_CAPACITY_USERPREFS) {
			historyList.remove(0);
			historyJson.remove(0);
		}
		if(historyList.stream().filter(s -> equals(str)).findFirst().isPresent()) {
			historyList.remove(str);
			historyJson.remove(str);
		}
		historyJson.add(str);
		historyList.add(str);
		writeUserPrefJson();
	}

	@SuppressWarnings("unchecked")
	public void addSuggestion(String suggestion) {
		suggestionsJson.add(suggestion);
		suggestionsList.add(suggestion);
		writeUserPrefJson();
	}

	@SuppressWarnings("unchecked")
	private void writeUserPrefJson() {
		try (FileWriter file = new FileWriter(USER_PREFS_FILENAME)) {
			JSONObject obj = new JSONObject();
			obj.put(QUESTIONS_SUGGESTIONS_JSONOBJECT, this.suggestionsList);
			obj.put(QUESTIONS_HISTORY_JSONOBJECT, this.historyList);
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readUserPrefs() {
		this.historyList = new ArrayList<String>();
		this.suggestionsList = new ArrayList<String>();
		this.historyJson = readJsonFile(QUESTIONS_HISTORY_JSONOBJECT);
		this.suggestionsJson = readJsonFile(QUESTIONS_SUGGESTIONS_JSONOBJECT);
		if (historyJson == null) {
			this.historyJson = new JSONArray();
		} else {
			for (int i = 0; i < this.historyJson.size(); i++) {
				String exploreObject = (String) this.historyJson.get(i);
				historyList.add(exploreObject);
			}
		}
		if (suggestionsJson == null) {
			this.suggestionsJson = new JSONArray();
		} else {
			for (int i = 0; i < this.suggestionsJson.size(); i++) {
				String exploreObject = (String) this.suggestionsJson.get(i);
				suggestionsList.add(exploreObject);
			}
		}

	}


}
