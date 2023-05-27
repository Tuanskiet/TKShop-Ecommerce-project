package com.poly.TKShop.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "username shouldn't be null!")
    private String username;

    @NotBlank(message = "password shouldn't be blank!")
    @Size(min = 6, max = 10, message = "password must between 6 and 10")
    private String password;

    @NotBlank(message = "email shouldn't be blank!")
    private String email;
    private boolean enabled ;
}
