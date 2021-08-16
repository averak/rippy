package dev.abelab.rippy.api.response;

import java.util.List;

import lombok.*;

/**
 * ユーザ一覧レスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {

    /**
     * ユーザリスト
     */
    List<UserResponse> users;

}
