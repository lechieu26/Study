package com.study.model;

import java.util.List;

public class Topic {
    private String id;
    private String name;
    private String description;
    private String icon;
    private String theoryHtml;
    private List<Exercise> exercises;

    public Topic() {}

    public Topic(String id, String name, String description, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getTheoryHtml() { return theoryHtml; }
    public void setTheoryHtml(String theoryHtml) { this.theoryHtml = theoryHtml; }
    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
}
