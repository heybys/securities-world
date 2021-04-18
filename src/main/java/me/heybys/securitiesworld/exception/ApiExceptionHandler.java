package me.heybys.securitiesworld.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(BranchStatisticsNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("404", "br code not found error");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
