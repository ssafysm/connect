package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.CommentDao;
import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.dto.CommentWithInfo;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao cDao;

    @Override
    public int addComment(Comment comment) {
        return cDao.insert(comment);
    }

    @Override
    public Comment selectComment(Integer id) {
        return cDao.select(id);
    }

    @Override
    public int removeComment(Integer id) {
        return cDao.delete(id);
    }

    @Override
    public int updateComment(Comment comment) {
        return cDao.update(comment);
    }

    @Override
    public List<CommentWithInfo> selectByProduct(Integer productId) {
        return cDao.selectByProduct(productId);
    }


}
