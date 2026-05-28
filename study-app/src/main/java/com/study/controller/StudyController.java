package com.study.controller;

import com.study.model.Exercise;
import com.study.model.Topic;
import com.study.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudyController {

    private final ContentService contentService;

    public StudyController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("topics", contentService.getAllTopics());
        return "index";
    }

    @GetMapping("/topic/{id}")
    public String topicDetail(@PathVariable String id, Model model) {
        Topic topic = contentService.getTopicById(id)
            .orElseThrow(() -> new RuntimeException("Chủ đề không tồn tại: " + id));
        model.addAttribute("topic", topic);
        model.addAttribute("topics", contentService.getAllTopics());
        return "topic";
    }

    @GetMapping("/topic/{topicId}/theory")
    public String theory(@PathVariable String topicId, Model model) {
        Topic topic = contentService.getTopicById(topicId)
            .orElseThrow(() -> new RuntimeException("Chủ đề không tồn tại: " + topicId));
        model.addAttribute("topic", topic);
        model.addAttribute("topics", contentService.getAllTopics());
        return "theory";
    }

    @GetMapping("/topic/{topicId}/exercise/{exerciseId}")
    public String exercise(@PathVariable String topicId, @PathVariable int exerciseId, Model model) {
        Topic topic = contentService.getTopicById(topicId)
            .orElseThrow(() -> new RuntimeException("Chủ đề không tồn tại: " + topicId));
        Exercise exercise = contentService.getExercise(topicId, exerciseId)
            .orElseThrow(() -> new RuntimeException("Bài tập không tồn tại: " + exerciseId));
        model.addAttribute("topic", topic);
        model.addAttribute("exercise", exercise);
        model.addAttribute("topics", contentService.getAllTopics());
        return "exercise";
    }
}
