package dev.abelab.rippy.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.NotFoundException;

public class UserRoleUtil_UT extends AbstractUtil_UT {

    /**
     * Test for check for valid roleId
     */
    @Nested
    @TestInstance(PER_CLASS)
    class CheckForValidRoleIdTest {

        @ParameterizedTest
        @MethodSource
        void 正_存在するユーザロール(final int roleId) {
            // verify
            assertThatCode(() -> {
                UserRoleUtil.checkForValidRoleId(roleId);
            }).doesNotThrowAnyException();
        }

        Stream<Arguments> 正_存在するユーザロール() {
            return Stream.of( //
                // 管理者
                arguments(UserRoleEnum.ADMIN.getId()),
                // メンバー
                arguments(UserRoleEnum.MEMBER.getId()) //
            );
        }

        @ParameterizedTest
        @MethodSource
        void 異_存在しないユーザロール(final int roleId) {
            // verify
            final var exception = assertThrows(NotFoundException.class, () -> UserRoleUtil.checkForValidRoleId(roleId));
            assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_USER_ROLE);
        }

        Stream<Arguments> 異_存在しないユーザロール() {
            return Stream.of( //
                arguments(0), //
                arguments(UserRoleEnum.values().length + 1) //
            );
        }

    }

}
