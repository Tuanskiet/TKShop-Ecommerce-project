package com.poly.TKShop.advice;

import com.poly.TKShop.exception.UserNotFoundException;
import com.poly.TKShop.model.ResponseObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseObject> handleUserException(UserNotFoundException userException){
        ResponseObject responseObject = new ResponseObject(
                "false",
                userException.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleMeMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errorMap.put(err.getField(), err.getDefaultMessage());
        });
        ResponseObject responseObject = new ResponseObject(
                "false",
                "validated fail",
                errorMap
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseObject> handleConflict(DataIntegrityViolationException ex){
        ResponseObject responseObject = new ResponseObject(
                "false",
                "Duplicate entry",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseObject);
    }


}
