package com.green;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.green.repository.ArticleRepository;

@SpringBootTest
class PrjJpa03ApplicationTests {
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void test1 () {
		double d1 = 10;
		double d2 = 3;
		double d = d1/d2;
		System.out.println(d);
//		assertEquals(d, 3.3);
		assertEquals(d, 3.3, 0.1); // 오차 범위를 플마0.1 로 인정
	}
	
	@Test
	void test2() {
		long totCnt = articleRepository.count();
		System.out.println(totCnt);
		assertEquals(6, totCnt);
	}
}
