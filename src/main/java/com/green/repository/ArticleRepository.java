package com.green.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.green.dto.Article;
import com.green.dto.ArticleDTO;

@Repository
public interface ArticleRepository
                             // <엔티티 타입, PK타입>
       extends   CrudRepository<Article, Long>{
	
	@Override
	List<Article> findAll();
	// Iterable<> findAll()  return -> List<Article> findAll()
	// 상속관계를 이용하여 List를 Iterable 로 UpCasting 하여 Casting
	
	@Transactional
	void deleteByTitle(String string);
	/*
	switch (fruit) {
		case "사과" :
			System.out.println("dd");
			break;
		case "포도" : 
			System.out.println(ㄴㄴ);
			break;
		default :
			System.out.println("없음");
			break;
	}
	*/

	
}

