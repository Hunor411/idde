package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.user.UserLoginRequestDto;
import edu.bbte.idde.dhim2228.model.User;
import edu.bbte.idde.dhim2228.security.jwt.JwtUtils;
import edu.bbte.idde.dhim2228.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtutils;

    @Override
    public ResponseCookie login(UserLoginRequestDto userLoginRequestDto) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getUsername(),
                            userLoginRequestDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();

            return jwtutils.generateJwtCookie(user);
        } catch (BadCredentialsException e) {
            log.warn("Invalid username or password for user: {}", userLoginRequestDto.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public ResponseCookie logout() {
        return jwtutils.getCleanJwtCookie();
    }
}
