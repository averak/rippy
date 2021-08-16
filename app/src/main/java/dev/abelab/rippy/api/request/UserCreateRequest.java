package dev.abelab.rippy.api.request;

import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * ユーザ作成リクエスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    /**
     * メールアドレス
     */
    @NotNull
    String email;

    /**
     * パスワード
     */
    @NotNull
    String password;

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

    /**
     * 入学年度
     */
    @NotNull
    Integer admissionYear;

    /**
     * ロールID
     */
    @NotNull
    Integer roleId;

}
