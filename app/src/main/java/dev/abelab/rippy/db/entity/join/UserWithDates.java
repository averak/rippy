package dev.abelab.rippy.db.entity.join;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.db.entity.EventAnswerDate;

/**
 * ユーザ + 候補日回答リスト
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserWithDates extends User {

    /**
     * 候補日回答リスト
     */
    List<EventAnswerDate> answerDates;

}
