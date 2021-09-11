package dev.abelab.rippy.db.entity.join;

import lombok.*;
import dev.abelab.rippy.db.entity.EventAnswer;
import dev.abelab.rippy.db.entity.User;

/**
 * イベント回答 + 回答者
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventAnswerWithUser extends EventAnswer {

    /**
     * 回答者
     */
    User user;

}
