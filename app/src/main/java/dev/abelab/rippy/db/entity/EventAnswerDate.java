package dev.abelab.rippy.db.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventAnswerDate {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_answer_date.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_answer_date.event_id
     *
     * @mbg.generated
     */
    private Integer eventId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_answer_date.possible_date
     *
     * @mbg.generated
     */
    private Date possibleDate;
}