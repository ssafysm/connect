package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;

public interface ProductDao {
    /**
     * 모든 상품정보를 조회한다. 
     * 
     * @return
     */
    List<Product> selectAll();
    
    /**
     * ID에 해당하는 상품의 comment 갯수, 평점평균, 전체 판매량 정보를 함께 반환
     * 
     * @param productId
     * @return
     */
    ProductWithComment selectWithInfo(Integer productId);
    
    int getQuantity(Integer productId);
    
    /**
     * 평점을 기준으로 상위 메뉴를 가져온다.
     * @param limit 가져올 메뉴의 수
     * @return 상위 메뉴 목록
     */
    List<ProductWithComment> selectTopProductsByRating(int limit);
    
    /**
     * 평점 기준 상위 5개의 제품을 반환한다.
     * @return
     */
    List<ProductWithComment> selectTop5Products();
}
