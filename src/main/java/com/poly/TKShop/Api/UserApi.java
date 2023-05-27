package com.poly.TKShop.Api;


import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.User;
import com.poly.TKShop.model.ResponseObject;
import com.poly.TKShop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserApi {
    @Autowired
    UserService userService;

    @GetMapping("/testApi")
    public String testApi(){
        return "data";
    }

    @PostMapping("/sign-up")
    @Operation(summary="Sign up new Account", description="Sign up new Account")
    public ResponseEntity<ResponseObject> doSignIn(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "no user created", bindingResult.getAllErrors()));
        }
        UserDto newUser = userService.createNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "created", newUser));
    }
//hi

}
