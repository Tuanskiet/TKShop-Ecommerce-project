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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class UserApi {
    @Autowired
    UserService userService;


    @PostMapping("/new")
    @Operation(summary="Create new Account/User", description="Create new user/ Sign up")
    public ResponseEntity<ResponseObject> createNewUser(@Valid @RequestBody UserDto userDto){
        UserDto newUser = userService.createNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "created", newUser));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update user", description = "Update user in DB, ensure your user id exist")
    @Validated
    public ResponseEntity<ResponseObject> updateUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable(name="id") int id
    ){
        UserDto newUser = userService.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "updated", newUser));
    }
}
