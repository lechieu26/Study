package com.study.model;

public class Solution {
    private String approach;
    private String codeHtml;

    public Solution() {}

    public Solution(String approach, String codeHtml) {
        this.approach = approach;
        this.codeHtml = codeHtml;
    }

    public String getApproach() { return approach; }
    public void setApproach(String approach) { this.approach = approach; }
    public String getCodeHtml() { return codeHtml; }
    public void setCodeHtml(String codeHtml) { this.codeHtml = codeHtml; }
}
