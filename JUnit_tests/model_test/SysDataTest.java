package model_test;

import model.Question;
import model.SysData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SysDataTest {

    private SysData sysData;

    @BeforeEach
    public void setUp() {
        sysData = SysData.getInstance(); 
        sysData.setQuestionsList(new ArrayList<>()); 
    }
//***************Adding TEST*******************
    @Test
    public void testAddQuestion() {
    	 List<String> answers = Arrays.asList(
    		        "Delivering working software frequently.",
    		        "Performing unit testing as part of the development process.",
    		        "Dividing the development process into a series of short iterations, where each iteration focuses on a specific set of features or requirements.",
    		        "Lack of collaboration with the customer"
    		    );
    		    Question agileQuestion = new Question(
    		        "Which of the following is not an agile best practice",
    		        answers,
    		        4, 
    		        1, // Difficulty level
    		        "Software Development"
    		    );
    		    sysData.addQuestion(agileQuestion);
    		    Assertions.assertFalse(sysData.getQuestionsList().isEmpty(), "The questions list should not be empty after adding a question.");
    		    Assertions.assertTrue(sysData.getQuestionsList().contains(agileQuestion), "The agile question should be present in the questions list.");
    }
  //***************DELETE TEST*******************

    @Test
    public void testDeleteQuestion() {
        List<String> answers = Arrays.asList(
            "Delivering working software frequently.",
            "Performing unit testing as part of the development process.",
            "Dividing the development process into a series of short iterations.",
            "Lack of collaboration with the customer"
        );
        Question question = new Question(
            "Which of the following is not an agile best practice",
            answers,
            4, // Correct answer index 
            1,  // Difficulty level
            "Software Development" // Category
        );

        sysData.addQuestion(question);
        int questionId = question.getQuestionID(); // Get the actual ID after adding the question
        sysData.deleteQuestion(questionId); // Delete the question

        Assertions.assertNull(sysData.getQuestionByID(questionId), 
            "The question should be deleted."); // Check if the question is deleted
    }

  //***************UPDATE TEST*******************

    @Test
    public void testUpdateQuestion() {

        List<String> originalAnswers = Arrays.asList(
            "Tasks to be done for the day",
            "Current obstacles",
            "Planning for the iteration",
            "Recently completed tasks"
        );
        Question originalQuestion = new Question(
            "Which of the following is not supposed to be discussed during the daily stand-up meetings (daily scrum meetings):",
            originalAnswers,
            3,  // Correct answer index
            2,  // Difficulty level
            "Scrum Practices" // Category
        );
        sysData.addQuestion(originalQuestion);

        int questionId = originalQuestion.getQuestionID();

        // Updated question with new content
        List<String> updatedAnswers = Arrays.asList(
            "Delivering working software frequently.",
            "Performing unit testing as part of the development process.",
            "Dividing the development process into a series of short iterations.",
            "Lack of collaboration with the customer"
        );
        Question updatedQuestion = new Question(
            "Which of the following is not an agile best practice",
            updatedAnswers,
            4,  // New correct answer index
            1,  // New difficulty level
            "Agile Methodologies" // New category
        );
        updatedQuestion.setQuestionID(questionId); // Set the same ID for the updated question

        // Update the question and assert the operation was successful
        boolean updateResult = sysData.updateQuestion(questionId, updatedQuestion);
        Assertions.assertTrue(updateResult, "The question should have been updated successfully.");

        // Retrieve the updated question and assert it's not null
        Question retrievedQuestion = sysData.getQuestionByID(questionId);
        Assertions.assertNotNull(retrievedQuestion, "The question retrieved should not be null.");

        // Assert that the retrieved question is equal to the updated question
        Assertions.assertEquals(updatedQuestion, retrievedQuestion, "The question should be updated.");
    }


    @AfterEach
    public void tearDown() {
        sysData.setQuestionsList(new ArrayList<>()); // Clear the questions list
        Assertions.assertTrue(sysData.getQuestionsList().isEmpty(), "The questions list should be empty after teardown.");
    }
}
