package com.ssafy.cafe.controller.rest;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;
import com.ssafy.cafe.model.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import com.ssafy.cafe.model.service.ChatGptService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rest/product")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    ProductService ps;
    
    @Autowired
    private ChatGptService chatGptService;

    @Operation(summary = "전체 상품의 목록을 반환한다.")
    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        List<Product> products = ps.getProductList();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "{productId}에 해당하는 상품의 정보를 comment와 함께 반환한다.")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        ProductWithComment product = ps.selectWithComment(productId);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "평점을 기준으로 상위 5개의 메뉴와 해당 메뉴의 댓글을 반환한다.")
    @GetMapping("/top5")
    public ResponseEntity<?> getTop5Products() {
        List<ProductWithComment> topProducts = ps.getTopProductsWithComments(5);
        return ResponseEntity.ok(topProducts);
    }
    
    @Operation(summary = "GPT를 활용하여 Top 5 메뉴의 장단점을 반환한다.")
    @GetMapping("/gpt-summary")
    public ResponseEntity<?> getGptSummary() {
        try {
            // Top 5 제품 가져오기
            List<ProductWithComment> topProducts = ps.getTop5Products();

            // 제품 정보를 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProducts = objectMapper.writeValueAsString(topProducts);

            // ChatGPT에 전달할 프롬프트 생성
            String prompt = jsonProducts + " 이렇게 top5 메뉴 설명이 있는데, 이걸 코멘트를 참고해서 이용자에게 각 메뉴의 장단점을 소개만 해.";

            // ChatGPT API 호출
            String chatGptResponse = chatGptService.getSummaryFromChatGpt(prompt, null);

            // JSON 형식으로 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("summary", chatGptResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "GPT 요약 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}