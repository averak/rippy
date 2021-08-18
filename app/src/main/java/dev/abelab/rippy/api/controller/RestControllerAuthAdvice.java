package dev.abelab.rippy.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.*;
import dev.abelab.rippy.annotation.Authenticated;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.service.AuthService;

/**
 * Rest controller auth advice
 */
@RequiredArgsConstructor
@RestControllerAdvice(annotations = Authenticated.class)
public class RestControllerAuthAdvice {

    private final AuthService authService;

    @ModelAttribute("LoginUser")
    public User addJwt(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String credentials) {
        return this.authService.getLoginUser(credentials);
    }

}
