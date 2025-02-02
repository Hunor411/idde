package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.user.UserLoginRequestDto;
import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import org.springframework.http.ResponseCookie;

public interface AuthenticationService {
    Long register(UserRequestDto userRequestDto);

    ResponseCookie login(UserLoginRequestDto userLoginRequestDto);

    ResponseCookie logout();
}
