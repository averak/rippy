package dev.abelab.rippy.api.response;

import lombok.*;
import dev.abelab.rippy.model.EventOwnerModel;

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

}
