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
public class EventDate {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_date.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_date.event_id
     *
     * @mbg.generated
     */
    private Integer eventId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_date.date_order
     *
     * @mbg.generated
     */
    private Integer dateOrder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_date.start_at
     *
     * @mbg.generated
     */
    private Date startAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_date.finish_at
     *
     * @mbg.generated
     */
    private Date finishAt;
}