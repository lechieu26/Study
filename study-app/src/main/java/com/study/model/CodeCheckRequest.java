package com.study.model;

public class CodeCheckRequest {
    private String code;
    private String topicId;
    private int exerciseId;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTopicId() { return topicId; }
    public void setTopicId(String topicId) { this.topicId = topicId; }
    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
}
