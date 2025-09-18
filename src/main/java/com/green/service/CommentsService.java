package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.green.dto.CommentsDto;
import com.green.entity.Article;
import com.green.entity.Comments;
import com.green.repository.ArticleRepository;
import com.green.repository.CommentsRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentsService {

	@Autowired
	private  ArticleRepository   articleRepository;
	
	@Autowired
	private  CommentsRepository  commentsRepository;
	
	// 댓글 목록 조회
	public List<Comments> getComments(Long id) {
		
		// ArticleId 로 조회
		List<Comments>  comments = commentsRepository.findByArticleId(id);
		
		// Nickname 로 조회 : xml
		//List<Comments>  comments = commentsRepository.findByNickname("Kim");
		
		System.out.println( comments );
		return          comments;
		
	}

	   // 쌤거 코딩 댓글 추가
	   public CommentsDto create(CommentsDto dto) {
	      
	      // 1. 게시글 조회 및 조회실패 예외발생
	      // 게시글에 존재 하지 않는 articleId 가 넘어오면 throw(예외를 던진다)
	      Article article = articleRepository.findById(dto.getArticleId())
	                    .orElseThrow( () -> new IllegalArgumentException
	                          ("댓글 생성 실패 대상 게시물이 없습니다") ); // 조회, 예외처리
	      // 2. 댓글 엔티티 생성 -> 저장할 데이터를 만든다
	      Comments comments = Comments.createComment(dto, article);
	      // 3. 댓글 엔티티르 db에 저장
	      Comments created = commentsRepository.save(comments);
	      
	      CommentsDto newDTO = CommentsDto.createCommentDTO(created);
	      return newDTO;
	   }
	   
	   @Transactional
	   public CommentsDto update(Long id, CommentsDto dto) {
		   // 1. 수정할 댓글 조회 및 예외 발생 
		   Comments target = commentsRepository.findById(id)
                    .orElseThrow( () -> new IllegalArgumentException("수정 대상이 없습니다") );
		    // 2. 댓글 수정
		    target.updateComment(dto);
		    
		    // 3. db에 적용
		    Comments updated = commentsRepository.save(target);
		    
		    // 4. 댓글 엔티티를 dto로 변환 및 반환
		    CommentsDto updateDto = CommentsDto.createCommentDTO(updated);
		    return updateDto;
	   }

		public CommentsDto delete(CommentsDto dto) {
			Long id = dto.getId();
			
		   // 1. 삭제할 댓글 조회 및 예외 발생 
		   Comments target = commentsRepository.findById(id)
		         .orElseThrow( () -> new IllegalArgumentException("삭제할 대상이 없습니다") );
		   
		   // 2. 삭제처리
		   commentsRepository.deleteById(id);
		   CommentsDto result = CommentsDto.createCommentDTO(target);
		   
		   return result;
		}	
}