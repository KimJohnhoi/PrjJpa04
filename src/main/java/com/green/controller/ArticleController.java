package com.green.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.dto.Article;
import com.green.dto.ArticleDTO;
import com.green.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@GetMapping("/article/WriteForm") 
	public String writeForm(Model model) {
		model.addAttribute("name","장원영");
		return "article/write"; // greetings3.mustache
	}
	
	// 게시글 등록
	@PostMapping("/article/Write") // title, content
	public String write(ArticleDTO articleDTO, Model model) {
		
		System.out.println("ㅁㄴㅇㄹ" + articleDTO);
		log.info("정보 : {}",articleDTO);
		log.warn("정보 : {}",articleDTO);
		log.error("정보 : {}",articleDTO);
		log.debug("정보 : {}",articleDTO);
		log.trace("정보 : {}",articleDTO);
		
		// h2 DB 에 Article table 에 저장
		// toEntity() : articleDTO -> article
		// DTO로 값을 받음다음 DTO 클라스에 있는 toEntity 실행(리턴 타입 Article)
		// article  에 DTO 값들을 저장
		Article article = articleDTO.toEntity();

		// .save(entity 타입 Class 필요)
		// articleRepository.save(article); - Entity에 저장한다는 뜻이니깐 Insert
		Article saved = articleRepository.save(article);
		// saved : 저장된 record 정보가 돌아온다
		
		System.out.println( saved.getId() ); // 저장된 글번호를 조회
		System.out.println( saved ); // 저장된 정보 조회
		
		model.addAttribute("articleDTO",articleDTO);
		
		return "redirect:/article/List"; // 목록조회로 이동
	}
	
	// 글 목록조회로 이동
	@GetMapping("/article/List")
	public String list(Model model) {
		
		// SELECT COUNT(*) FROM article
		long totCount = articleRepository.count();
		System.out.println("전체 자료수 : " + totCount);
		
		// DB 조회
//		List<Article> articleEntityList = articleRepository.findAll();      오류
//		ArrayList<Article> articleEntityList = articleRepository.findAll(); 오류
		
	    /* 해결책 1 
		List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
		System.out.println(articleEntityList);
		*/
		
		/* 해결책 2 
		Iterable<Article> articleEntityList =  articleRepository.findAll();
		System.out.println(articleEntityList);
		*/
		
		// 해결책 3 - 우리가 사용할 방법
		// articleRepository 에 있는 findAll()을 재정의한다
		// articleRepository.findAll(); 이거의 리턴타입을 List로 바꿔서 쓸수있는거 
		List<Article> articleList =  articleRepository.findAll();
		System.out.println(articleList);
		 
		model.addAttribute("articleList", articleList);
		return "article/list";
	}
	
	//  /article/10 : 10번 개시글 조회 id=10
	@GetMapping("/article/{id}")
	public String view(
			@PathVariable("id") Long id,
								Model model
			) {
		System.out.println("아이디: " + id);
		/* 조회방법 1 */
		// Optional<Article> : null 이 들어와도 null point exception
		// 이 발생하지 않도록 Optional로 반환받는다
		// Optional<Article> article = articleRepository.findById(id);
		
		// Optional<Article> article = articleRepository.findById(id);
		// DB에 row가 있으면 : Optional.of(article)
		// DB에 row가 없으면 : null이 아니라 -> Optional.empty() 
		
		/* 조회방법2 */ 
		// 이걸 많이씀
		// .orELse() : 값이 없을때 null이 return 되도록 변경가능
		Article article = articleRepository.findById(id).orElse(null);
		model.addAttribute("article", article);
		
		System.out.println("응애 : " + article);
		return "article/view";
	}
	
	// 삭제
	// /article/3/Delete
	@GetMapping("/article/{id}/Delete")
	public String delete(
			@PathVariable("id") Long id,
			RedirectAttributes rttr
			) {
		
		/* 1번 방법*/
//		articleRepository.deleteById(id);
//		articleRepository.deleteByTitle("ㄱㄱ");
		/* 2번 방법 */
	//	Article article = new Article(id, null, null);
		
		log.info(id + "번의 삭제요청이 들어왔습니다");
		
		
		// 1. 삭제 대상을 조회한다
		Article target = articleRepository.findById(id).orElse(null);
		
		// 2. 자료가 있으면 삭제
		if(target != null) {
			articleRepository.delete(target);
			// 메세지를 만든다 -> redirect한 페이지로 msg가 전달 된다
			rttr.addFlashAttribute("msg",id + "번이 삭제 되었습니다");
		}
		
		
		return "redirect:/article/List";
	}
	
	// UpdateForm
	// http://localhost:8080/article/2/UpdateForm
	@GetMapping("/article/UpdateForm/{id}")
	public String updateForm(
			@PathVariable("id") Long id,
								Model model
			) {
		
		Article article = articleRepository.findById(id).orElse(null);
		
		model.addAttribute("article", article);
		return "article/update";
	}
	
	// 파라미터로 HashMap을 사용할때 @RequestParam 필수
	@PostMapping("/article/Update")
	public String update(
		@RequestParam HashMap<String, Object> map
			) {
		
		
		// map.get("id") : Object
		// String.valueOf( map.get("id") ) : String
		// Long.parseLong(String.valueOf( map.get("id") )) : Long
		// 넘어온 값 처리
	//	Long   id      = (Long) map.get("id"); 이거 안됨
		Long   id      = Long.parseLong( String.valueOf( map.get("id") ) );
		String title   = String.valueOf( map.get("title") );
		String content = String.valueOf( map.get("content") );
		Article article = new Article(id, title, content);
		
		//수정 .save
		articleRepository.save(article);
		return "redirect:/article/List";
	}
}




