package org.example.likelion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OutOfStockProductException extends RuntimeException {
    public OutOfStockProductException(String message) {
        super(message);
    }
}
