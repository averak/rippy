package dev.abelab.rippy.api.request;

import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * ログインユーザのパスワード更新リクエスト
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserPasswordUpdateRequest {

    /**
     * 現在のパスワード
     */
    @NotNull
    String currentPassword;

    /**
     * 新しいパスワード
     */
    @NotNull
    String newPassword;

}
