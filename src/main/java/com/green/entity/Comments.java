package com.green.entity;

import com.green.dto.CommentsDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
	name = "COMMENTS_SEQ_GENERATOR",
	sequenceName= "COMMENTS_SEQ", // create sequence COMMEN-SEQ
	initialValue = 1, // 초기값
	allocationSize = 1 // 증가치
)
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "COMMENTS_SEQ_GENERATOR") // 번호 자동 증가
	private Long id;
	
	// @Column(name="nick_name", nullable=true, length=255)
	// Oracle 11g varchar2 최대4000 -> clob
	// Oracle 12g varchar2 최대32000 -> 별도설정  필요
	@Column
	private String body;
	
	@Column
	private String nickname;
	
	
	// 외래키 설정
	@ManyToOne 								// 외래키 다대일 관계
	@JoinColumn(name="article_id") 			// 외래키 칼럼
	private Article article; 				// 연결될 entity 객체의 이름
	
   public static Comments createComment(CommentsDto dto, Article article) {
	  Comments comments = new Comments(
		    null, //dto.getId()
		    dto.getBody(),
		    dto.getNickname(),
		    article
	    );
      return comments;
   }
   
   public void updateComment(CommentsDto dto) {	   
	   if(this.id != dto.getId())
		   throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다");
	   if(dto.getNickname() != null)
		   this.nickname = dto.getNickname();
	   if(dto.getBody() != null)
		   this.body = dto.getBody();	   
   }
}