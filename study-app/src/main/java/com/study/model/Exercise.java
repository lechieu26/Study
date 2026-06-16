package com.study.model;

import java.util.List;

public class Exercise {
    private int id;
    private String title;
    private String difficulty;
    private String descriptionHtml;
    private List<Solution> solutions;
    private String boilerplateCode;

    public Exercise() {}

    public Exercise(int id, String title, String difficulty, String descriptionHtml, List<Solution> solutions) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.descriptionHtml = descriptionHtml;
        this.solutions = solutions;
    }

    public Exercise(int id, String title, String difficulty, String descriptionHtml, List<Solution> solutions, String boilerplateCode) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.descriptionHtml = descriptionHtml;
        this.solutions = solutions;
        this.boilerplateCode = boilerplateCode;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getDescriptionHtml() { return descriptionHtml; }
    public void setDescriptionHtml(String descriptionHtml) { this.descriptionHtml = descriptionHtml; }
    public List<Solution> getSolutions() { return solutions; }
    public void setSolutions(List<Solution> solutions) { this.solutions = solutions; }
    public String getBoilerplateCode() { return boilerplateCode; }
    public void setBoilerplateCode(String boilerplateCode) { this.boilerplateCode = boilerplateCode; }
}
