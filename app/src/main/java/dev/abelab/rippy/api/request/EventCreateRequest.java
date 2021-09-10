package dev.abelab.rippy.api.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import dev.abelab.rippy.model.EventDateModel;

/**
 * イベント作成リクエスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateRequest {

    /**
     * イベント名
     */
    @NotNull
    @Size(max = 255)
    String name;

    /**
     * イベント概要
     */
    @NotNull
    @Size(max = 1000)
    String description;

    /**
     * イベント募集締め切り
     */
    @NotNull
    Date expiredAt;

    /**
     * イベント候補日リスト
     */
    @NotNull
    List<EventDateModel> dates;

}
