//package com.example.trendtyschool.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.nio.file.AccessDeniedException;
//
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
//        logger.error("Authentication error: {}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
//        logger.error("Access denied: {}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//    }
//}
