package edu.bbte.idde.dhim2228.dto.user;

import edu.bbte.idde.dhim2228.anotations.ValidPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotNull
    @Size(min = 4, max = 15)
    private String username;

    @NotNull
    @ValidPassword
    private String password;
}
