package com.green.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleDTO {
	

	private String title;
	private String content;
	public Article toEntity() {
		Article article = new Article(null, this.title, this.content);
		return article;
	}
}

