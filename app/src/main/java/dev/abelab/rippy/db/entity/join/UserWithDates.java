package dev.abelab.rippy.db.entity.join;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.db.entity.User;

/**
 * ユーザ + 候補日リスト
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserWithDates extends User {

    /**
     * 候補日リスト
     */
    List<EventDateWithAnswer> dates;

}
