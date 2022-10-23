package com.example.inzynierka.exceptions.handlers;


import com.example.inzynierka.exceptions.AddRecipeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AddRecipeException.class)
    public ResponseEntity<String> handlerException(AddRecipeException addRecipeException){
        log.info("Failed to add recipe", addRecipeException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addRecipeException.getMessage());
    }
}
