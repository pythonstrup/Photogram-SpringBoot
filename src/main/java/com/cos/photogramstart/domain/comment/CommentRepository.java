package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	// 내가 만든 네이티브 쿼리를 사용하면 Comment를 반환받지 못함.
	// int형이나 void만 가능하다.....
//	@Modifying
//	@Query(value="INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :userId, now())", nativeQuery=true)
//	Comment mSave(String content, int imageId, int userId);
}
