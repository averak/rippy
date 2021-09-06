package dev.abelab.rippy.api.request;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

/**
 * イベント更新リクエスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateRequest {

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

}
