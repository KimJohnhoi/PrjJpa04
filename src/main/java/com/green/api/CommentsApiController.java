package com.green.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.CommentsDto;
import com.green.entity.Comments;
import com.green.service.CommentsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CommentsApiController {

	@Autowired
	private  CommentsService   commentsService;
	
	// 댓글 추가
	// @RequestBody : 넘어오는 JSON 을 받겠다
	// {nickname: "Jo", body: "모짜르트", article_id: 4}
	// /api/articles/{{id}}/comments	
	@PostMapping("/api/articles/{articleId}/comments")
	public  ResponseEntity<CommentsDto>  create(
		@PathVariable("articleId")   Long            articleId,  // 게시글번호
		@RequestBody                 CommentsDto     dto
			) {
		log.info("dto: {}", dto );
		
		CommentsDto  created  =   commentsService.create( dto  );
						
		return ResponseEntity.status(HttpStatus.OK).body( created  );
	}
		
	@PatchMapping("/api/comments/{id}")
	public ResponseEntity<CommentsDto> update(@PathVariable Long id, @RequestBody CommentsDto dto) {
		log.info("수정내용: {}", dto);
		
		CommentsDto updatedDto = commentsService.update(id, dto);
		
		return ResponseEntity.status(HttpStatus.OK).body( updatedDto );
	}
	
	@DeleteMapping("/api/comments")
	public ResponseEntity<CommentsDto>  delete(@RequestBody CommentsDto dto){
		CommentsDto deletedDto = commentsService.delete(dto);
		return ResponseEntity.status(HttpStatus.OK).body( deletedDto );
	}
}