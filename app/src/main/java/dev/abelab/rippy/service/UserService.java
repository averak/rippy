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
import dev.abelab.rippy.util.AuthUtil;

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
     * @param loginUser ログインユーザ
     *
     * @return ユーザ一覧レスポンス
     */
    @Transactional
    public UsersResponse getUsers(final User loginUser) {
        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

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
     * @param loginUser   ログインユーザ
     *
     * @param requestBody ユーザ作成リクエスト
     */
    @Transactional
    public void createUser(final UserCreateRequest requestBody, final User loginUser) {
        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // 有効なユーザロールかチェック
        this.usreRoleLogic.checkForValidRoleId(requestBody.getRoleId());

        // 有効なパスワードかチェック
        AuthUtil.validatePassword(requestBody.getPassword());

        // ユーザを作成
        final var user = this.modelMapper.map(requestBody, User.class);
        user.setPassword(this.userLogic.encodePassword(requestBody.getPassword()));
        this.userRepository.insert(user);
    }

    /**
     * ユーザを更新
     *
     * @param loginUser   ログインユーザ
     *
     * @param userId      ユーザID
     *
     * @param requestBody ユーザ更新リクエスト
     */
    @Transactional
    public void updateUser(final int userId, final UserUpdateRequest requestBody, final User loginUser) {
        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // ユーザを更新
        final var user = this.userRepository.selectById(userId);
        user.setFirstName(requestBody.getFirstName());
        user.setLastName(requestBody.getLastName());
        user.setEmail(requestBody.getEmail());
        user.setRoleId(requestBody.getRoleId());
        user.setAdmissionYear(requestBody.getAdmissionYear());
        this.userRepository.update(user);
    }

    /**
     * ユーザを削除
     *
     * @param loginUser ログインユーザ
     *
     * @param userId    ユーザID
     */
    @Transactional
    public void deleteUser(final int userId, final User loginUser) {
        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // ユーザを削除
        this.userRepository.deleteById(userId);
    }

    /**
     * ログインユーザ詳細を取得
     *
     * @param loginUser ログインユーザ
     *
     * @return ユーザ詳細レスポンス
     */
    @Transactional
    public UserResponse getLoginUser(final User loginUser) {
        return this.modelMapper.map(loginUser, UserResponse.class);
    }

}
