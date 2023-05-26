package com.poly.TKShop.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;

    @NotBlank
    @Size(min = 6, max = 10)
    private String password;

    @NotBlank
    @Size(min = 6, max = 10)
    private String email;
    private boolean enabled ;
}
