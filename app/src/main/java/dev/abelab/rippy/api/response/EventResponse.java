package dev.abelab.rippy.api.response;

import java.util.Date;

import lombok.*;

/**
 * イベント情報レスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    /**
     * ユーザID
     */
    Integer id;

    /**
     * イベント名
     */
    String name;

    /**
     * イベント概要
     */
    String description;

    /**
     * 所有者
     */
    Integer ownerId;

    /**
     * イベント募集締め切り
     */
    Date expiredAt;

}
