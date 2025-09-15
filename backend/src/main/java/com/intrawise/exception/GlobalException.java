package com.intrawise.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.intrawise.responseDTO.ApiResponse;

@RestControllerAdvice
public class GlobalException {
//   private final Logger Logger = LoggerFactory.getLogger(GlobalException.class);
   
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException
                 (MethodArgumentNotValidException ex){
	   Map<String, String> errors = new HashMap<>();
       for (FieldError error : ex.getBindingResult().getFieldErrors()) {
           errors.put(error.getField(), error.getDefaultMessage());
       }
       
	   ApiResponse<Map<String, String>> response = new ApiResponse<Map<String, String>>(false, "Validation failed", errors);
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
   }
   
   @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex){
	   ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null);
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
   }
   
   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
       ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
   }
   
   @ExceptionHandler(FileUploadException.class)
   public ResponseEntity<ApiResponse<?>> handleFile(FileUploadException ex){
	   ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null);
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
   }
}
