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
import dev.abelab.rippy.db.entity.UserSample;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.util.DateTimeUtil;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BaseException;
import dev.abelab.rippy.exception.ForbiddenException;
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
            // verify
            if (exception == null) {
                assertDoesNotThrow(() -> EventUtil.validateExpiredAt(expiredAt));
            } else {
                final var occurredException = assertThrows(exception.getClass(), () -> EventUtil.validateExpiredAt(expiredAt));
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

    /**
     * Test for check edit event permission
     */
    @Nested
    @TestInstance(PER_CLASS)
    class CheckEditEventPermission {

        @ParameterizedTest
        @MethodSource
        void イベントの編集権限をチェック(final UserRoleEnum roleId, final boolean isOwner, final BaseException exception) {
            // setup
            final var loginUser = UserSample.builder().roleId(roleId.getId()).build();
            final var event = EventSample.builder().expiredAt(DateTimeUtil.getTomorrow()).build();

            // イベントオーナーかどうか
            if (isOwner) {
                event.setOwnerId(loginUser.getId());
            } else {
                event.setOwnerId(loginUser.getId() + 1);
            }

            // verify
            if (exception == null) {
                assertDoesNotThrow(() -> EventUtil.checkDeleteEventPermission(event, loginUser));
            } else {
                final var occurredException =
                    assertThrows(exception.getClass(), () -> EventUtil.checkEditEventPermission(event, loginUser));
                assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());
            }
        }

        Stream<Arguments> イベントの編集権限をチェック() {
            return Stream.of( //
                // 管理者 & イベントオーナー
                arguments(UserRoleEnum.ADMIN, true, null), //
                // 管理者 & 非イベントオーナー
                arguments(UserRoleEnum.ADMIN, false, null), //
                // メンバー & イベントオーナー
                arguments(UserRoleEnum.MEMBER, true, null), //
                // メンバー & 非イベントオーナー
                arguments(UserRoleEnum.MEMBER, false, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION)) //
            );
        }

        @Test
        void 異_募集締め切り後のイベントは編集不可() {
            // setup
            final var loginUser = UserSample.builder().roleId(UserRoleEnum.MEMBER.getId()).build();
            final var event = EventSample.builder() //
                .ownerId(loginUser.getId()) //
                .expiredAt(DateTimeUtil.getYesterday()) //
                .build();

            // verify
            final var occurredException =
                assertThrows(BadRequestException.class, () -> EventUtil.checkEditEventPermission(event, loginUser));
            assertThat(occurredException.getErrorCode()).isEqualTo(ErrorCode.PAST_EVENT_CANNOT_BE_UPDATED);
        }

    }

    /**
     * Test for check delete event permission
     */
    @Nested
    @TestInstance(PER_CLASS)
    class CheckDeleteEventPermission {

        @ParameterizedTest
        @MethodSource
        void イベントの削除権限をチェック(final UserRoleEnum roleId, final boolean isOwner, final BaseException exception) {
            // setup
            final var loginUser = UserSample.builder().roleId(roleId.getId()).build();
            final var event = EventSample.builder().expiredAt(DateTimeUtil.getTomorrow()).build();

            // イベントオーナーかどうか
            if (isOwner) {
                event.setOwnerId(loginUser.getId());
            } else {
                event.setOwnerId(loginUser.getId() + 1);
            }

            // verify
            if (exception == null) {
                assertDoesNotThrow(() -> EventUtil.checkDeleteEventPermission(event, loginUser));
            } else {
                final var occurredException =
                    assertThrows(exception.getClass(), () -> EventUtil.checkDeleteEventPermission(event, loginUser));
                assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());
            }
        }

        Stream<Arguments> イベントの削除権限をチェック() {
            return Stream.of( //
                // 管理者 & イベントオーナー
                arguments(UserRoleEnum.ADMIN, true, null), //
                // 管理者 & 非イベントオーナー
                arguments(UserRoleEnum.ADMIN, false, null), //
                // メンバー & イベントオーナー
                arguments(UserRoleEnum.MEMBER, true, null), //
                // メンバー & 非イベントオーナー
                arguments(UserRoleEnum.MEMBER, false, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION)) //
            );
        }

    }

}
