package com.example.inzynierka.exceptions.handlers;


import com.example.inzynierka.exceptions.*;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerException(IllegalArgumentException illegalArgumentException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegalArgumentException.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handlerException(ResourceNotFoundException resourceNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handlerException(AccessDeniedException accessDeniedException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(accessDeniedException.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerException(IllegalStateException illegalStateException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(illegalStateException.getMessage());
    }

    @ExceptionHandler(PantryCreationException.class)
    public ResponseEntity<String> handlerException(PantryCreationException pantryCreationException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(pantryCreationException.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> handlerException(TokenExpiredException tokenExpiredException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenExpiredException.getMessage());
    }
}
