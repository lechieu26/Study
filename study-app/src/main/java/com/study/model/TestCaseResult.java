package com.study.model;

public class TestCaseResult {
    private int testId;
    private boolean passed;
    private String input;
    private String expected;
    private String actual;

    public TestCaseResult() {}

    public TestCaseResult(int testId, boolean passed, String input, String expected, String actual) {
        this.testId = testId;
        this.passed = passed;
        this.input = input;
        this.expected = expected;
        this.actual = actual;
    }

    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public String getExpected() { return expected; }
    public void setExpected(String expected) { this.expected = expected; }
    public String getActual() { return actual; }
    public void setActual(String actual) { this.actual = actual; }
}
