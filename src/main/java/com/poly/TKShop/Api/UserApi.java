package com.poly.TKShop.Api;


import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.response.ResponseObject;
import com.poly.TKShop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class UserApi {
    @Autowired
    UserService userService;

    @GetMapping("/getAllUsers")
    @Operation(summary = "get all users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getAllUsers(){
        List<UserDto> listUsers = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseObject("ok", "get all user successfully",
                        listUsers != null ? listUsers : "No users found!"));
    }

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

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete user", description = "delete user by id")
    public ResponseEntity<ResponseObject> deleteUser(
            @PathVariable(name = "id") int id){
        //do something
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "delete user successfully", "ID user deleted : "+ id));
    }

    @PatchMapping("/reset-password")
    @Operation(summary = "Reset password")
    public ResponseEntity<?> resetPassword(@RequestBody String email){
        boolean sent = userService.resetPassword(email);
        if(!sent) ResponseEntity.internalServerError();
        return ResponseEntity.ok("sent");
    }
}
