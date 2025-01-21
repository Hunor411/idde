package edu.bbte.idde.dhim2228.dto.user;

import edu.bbte.idde.dhim2228.anotations.PasswordMatches;
import edu.bbte.idde.dhim2228.anotations.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatches
public class UserRequestDto {
    @NotNull
    @Size(min = 4, max = 15)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    private String confirmPassword;
}
