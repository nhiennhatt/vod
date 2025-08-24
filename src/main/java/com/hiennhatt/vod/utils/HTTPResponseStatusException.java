package com.hiennhatt.vod.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HTTPResponseStatusException extends RuntimeException {
    private String message;
    private String errorCode;
    private HttpStatus httpStatus;
    private Object data;
}
