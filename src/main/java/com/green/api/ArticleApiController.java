package com.green.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.Article;
import com.green.dto.ArticleDTO;
import com.green.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ArticleApiController {
		
	@Autowired
	private ArticleService articleService;
	
	/* 
	 build.gradle 에 jackson dataformat xml 라이브러리를 추가하면
	 기본 data 로 출력
	 라이브러리를 추가한 후 json을 출력할려면
	 produces ="application/json;charset=UTF-8" 추가 하면 된다
	 */
	@GetMapping(
			value="/api/articles",
			produces ="application/json;charset=UTF-8" // json 으로 바꿈
			)
	public List<Article> list() {
		
		
		List<Article> list = articleService.getList();
		log.info("list : {}",list);
		System.out.println(list);
		return list;
	}
	
	// ResponseEntity<Article>
	// Article : DATA
	// +상태 코드 ResponseEntity.status( HttpStatus.OK ) -200, 304등등
	// 또는 ResponseEntity.status( HttpStatus.BAD_REQUEST )
	
	// 한개 조회
	@GetMapping("/api/articles/{id}") 
	public ResponseEntity<Article> getOne(
		@PathVariable("id") Long id
			) {
		Article article = articleService.get(id);
		ResponseEntity<Article> result = 
			(article != null)
			? ResponseEntity.status(HttpStatus.OK).body(article)
			: ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		return result;
	}
	
	@PostMapping(value = "/api/articles") // title, content
	public ResponseEntity<Article> create(@RequestBody ArticleDTO articleDTO) { 
				      // @RequestBody넘어오는 정보는 json 문자열일떄 사용
		
		Article created = articleService.create(articleDTO);
		// saved : 저장된 id, title, content 를 돌려받는다
		
		ResponseEntity<Article> result =
		  (created != null) 
		  	// created != null 이면
		  	? ResponseEntity.status(HttpStatus.OK).body(created) // 200(ok), 201(created)
		  	// created = null 이면
		  	: ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 (error)
		
		return result;
	}
	
	@DeleteMapping(value = "/api/articles/{id}")
	public ResponseEntity<Article> delete(
		@PathVariable("id") Long id	
			) {
		
		Article deleted = articleService.delete(id);
		ResponseEntity<Article> result =
				(deleted != null) 
				? ResponseEntity.status(HttpStatus.OK).body(deleted) // 200(ok), 201(created)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 (error)
		
		return result;
	}
	
	@PatchMapping(value = "/api/articles")
	public ResponseEntity<Article> update(
			@RequestBody Article article // JSON.string 으로 날라온건 @RequestBody를 붙여야 함
			) {
		System.out.println("???????????? : " + article);
		Article updated = articleService.update(article);
		ResponseEntity<Article> result =
				(updated != null) 
				? ResponseEntity.status(HttpStatus.OK).body(updated) // 200(ok), 201(created)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 (error)
		return result;
	}
	
	// 3줄의 data
	/* JSON 형식
	[
		{ "title" : "시간예약", "content" : "1420" },
		{ "title" : "영화 지정", "content" : "캐대헌" },
		{ "title" : "자리 지정", "content" : "A2" }
	]
	*/
	// /api/transaction-test-1
	// 3개의 데이터를 받아서 서비스에 넘겨 저장결과를 받는다

	@PostMapping(value="/api/transaction-test1")
	public ResponseEntity<List<Article>> transaction1(
			@RequestBody List<ArticleDTO> dtos
			) {
		List<Article> createdList = articleService.createArticleList(dtos);
		ResponseEntity<List<Article>> result =
				(createdList != null)
				? ResponseEntity.status(HttpStatus.OK).body(createdList)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return result;
	}
	
	@PostMapping(value="/api/transaction-test2")
	public ResponseEntity<List<Article>> transaction2(
			@RequestBody List<ArticleDTO> dtos
			) {
		List<Article> createdList = articleService.createArticleList2(dtos);
		ResponseEntity<List<Article>> result =
				(createdList != null)
				? ResponseEntity.status(HttpStatus.OK).body(createdList)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return result;
	}
}




