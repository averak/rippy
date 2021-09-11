package dev.abelab.rippy.api.response;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.model.EventOwnerModel;
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
     * イベントメンバーリスト
     */
    List<EventMemberModel> members;

}
