package com.study.controller;

import com.study.model.CodeCheckRequest;
import com.study.model.CodeCheckResult;
import com.study.service.CodeExecutionService;
import com.study.service.ContentService;
import com.study.service.SqlExecutionService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final CodeExecutionService codeExecutionService;
    private final SqlExecutionService sqlExecutionService;
    private final ContentService contentService;

    public ApiController(CodeExecutionService codeExecutionService,
                         SqlExecutionService sqlExecutionService,
                         ContentService contentService) {
        this.codeExecutionService = codeExecutionService;
        this.sqlExecutionService = sqlExecutionService;
        this.contentService = contentService;
    }

    @PostMapping("/check")
    public ResponseEntity<CodeCheckResult> checkCode(@RequestBody CodeCheckRequest request) {
        CodeCheckResult result;
        if ("sql".equals(request.getTopicId())) {
            result = sqlExecutionService.executeSql(request.getCode());
        } else {
            result = codeExecutionService.executeJavaCode(request.getCode());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/download/{topicId}/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String topicId,
                                                  @PathVariable String filename) {
        if (!filename.endsWith(".sql")) {
            return ResponseEntity.badRequest().build();
        }
        String content = contentService.loadFile(topicId, filename);
        if (content.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource resource = new ByteArrayResource(content.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
