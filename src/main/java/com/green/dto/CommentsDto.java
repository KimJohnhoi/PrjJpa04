package com.green.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentsDto {
   private Long id;         // 댓글(자식) ID
   @JsonProperty("article_id") // json(article_id)이름으로 날라온걸 articleId로 바꾼다
   private Long articleId;  // 게시글(부모) 테이블 ID
   private String nickname;
   private String body;
   
   // CommentsDTO <- Comments (db 조회한)
   public static CommentsDto createCommentDTO(Comments comments) {
      return new CommentsDto(
            comments.getId(),
            comments.getArticle().getId(),
            comments.getNickname(),
            comments.getBody()
            );
   }
   
}
