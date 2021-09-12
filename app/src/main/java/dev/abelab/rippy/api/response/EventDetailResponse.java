package dev.abelab.rippy.api.response;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.model.EventOwnerModel;
import dev.abelab.rippy.model.EventDateModel;
import dev.abelab.rippy.model.EventMemberModel;

/**
 * イベント情報レスポンス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventDetailResponse extends EventResponse {

    /**
     * イベントオーナー
     */
    EventOwnerModel owner;

    /**
     * イベント候補日リスト
     */
    List<EventDateModel> dates;

    /**
     * イベントメンバーリスト
     */
    List<EventMemberModel> members;
}
