package com.study.model;

public class CodeCheckResult {
    private boolean success;
    private String output;
    private String error;
    private int score;
    private String feedback;

    public CodeCheckResult() {}

    public CodeCheckResult(boolean success, String output, String error, int score, String feedback) {
        this.success = success;
        this.output = output;
        this.error = error;
        this.score = score;
        this.feedback = feedback;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
