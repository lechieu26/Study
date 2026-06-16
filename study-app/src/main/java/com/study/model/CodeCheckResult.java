package com.study.model;

import java.util.List;

public class CodeCheckResult {
    private boolean success;
    private String output;
    private String error;
    private int score;
    private String feedback;
    private List<TestCaseResult> testResults;
    private int passedCount;
    private int totalTests;

    public CodeCheckResult() {}

    public CodeCheckResult(boolean success, String output, String error, int score, String feedback) {
        this.success = success;
        this.output = output;
        this.error = error;
        this.score = score;
        this.feedback = feedback;
    }

    public CodeCheckResult(boolean success, String output, String error, int score, String feedback,
                           List<TestCaseResult> testResults, int passedCount, int totalTests) {
        this.success = success;
        this.output = output;
        this.error = error;
        this.score = score;
        this.feedback = feedback;
        this.testResults = testResults;
        this.passedCount = passedCount;
        this.totalTests = totalTests;
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
    public List<TestCaseResult> getTestResults() { return testResults; }
    public void setTestResults(List<TestCaseResult> testResults) { this.testResults = testResults; }
    public int getPassedCount() { return passedCount; }
    public void setPassedCount(int passedCount) { this.passedCount = passedCount; }
    public int getTotalTests() { return totalTests; }
    public void setTotalTests(int totalTests) { this.totalTests = totalTests; }
}
