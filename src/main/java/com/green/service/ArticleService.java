package com.green.service;

import java.util.ArrayList;
import java.util.List;

import org.glassfish.jaxb.core.v2.runtime.IllegalAnnotationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.dto.ArticleDTO;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	public List<Article> getList() {
		List<Article> articleList = articleRepository.findAll();
		
		return articleList;
	}
	
	// 추가
	public Article create(ArticleDTO articleDTO) {
		
		System.out.println(articleDTO);
		Article article = articleDTO.toEntity();
		// 입력된 id 가 있다면 null 을 리턴하도록 코딩 - 왜냐 시퀸스니깐
		if( article.getId() != null ) {
			return null;
		}
		Article saved = articleRepository.save(article);
		return saved;
	}
	
	// 한개 조회
	public Article get(Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		return article;
	}
	
	public Article delete(Long id) {
		
		// 삭제할 떄는 미리 검색을 하고 cache memory 에서
		Article target = articleRepository.findById(id).orElse(null);
		
		if(target == null )  return null;
		articleRepository.delete(target);
		
		return target;
	}
	public Article update(Article article) {
		// 1. 수정할 데이터를 검색
		Long id = article.getId();
		Article target = articleRepository.findById(id).orElse(null);
		if(target == null || target.getId() != id) {
			log.info("id:{} article{}", id, article);
			return null; // 400 띄울려면 null을 리턴해야함
		}
		target = articleRepository.save(article);

		return target;
	}
	
	// 실패 케이스 : 예약처리
	// 테스트 : TalendApi 로 테스트
	public List<Article> createArticleList(List<ArticleDTO> dtos) {
		
		// 1.넘어온 DTO 들을 Article Entity 묶음으로 변환
		List<Article> articleList = new ArrayList<>();
		for( ArticleDTO dto : dtos ) {
			Article article = dto.toEntity();
			articleList.add(article);
		}
		
		// 2. DB 에 반복저장
		for( Article article : articleList ) {
			articleRepository.save(article);
		}
		
		// 3. 강제로 에러 발생 - 찾다가 없으면 예외발생
		articleRepository.findById(-1L).orElseThrow( 
				() -> new IllegalArgumentException("결재 실패")
				);
		
		return articleList;
	}
	
	// 성공 케이스 : 예약처리
	// 테스트 : TalendApi 로 테스트
	@Transactional
	public List<Article> createArticleList2(List<ArticleDTO> dtos) {
		
		// 1.넘어온 DTO 들을 Article Entity 묶음으로 변환
		List<Article> articleList = new ArrayList<>();
		for( ArticleDTO dto : dtos ) {
			Article article = dto.toEntity();
			articleList.add(article);
		}
		
		// 2. DB 에 반복저장
		for( Article article : articleList ) {
			articleRepository.save(article);
		}
		
		// 3. 강제로 에러 발생 - 찾다가 없으면 예외발생
		articleRepository.findById(-1L).orElseThrow( 
				() -> new IllegalArgumentException("결재 실패")
				);
		
		return articleList;
	}
	
}



