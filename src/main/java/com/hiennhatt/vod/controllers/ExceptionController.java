package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("message", ex.getReason());
        return new ResponseEntity<>(map, ex.getStatusCode());
    }

    @ExceptionHandler(HTTPResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleHTTPResponseStatusException(HTTPResponseStatusException ex) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("errorCode", ex.getErrorCode());
        return new ResponseEntity<>(map, ex.getHttpStatus());
    }
}
