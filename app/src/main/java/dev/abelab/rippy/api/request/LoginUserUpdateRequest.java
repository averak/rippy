package dev.abelab.rippy.api.request;

import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * ログインユーザ更新リクエスト
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserUpdateRequest {

    /**
     * メールアドレス
     */
    @NotNull
    String email;

    /**
     * ファーストネーム
     */
    @NotNull
    String firstName;

    /**
     * ラストネーム
     */
    @NotNull
    String lastName;

}
