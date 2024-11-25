package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.CommentDao;
import com.ssafy.cafe.model.dao.ProductDao;
import com.ssafy.cafe.model.dto.CommentWithInfo;
import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.ProductWithComment;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao pDao;

    @Autowired
    private CommentDao cDao;
    
    @Override
    public List<Product> getProductList() {
        return pDao.selectAll();
    }

    @Override
    public ProductWithComment selectWithComment(Integer productId) {
    	ProductWithComment productWithComment = pDao.selectWithInfo(productId);
    	int quantity = pDao.getQuantity(productId);
        List<CommentWithInfo> comments = cDao.selectByProduct(productId);
        productWithComment.setComments(comments);
        productWithComment.setCommentCount(comments.size());
        productWithComment.setTotalSells(quantity);
        double rateSum = 0.0;
        for (CommentWithInfo c : comments) {
        	rateSum += c.getRating();
        }
        double averageStars = (double) ((int) (rateSum / comments.size() * 10)) / (double) 10;
        
        productWithComment.setAverageStars(averageStars);
    	
        return productWithComment;
    }
    
    @Override
    public List<ProductWithComment> getTopProductsWithComments(int limit) {
        List<ProductWithComment> topProducts = pDao.selectTopProductsByRating(limit);
        for (ProductWithComment product : topProducts) {
            List<CommentWithInfo> comments = cDao.selectByProduct(product.getId());
            product.setComments(comments);
        }
        return topProducts;
    }

}