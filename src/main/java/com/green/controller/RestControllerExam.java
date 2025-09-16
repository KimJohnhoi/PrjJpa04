package com.green.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerExam {
	/* Method */
	// Get
	// Post
	// Delete
	// Put, Patch
	
	// Method : Get 호출되는 주소
	// data 조회 : Select
	@GetMapping("/exam")
//	@ResponseBody
	public String getExam() {
		return "Get Data";
	}
	
	// Method : Post 호출되는 주소
	// data 추가 : Insert
	@PostMapping("/exam")
//	@ResponseBody
	public String postExam() {
		return "Post Data";
	}
	
	// 해당 데이터 삭제 : Delete
	@DeleteMapping("/exam")
//	@ResponseBody
	public String deleteExam() {
		return "Delete Data";
	}
	
	// 일부 데이터 수정 : Update
	@PatchMapping("/exam")
	public String patchExam() {
		return "Patch Data";
	}
	
	// 전체 데이터 수정 : Update
	@PutMapping("/exam")
	public String putExam() {
		return "Put Data";
	}
	
}





