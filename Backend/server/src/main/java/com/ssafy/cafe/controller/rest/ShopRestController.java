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

import com.ssafy.cafe.model.dto.Shop;
import com.ssafy.cafe.model.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/shop")
@CrossOrigin("*")
public class ShopRestController {

    private static final Logger log = LoggerFactory.getLogger(ShopRestController.class);

    @Autowired
    ShopService ss;
    
    @Operation(summary = "전체 매장 정보를 가지고 온다.")
    @GetMapping
    public ResponseEntity<?> getshops() {
        List<Shop> result = ss.getshops();
        
        return ResponseEntity.ok(result);
    }

}
