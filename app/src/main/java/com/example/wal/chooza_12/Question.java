package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class Question {
    private int Question_ID;
    private int Test_ID;
    private String Statement;
    private String Result;

    public int getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(int question_ID) {
        Question_ID = question_ID;
    }

    public int getTest_ID() {
        return Test_ID;
    }

    public void setTest_ID(int test_ID) {
        Test_ID = test_ID;
    }

    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

}
