package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findAllByHouseId(Long houseId);
}
