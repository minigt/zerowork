package ar.com.minigt.zerowork.todoapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(TodoException.class)
    protected ResponseEntity<ErrorModel> handleConflict(TodoException ex, WebRequest request) {
        return new ResponseEntity<ErrorModel>(new ErrorModel(ex), HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorModel> handleConflictGeneric(
            Throwable ex, WebRequest request) {
        return new ResponseEntity<ErrorModel>(new ErrorModel(500, 5001, ex.getMessage()), HttpStatus.valueOf(500));
    }

}