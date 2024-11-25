package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;

public interface ProductService {
    /**
     * 모든 상품 정보를 반환한다.
     * @return
     */
    List<Product> getProductList();
    
    /**
     * backend 관통 과정에서 추가됨
     * 상품의 정보, 판매량, 평점 정보를 함께 반환
     * @param productId
     * @return
     */
    ProductWithComment selectWithComment(Integer productId);
    
    /**
     * 평점을 기준으로 상위 메뉴와 해당 메뉴의 댓글을 가져온다.
     * @param limit 가져올 메뉴의 수
     * @return 상위 메뉴와 댓글 목록
     */
    List<ProductWithComment> getTopProductsWithComments(int limit);
    /**
     * 평점 기준 상위 5개의 제품을 반환한다.
     * @return
     */
    List<ProductWithComment> getTop5Products();
}
