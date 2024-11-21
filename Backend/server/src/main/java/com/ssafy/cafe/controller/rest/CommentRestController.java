package com.ssafy.cafe.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/comment")
@CrossOrigin("*")
public class CommentRestController {

    private static final Logger log = LoggerFactory.getLogger(CommentRestController.class);

    @Autowired
    CommentService cs;

    @Operation(summary = "comment 객체를 수정한다. 평점은 1.0 ~ 5.0만 허용. 벗어나면 false를 리턴.")
    @PutMapping("")
    public ResponseEntity<?> updateComment(@Validated @RequestBody Comment comment) {
        if (comment.getRating() < 1.0 || comment.getRating() > 5.0) {
            return ResponseEntity.ok(false);
        }
        int result = cs.updateComment(comment);
        if (result == 1) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @Operation(summary = "comment 객체를 추가한다. 평점은 1.0 ~ 5.0만 허용. 벗어나면 false를 리턴. id는 자동생성.")
    @PostMapping("")
    public ResponseEntity<?> registerComment(@Validated @RequestBody Comment comment) {
        log.debug("Comment: {}", comment);
        if (comment.getRating() < 1.0 || comment.getRating() > 5.0) {
            return ResponseEntity.ok(false);
        }
        int result = cs.addComment(comment);
        if (result == 1) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @Operation(summary = "{id}에 해당하는 comment를 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        int result = cs.removeComment(id);
        if (result == 1) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

}
