package dev.abelab.rippy.model;

import java.util.Date;

import lombok.*;

/**
 * イベント候補日モデル
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDateModel {

    /**
     * 開始時間
     */
    Date startAt;

    /**
     * 終了時間
     * */
    Date finishAt;

}
