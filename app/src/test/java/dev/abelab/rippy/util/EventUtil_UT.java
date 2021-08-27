package dev.abelab.rippy.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import dev.abelab.rippy.db.entity.EventSample;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BaseException;
import dev.abelab.rippy.exception.BadRequestException;

public class EventUtil_UT extends AbstractUtil_UT {

    /**
     * Test for validate expired at
     */
    @Nested
    @TestInstance(PER_CLASS)
    class ValidateExpiredAtTest {

        @ParameterizedTest
        @MethodSource
        void 有効な募集締め切りかチェック(final Date expiredAt, final BaseException exception) {
            // setup
            final var event = EventSample.builder().expiredAt(expiredAt).build();

            // verify
            if (exception == null) {
                assertDoesNotThrow(() -> EventUtil.validateExpiredAt(event));
            } else {
                final var occurredException = assertThrows(exception.getClass(), () -> EventUtil.validateExpiredAt(event));
                assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());
            }
        }

        Stream<Arguments> 有効な募集締め切りかチェック() {
            return Stream.of( //
                // 過去の日時
                arguments(DateTimeUtil.getYesterday(), new BadRequestException(ErrorCode.INVALID_EXPIRED_AT)), //
                // 未来の日時
                arguments(DateTimeUtil.getTomorrow(), null) //
            );
        }

    }

}
