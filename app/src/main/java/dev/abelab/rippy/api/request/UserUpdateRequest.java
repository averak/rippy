package dev.abelab.rippy.api.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

/**
 * ユーザ作成リクエスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    /**
     * メールアドレス
     */
    @NotNull
    @Size(max = 255)
    String email;

    /**
     * パスワード
     */
    @NotNull
    @Size(max = 255)
    String password;

    /**
     * ファーストネーム
     */
    @NotNull
    @Size(max = 255)
    String firstName;

    /**
     * ラストネーム
     */
    @NotNull
    @Size(max = 255)
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
