package dev.abelab.rippy.api.response;

import java.util.List;

import lombok.*;

/**
 * イベント一覧レスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponse {

    /**
     * イベントリスト
     */
    List<EventResponse> events;

}
