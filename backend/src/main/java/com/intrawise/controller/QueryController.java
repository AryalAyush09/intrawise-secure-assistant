//package com.intrawise.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.intrawise.requestDto.QueryDTO;
//import com.intrawise.responseDTO.ApiResponse;
//import com.intrawise.services.QueryService;
//
//import lombok.AllArgsConstructor;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/v1/query")
//public class QueryController {
//   private final QueryService queryService;
//	
//	@PostMapping("/ask")
//	 public ResponseEntity<ApiResponse<?>> askQuery(Authentication auth,@RequestBody QueryDTO dto){
//		Long userId = Long.parseLong(auth.getName());
//		ApiResponse<?> response = queryService.askQuery(userId,dto);
//		return new ResponseEntity.ok(response);
//	}
//}
