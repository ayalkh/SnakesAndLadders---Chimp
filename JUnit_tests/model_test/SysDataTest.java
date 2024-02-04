package model_test;

import model.Question;
import model.SysData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SysDataTest {

    private SysData sysData;

    @BeforeEach
    public void setUp() {
        sysData = SysData.getInstance();
        sysData.readQuestions(); 
    }

    //***************Adding TEST*******************
    @Test
    public void testAddQuestion() {
        // Test setup with mock data
        Question mockQuestion = createMockQuestion();
        int initialListSize = sysData.getQuestionsList().size();

        // Add question
        boolean addResult = sysData.addQuestion(mockQuestion);
        int newListSize = sysData.getQuestionsList().size();

        // Assertions
        Assertions.assertTrue(addResult, "Question should be added successfully.");
        Assertions.assertEquals(initialListSize + 1, newListSize, "The questions list size should increase by 1.");
        Assertions.assertTrue(sysData.getQuestionsList().contains(mockQuestion), "The question should be present in the questions list.");
    }

    //***************DELETE TEST*******************
    @Test
    public void testDeleteQuestion() {
        Question mockQuestion = createMockQuestion();
        sysData.addQuestion(mockQuestion);

        int questionId = mockQuestion.getQuestionID();
        boolean deleteResult = sysData.deleteQuestion(questionId);
        Assertions.assertTrue(deleteResult, "Question should be deleted successfully.");
        Assertions.assertFalse(sysData.getQuestionsList().contains(mockQuestion), "The question should not be present in the questions list after deletion.");
    }

    //***************UPDATE TEST*******************
    @Test
    public void testUpdateQuestion() {
        Question originalQuestion = createMockQuestion();
        sysData.addQuestion(originalQuestion);

        int questionId = originalQuestion.getQuestionID();
        Question updatedQuestion = createMockQuestion(); 
        updatedQuestion.setQuestionID(questionId); // Ensure the ID is the same

        boolean updateResult = sysData.updateQuestion(questionId, updatedQuestion);

        Assertions.assertTrue(updateResult, "The question should have been updated successfully.");

        Question retrievedQuestion = sysData.getQuestionByID(questionId);
        Assertions.assertEquals(updatedQuestion, retrievedQuestion, "The updated question should match the retrieved question.");
    }


    private Question createMockQuestion() {
        List<String> answers = Arrays.asList(
                "Answer 1",
                "Answer 2",
                "Answer 3",
                "Answer 4"
        );
        // Use a temporary ID for creation that doesn't affect the static questionID
        int tempID = sysData.getQuestionsList().isEmpty() ? 1 : sysData.getQuestionsList().get(sysData.getQuestionsList().size() - 1).getQuestionID() + 1;
        return new Question(
                tempID,
                "Mock Question",
                answers,
                1, //  correct answer
                1, // difficulty Level
                "Mock Team" // Team name
        );
    }



}
