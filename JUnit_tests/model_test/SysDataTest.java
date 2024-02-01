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
        sysData = SysData.getInstance(); // Assuming SysData is a singleton
        sysData.setQuestionsList(new ArrayList<>()); // Assuming there's a method to set the question list
    }

    @Test
    public void testAddQuestion() {
        List<String> answers = Arrays.asList("Answer1", "Answer2", "Answer3", "Answer4");
        Question question = new Question("What is the capital of France?", answers, 2, 1, "Geography");
        sysData.addQuestion(question); // Assuming SysData has a method to add questions
        Assertions.assertFalse(sysData.getQuestionsList().isEmpty(), "The questions list should not be empty after adding a question.");
        // Assume Question class has an overridden equals() method
        Assertions.assertTrue(sysData.getQuestionsList().contains(question), "The question should be present in the questions list.");
    }

    @Test
    public void testDeleteQuestion() {
        List<String> answers = Arrays.asList("a", "b", "c", "d");
        Question question = new Question("Dummy question", answers, 1, 1, "Dummy");
        sysData.addQuestion(question);
        int questionId = question.getQuestionID(); // Get the actual ID after adding the question
        sysData.deleteQuestion(questionId); // Assuming SysData has a method to delete questions by ID
        Assertions.assertNull(sysData.getQuestionByID(questionId), "The question should be deleted."); // Assuming SysData has a method to retrieve a question by ID
    }

    @Test
    public void testUpdateQuestion() {
        List<String> answers = Arrays.asList("Answer1", "Answer2", "Answer3", "Answer4");
        Question originalQuestion = new Question("Original Question?", answers, 1, 1, "Category");
        sysData.addQuestion(originalQuestion);

        int questionId = originalQuestion.getQuestionID();
        Question updatedQuestion = new Question("Updated Question?", answers, 1, 1, "Category");
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
