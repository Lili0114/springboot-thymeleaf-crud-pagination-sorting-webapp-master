package net.javaguides.springboot.dto;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty
    private String username;
    @NotEmpty(message = "Email cannot be empty!")
    @Email
    private String email;
    @NotEmpty(message = "Password cannot be empty!")
    private String password;
    @NotEmpty(message = "Role cannot be empty!")
    private String role;
}