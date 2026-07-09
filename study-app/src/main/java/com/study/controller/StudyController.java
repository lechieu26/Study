package com.study.controller;

import com.study.model.Exercise;
import com.study.model.InterviewNote;
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
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "index";
    }

    @GetMapping("/topic/{id}")
    public String topicDetail(@PathVariable String id, Model model) {
        Topic topic = contentService.getTopicById(id)
            .orElseThrow(() -> new RuntimeException("Chủ đề không tồn tại: " + id));
        model.addAttribute("topic", topic);
        model.addAttribute("topics", contentService.getAllTopics());
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "topic";
    }

    @GetMapping("/topic/{topicId}/theory")
    public String theory(@PathVariable String topicId, Model model) {
        Topic topic = contentService.getTopicById(topicId)
            .orElseThrow(() -> new RuntimeException("Chủ đề không tồn tại: " + topicId));
        model.addAttribute("topic", topic);
        model.addAttribute("topics", contentService.getAllTopics());
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "theory";
    }

    @GetMapping("/interview")
    public String interview(Model model) {
        var notes = contentService.getInterviewNotes();
        model.addAttribute("notes", notes);
        model.addAttribute("selectedNote", notes.isEmpty() ? null : notes.get(0));
        model.addAttribute("topics", contentService.getAllTopics());
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "interview";
    }

    @GetMapping("/interview/{id}")
    public String interviewNote(@PathVariable String id, Model model) {
        var notes = contentService.getInterviewNotes();
        InterviewNote selectedNote = contentService.getInterviewNoteById(id)
            .orElseThrow(() -> new RuntimeException("Ghi chú interview không tồn tại: " + id));
        model.addAttribute("notes", notes);
        model.addAttribute("selectedNote", selectedNote);
        model.addAttribute("topics", contentService.getAllTopics());
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "interview";
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
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "exercise";
    }

    @GetMapping("/topic/{topicId}/quiz")
    public String quiz(@PathVariable String topicId, Model model) {
        Topic topic = contentService.getTopicById(topicId)
            .orElseThrow(() -> new RuntimeException("Chủ đề không tồn tại: " + topicId));
        model.addAttribute("topic", topic);
        model.addAttribute("topics", contentService.getAllTopics());
        model.addAttribute("topicGroups", contentService.getTopicGroups());
        return "quiz";
    }
}
