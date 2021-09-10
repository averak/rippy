package dev.abelab.rippy.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import dev.abelab.rippy.db.entity.EventSample;
import dev.abelab.rippy.db.entity.EventDateSample;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BadRequestException;

public class EventDateUtil_UT extends AbstractUtil_UT {

    /**
     * Test for validate event date
     */
    @Nested
    @TestInstance(PER_CLASS)
    class ValidateExpiredAtTest {

        @Test
        void 正_有効な候補日() {
            // setup
            final var event = EventSample.builder().expiredAt(DateTimeUtil.getTomorrow()).build();
            final var eventDate = EventDateSample.builder() //
                .startAt(DateTimeUtil.getDaysLater(2)) //
                .finishAt(DateTimeUtil.getDaysLater(2)) //
                .build();

            // verify
            assertDoesNotThrow(() -> EventDateUtil.validateEventDate(event, eventDate));
        }

        @ParameterizedTest
        @MethodSource
        void 異_無効な候補日(final Date expiredAt, final Date startAt, final Date finishAt) {
            // setup
            final var event = EventSample.builder().expiredAt(expiredAt).build();
            final var eventDate = EventDateSample.builder() //
                .startAt(startAt) //
                .finishAt(finishAt) //
                .build();

            // verify
            final var occurredException = assertThrows(BadRequestException.class, () -> EventDateUtil.validateEventDate(event, eventDate));
            assertThat(occurredException.getErrorCode()).isEqualTo(ErrorCode.INVALID_EVENT_DATE);
        }

        Stream<Arguments> 異_無効な候補日() {
            return Stream.of( //
                // 過去の日時
                arguments(DateTimeUtil.getTomorrow(), DateTimeUtil.getYesterday(), DateTimeUtil.getTomorrow()), //
                // 開始・終了時刻の順序が逆
                arguments(DateTimeUtil.getTomorrow(), DateTimeUtil.getDaysLater(3), DateTimeUtil.getDaysLater(2)), //
                // 募集締め切り前
                arguments(DateTimeUtil.getNextWeek(), DateTimeUtil.getDaysLater(3), DateTimeUtil.getDaysLater(2)) //
            );
        }

    }

}
