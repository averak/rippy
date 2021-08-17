package dev.abelab.rippy.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.api.request.UserCreateRequest;
import dev.abelab.rippy.api.request.UserUpdateRequest;
import dev.abelab.rippy.api.response.UserResponse;
import dev.abelab.rippy.api.response.UsersResponse;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.logic.UserLogic;
import dev.abelab.rippy.logic.UserRoleLogic;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final UserLogic userLogic;

    private final UserRoleLogic usreRoleLogic;

    /**
     * ユーザ一覧を取得
     *
     * @param jwt JWT
     *
     * @return ユーザ一覧レスポンス
     */
    @Transactional
    public UsersResponse getUsers(final String jwt) {
        // ログインユーザの取得
        final var loginUser = this.userLogic.getLoginUser(jwt);

        // 管理者かチェック
        this.userLogic.checkAdmin(loginUser.getId());

        // ユーザ一覧の取得
        final var users = this.userRepository.selectAll();
        final var userResponses = users.stream() //
            .map(user -> this.modelMapper.map(user, UserResponse.class)) //
            .collect(Collectors.toList());

        return new UsersResponse(userResponses);
    }

    /**
     * ユーザを作成
     *
     * @param jwt         JWT
     *
     * @param requestBody ユーザ作成リクエスト
     */
    @Transactional
    public void createUser(final UserCreateRequest requestBody, final String jwt) {
        // ログインユーザの取得
        final var loginUser = this.userLogic.getLoginUser(jwt);

        // 管理者かチェック
        this.userLogic.checkAdmin(loginUser.getId());

        // 有効なユーザロールかチェック
        this.usreRoleLogic.checkForValidRoleId(requestBody.getRoleId());

        // 有効なパスワードかチェック
        this.userLogic.validatePassword(requestBody.getPassword());

        // ユーザを作成
        final var user = this.modelMapper.map(requestBody, User.class);
        user.setPassword(this.userLogic.encodePassword(requestBody.getPassword()));
        this.userRepository.insert(user);
    }

    /**
     * ユーザを更新
     *
     * @param jwt         JWT
     *
     * @param userId      ユーザID
     *
     * @param requestBody ユーザ更新リクエスト
     */
    @Transactional
    public void updateUser(final int userId, final UserUpdateRequest requestBody, final String jwt) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(jwt);

        // 管理者かチェック
        this.userLogic.checkAdmin(loginUser.getId());

        final var user = this.userRepository.selectById(userId);
        user.setFirstName(requestBody.getFirstName());
        user.setLastName(requestBody.getLastName());
        user.setEmail(requestBody.getEmail());
        user.setRoleId(requestBody.getRoleId());
        user.setAdmissionYear(requestBody.getAdmissionYear());
        this.userRepository.update(user);
    }

}
