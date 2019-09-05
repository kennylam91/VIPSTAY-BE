package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> findAllByHouseId(Long id);

    void createComment(Comment comment);
}
