package com.poly.TKShop.hander.advice;

import com.poly.TKShop.exception.AppException;
import com.poly.TKShop.exception.RoleException;
import com.poly.TKShop.exception.UserException;
import com.poly.TKShop.dto.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseObject> handleAppException(AppException appException){
        ResponseObject responseObject = new ResponseObject(
                "false",
                "",
                appException.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseObject> handleUserException(UserException userException){
        ResponseObject responseObject = new ResponseObject(
                "false",
                "user problem",
                userException.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ResponseObject> handleRoleException(RoleException roleException){
        ResponseObject responseObject = new ResponseObject(
                "false",
                "role problem",
                roleException.getMessage()
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
//        Throwable c = ex.getCause();
//        ConstraintViolationException cv = (ConstraintViolationException) c;
//        System.out.println("d " + cv.getSQLException().getMessage());
        ResponseObject responseObject = new ResponseObject(
                "false",
                "Duplicate entry",
                ex.getMostSpecificCause().getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseObject);
    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ResponseObject> accessDeniedException(AccessDeniedException ex) throws AccessDeniedException {
//        ResponseObject responseObject = new ResponseObject(
//                "false",
//                "Access is denied",
//                ex.getMessage()
//        );
//
//        return ResponseEntity.status(403)
//                .body(responseObject);
//    }

}
