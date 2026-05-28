package com.study.controller;

import com.study.model.CodeCheckRequest;
import com.study.model.CodeCheckResult;
import com.study.service.CodeExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final CodeExecutionService codeExecutionService;

    public ApiController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/check")
    public ResponseEntity<CodeCheckResult> checkCode(@RequestBody CodeCheckRequest request) {
        CodeCheckResult result = codeExecutionService.executeJavaCode(request.getCode());
        return ResponseEntity.ok(result);
    }
}
