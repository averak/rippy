package dev.abelab.rippy.db.entity.join;

import java.util.List;

import lombok.*;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.db.entity.EventDate;

/**
 * イベント + 候補日リスト
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventWithDates extends Event {

    /**
     * 候補日リスト
     */
    List<EventDate> dates;

}
