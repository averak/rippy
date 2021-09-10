package dev.abelab.rippy.api.response;

import java.util.Date;
import java.util.List;

import lombok.*;
import dev.abelab.rippy.model.EventDateModel;

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
     * イベントオーナーID
     */
    Integer ownerId;

    /**
     * イベント募集締め切り
     */
    Date expiredAt;

    /**
     * イベント候補日リスト
     */
    List<EventDateModel> dates;

}
