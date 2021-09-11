package dev.abelab.rippy.db.entity.join;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.db.entity.EventAnswer;
import dev.abelab.rippy.db.entity.EventAnswerDate;

/**
 * イベント回答 + 候補日回答リスト
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventAnswerWithDates extends EventAnswer {

    /**
     * 候補日リスト
     */
    List<EventAnswerDate> dates;

}
