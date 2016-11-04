package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class Question {
    public String getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(String question_ID) {
        Question_ID = question_ID;
    }

    public Question(String question_ID, String statement, String type) {
        Question_ID = question_ID;
        Statement = statement;
        Type = type;
    }
    public Question(){}
    private String Question_ID;
    private String Statement;
    private String Type;



    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }




}
