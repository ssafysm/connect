package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;
import com.ssafy.cafe.model.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rest/product")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    ProductService ps;

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
}
