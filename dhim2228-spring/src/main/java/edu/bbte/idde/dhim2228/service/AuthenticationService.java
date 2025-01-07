package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.user.UserLoginRequestDto;
import org.springframework.http.ResponseCookie;

public interface AuthenticationService {
    ResponseCookie login(UserLoginRequestDto userLoginRequestDto);

    ResponseCookie logout();
}
