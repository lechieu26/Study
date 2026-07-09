package com.study.model;

public class InterviewNote {
    private final String id;
    private final String title;
    private final String fileName;
    private final String summary;
    private final String html;

    public InterviewNote(String id, String title, String fileName, String summary, String html) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
        this.summary = summary;
        this.html = html;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSummary() {
        return summary;
    }

    public String getHtml() {
        return html;
    }
}
