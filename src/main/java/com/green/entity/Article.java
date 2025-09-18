package com.green.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Data;

// @AllArgsConstructor 모든 인자가 있는 생성자
// @NoArgsConstructor  기본 생성자
@Entity
@Builder
@Data        //@RequiredArgsConstructor - null 못받음
@SequenceGenerator(
		name = "ARTICLE_SEQ_GENERATOR",
		sequenceName= "ARTICLE_SEQ", // create sequence COMMEN-SEQ
		initialValue = 1, // 초기값
		allocationSize = 1 // 증가치
	)

public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "ARTICLE_SEQ_GENERATOR") // 번호 자동 증가
	private Long id;    // Integer : null 입력가능, int 는 null 못받음
	
	@Column                // DB 칼럼 선언 
	private String title;
	
	@Column
	private String content;
	
	public Article() {}
	public Article(Long id , String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}	
}