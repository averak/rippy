package dev.abelab.rippy.db.entity.join;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.db.entity.EventAnswer;
import dev.abelab.rippy.db.entity.EventAnswerDate;
import dev.abelab.rippy.db.entity.User;

/**
 * イベント回答 + 回答者 + 候補日回答リスト
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventAnswerWithUserAndDates extends EventAnswer {

    /**
     * 回答者
     */
    User user;

    /**
     * 候補日リスト
     */
    List<EventAnswerDate> dates;

}
