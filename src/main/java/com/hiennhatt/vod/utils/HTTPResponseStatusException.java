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
public class HTTPResponseStatusException extends Exception {
    private String message;
    private int status;
    private HttpStatus httpStatus;
    private Object data;
}
