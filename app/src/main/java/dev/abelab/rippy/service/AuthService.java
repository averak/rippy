package dev.abelab.rippy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.*;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.api.request.LoginRequest;
import dev.abelab.rippy.logic.UserLogic;

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

}
