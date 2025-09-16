package com.green.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.green.dto.Comments;


// JpaRepository : Crud + PagingAndSorting
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>{

	// @Query : findByArticleId() 함수 호출하면 JPA 기능이 아닌 @Query 안의 sql(JPQL)을 실행한다.
	// nativeQuery = true  : oracle 용 sql 문을 사용
	// nativeQuery = false : JPQL 문법의 sql 문을 사용
	@Query(
			value="SELECT article_id, id, body, nickname FROM comments WHERE article_id=:articleId"
		  , nativeQuery = true )
	List<Comments> findByArticleId(Long articleId);
	
	// native query xml
	// src/main/resources/META-INF/orm.xml // 폴더와 파일을 생성해야 한다.
	// orm.xml 에 sql 을 저장해서 함수 호출
	// List<Comments> findByNickname(String nickname);
	
	
}