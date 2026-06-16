package com.study.model;

import java.util.List;

public class TopicGroup {
    private String name;
    private String icon;
    private List<Topic> topics;

    public TopicGroup(String name, String icon, List<Topic> topics) {
        this.name = name;
        this.icon = icon;
        this.topics = topics;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public List<Topic> getTopics() { return topics; }
    public void setTopics(List<Topic> topics) { this.topics = topics; }
}
