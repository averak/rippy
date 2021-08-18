package dev.abelab.rippy.logic;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.*;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.property.JwtProperty;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.ForbiddenException;
import dev.abelab.rippy.exception.BadRequestException;
import dev.abelab.rippy.exception.UnauthorizedException;

@RequiredArgsConstructor
@Component
public class UserLogic {

    private final UserRepository userRepository;

    private final JwtProperty jwtProperty;

    private final PasswordEncoder passwordEncoder;

    /**
     * 管理者チェック
     *
     * @param userId ユーザID
     */
    public void checkAdmin(final int userId) {
        final var user = this.userRepository.selectById(userId);

        if (user.getRoleId() != UserRoleEnum.ADMIN.getId()) {
            throw new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION);
        }
    }

    /**
     * JWTを発行
     *
     * @param user ユーザ
     *
     * @return JWT
     */
    public String generateJwt(final User user) {
        final var claims = Jwts.claims();
        claims.put(Claims.ISSUER, this.jwtProperty.getIssuer());
        claims.put("id", user.getId());

        return Jwts.builder() //
            .setClaims(claims) //
            .setIssuer(this.jwtProperty.getIssuer()) //
            .setIssuedAt(new Date()) //
            .setExpiration(new Date(System.currentTimeMillis() + this.jwtProperty.getValidHour() * 60 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, this.jwtProperty.getSecret().getBytes()) //
            .compact();
    }

    /**
     * ログインユーザを取得
     *
     * @param token Bearerトークン
     *
     * @return ユーザ
     */
    public User getLoginUser(final String token) {
        // 不正な構文
        if (!token.startsWith("Bearer ")) {
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
        final var jwt = token.substring(7);

        // JWTの有効性を検証
        try {
            final var claim = Jwts.parser().setSigningKey(this.jwtProperty.getSecret().getBytes()).parseClaimsJws(jwt).getBody();
            final var userId = claim.get("id");

            if (userId == null) {
                throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
            }

            return this.userRepository.selectById((int) userId);
        } catch (SignatureException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }
    }

    /**
     * パスワードをハッシュ化
     *
     * @param password パスワード
     *
     * @return ハッシュ値
     */
    public String encodePassword(final String password) {
        return this.passwordEncoder.encode(password);
    }

    /**
     * パスワードが一致するか検証
     *
     * @param user     ユーザ
     *
     * @param password パスワード
     */
    public void verifyPassword(final User user, final String password) {
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.WRONG_PASSWORD);
        }
    }

    /**
     * パスワードが有効かチェック
     */
    public void validatePassword(final String password) {
        // 8~32文字かどうか
        if (password.length() < 8 || password.length() > 32) {
            throw new BadRequestException(ErrorCode.INVALID_PASSWORD_SIZE);
        }
        // 大文字・小文字・数字を含むか
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).+$")) {
            throw new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD);
        }
    }

}
