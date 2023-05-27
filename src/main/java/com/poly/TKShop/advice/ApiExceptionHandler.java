package com.poly.TKShop.advice;

import com.poly.TKShop.exception.UserException;
import com.poly.TKShop.model.ResponseObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.hibernate.exception.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseObject> handleUserException(UserException userException){
        ResponseObject responseObject = new ResponseObject(
                "false",
                "Duplicate entry",
                userException.getMessage()

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
        Throwable c = ex.getCause();
        ConstraintViolationException cv = (ConstraintViolationException) c;
        System.out.println("d " + cv.getSQLException().getMessage());
        ResponseObject responseObject = new ResponseObject(
                "false",
                "Duplicate entry",
                ex.getMostSpecificCause().getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseObject);
    }


}
