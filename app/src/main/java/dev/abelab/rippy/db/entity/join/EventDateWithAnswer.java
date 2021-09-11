package dev.abelab.rippy.db.entity.join;

import lombok.*;
import dev.abelab.rippy.db.entity.EventDate;
import dev.abelab.rippy.db.entity.EventAnswerDate;

/**
 * イベント候補日 + 回答
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventDateWithAnswer extends EventDate {

    /**
     * 回答
     */
    EventAnswerDate answer;

}
