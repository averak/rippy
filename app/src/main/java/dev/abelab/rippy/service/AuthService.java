package dev.abelab.rippy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.api.request.LoginRequest;
import dev.abelab.rippy.logic.UserLogic;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.UnauthorizedException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserLogic userLogic;

    private final UserRepository userRepository;

    /**
     * ログイン処理
     *
     * @param requestBody ログインリクエスト
     *
     * @return JWT
     */
    @Transactional
    public String login(final LoginRequest requestBody) {
        // ユーザ情報を取得
        final var user = this.userRepository.selectByEmail(requestBody.getEmail());

        // パスワードチェック
        this.userLogic.verifyPassword(user, requestBody.getPassword());

        // JWTを発行
        return this.userLogic.generateJwt(user);
    }

    /**
     * ログインユーザを取得
     *
     * @param credentials 資格情報
     *
     * @return ログインユーザ
     */
    @Transactional
    public User getLoginUser(final String credentials) {
        // 資格情報の構文チェック
        if (!credentials.startsWith("Bearer ")) {
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
        final var jwt = credentials.substring(7);

        // ログインユーザを取得
        return this.userLogic.getLoginUser(jwt);
    }

}
