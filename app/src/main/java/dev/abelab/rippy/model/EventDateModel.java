package dev.abelab.rippy.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
     * 候補日の順番
     */
    @Min(1)
    @NotNull
    Integer dateOrder;

    /**
     * 開始時間
     */
    @NotNull
    Date startAt;

    /**
     * 終了時間
     */
    @NotNull
    Date finishAt;

    /**
     * 参加可能者リスト
     */
    @NotNull
    List<EventUserModel> users;

}
