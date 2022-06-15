package com.example.trvia.model;

public class Question {
    private String answer;
    private Boolean AnswerTrue;

    public Question() {
    }

    public Question(String answer, Boolean answerTrue) {
        this.answer = answer;
        AnswerTrue = answerTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getAnswerTrue() {
        return AnswerTrue;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        AnswerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", AnswerTrue=" + AnswerTrue +
                '}';
    }
}
