package com.study.model;

import java.util.List;

public class QuizQuestion {

    public enum QuestionType {
        MULTIPLE_CHOICE,
        FILL_BLANK,
        SELECT_RESULT,
        TRUE_FALSE
    }

    private int id;
    private QuestionType type;
    private String question;
    private String questionHtml;
    private String codeSnippet;
    private List<String> options;
    private int correctAnswer;
    private String explanation;

    public QuizQuestion() {}

    public QuizQuestion(int id, QuestionType type, String question, String questionHtml,
                        String codeSnippet, List<String> options, int correctAnswer,
                        String explanation) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.questionHtml = questionHtml;
        this.codeSnippet = codeSnippet;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getQuestionHtml() { return questionHtml; }
    public void setQuestionHtml(String questionHtml) { this.questionHtml = questionHtml; }
    public String getCodeSnippet() { return codeSnippet; }
    public void setCodeSnippet(String codeSnippet) { this.codeSnippet = codeSnippet; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
