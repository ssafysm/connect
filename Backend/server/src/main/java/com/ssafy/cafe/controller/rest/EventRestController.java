package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Event;
import com.ssafy.cafe.model.service.EventService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/event")
@CrossOrigin("*")
public class EventRestController {

    private static final Logger log = LoggerFactory.getLogger(EventRestController.class);

    @Autowired
    EventService es;
    
    @Operation(summary = "전체 이벤트 정보를 가지고 온다.")
    @GetMapping
    public ResponseEntity<?> getEvents() {
        List<Event> result = es.getEvents();
        
        return ResponseEntity.ok(result);
    }

}
