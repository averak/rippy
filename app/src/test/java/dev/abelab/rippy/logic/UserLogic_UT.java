package dev.abelab.rippy.logic;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

import org.springframework.security.crypto.password.PasswordEncoder;

import dev.abelab.rippy.db.entity.UserSample;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.property.JwtProperty;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BaseException;
import dev.abelab.rippy.exception.ForbiddenException;
import dev.abelab.rippy.exception.BadRequestException;
import dev.abelab.rippy.exception.UnauthorizedException;

public class UserLogic_UT extends AbstractLogic_UT {

    @Injectable
    UserRepository userRepository;

    @Injectable
    JwtProperty jwtProperty;

    @Injectable
    PasswordEncoder passwordEncoder;

    @Tested
    UserLogic userLogic;

    /**
     * Test for check admin
     */
    @Nested
    @TestInstance(PER_CLASS)
    class CheckAdminTest {

        @ParameterizedTest
        @MethodSource
        void 正_管理者かチェック(final UserRoleEnum userRole, final BaseException exception) {
            // setup
            final var user = UserSample.builder().roleId(userRole.getId()).build();

            new Expectations() {
                {
                    userRepository.selectById(anyInt);
                    result = user;
                }
            };

            // verify
            if (exception == null) {
                assertDoesNotThrow(() -> userLogic.checkAdmin(user.getId()));
            } else {
                final var occurredException = assertThrows(exception.getClass(), () -> userLogic.checkAdmin(user.getId()));
                assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());
            }
        }

        Stream<Arguments> 正_管理者かチェック() {
            return Stream.of(
                // 管理者
                arguments(UserRoleEnum.ADMIN, null),
                // メンバー
                arguments(UserRoleEnum.MEMBER, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION)));
        }

    }
    /**
     * Test for generate jwt
     */
    @Nested
    @TestInstance(PER_CLASS)
    public class GenerateJwtTest {

        @Test
        void 正_ユーザのJWTを発行する() {
            new Expectations() {
                {
                    jwtProperty.getIssuer();
                    result = SAMPLE_STR;
                }
                {
                    jwtProperty.getSecret();
                    result = SAMPLE_STR;
                }
            };

            // setup
            final var user = UserSample.builder().roleId(UserRoleEnum.ADMIN.getId()).build();

            // verify
            final var jwt = userLogic.generateJwt(user);
            assertThat(jwt).matches("[A-Za-z0-9-_]+.[A-Za-z0-9-_]+.[A-Za-z0-9-_]+");
        }

    }

    /**
     * Test for get login user
     */
    @Nested
    @TestInstance(PER_CLASS)
    public class GetLoginUserTest {

        @Test
        void 正_有効なJWTからログインユーザを取得() {
            // setup
            final var user = UserSample.builder().roleId(UserRoleEnum.ADMIN.getId()).build();

            new Expectations() {
                {
                    userRepository.selectById(anyInt);
                    result = user;
                }
                {
                    jwtProperty.getIssuer();
                    result = SAMPLE_STR;
                }
                {
                    jwtProperty.getSecret();
                    result = SAMPLE_STR;
                }
            };

            // verify
            final var jwt = userLogic.generateJwt(user);
            final var loginUser = userLogic.getLoginUser(jwt);
            assertThat(loginUser.getId()).isEqualTo(user.getId());
        }

        @ParameterizedTest
        @MethodSource
        void 異_無効なJWT(final String jwt, final BaseException exception) {
            new Expectations() {
                {
                    jwtProperty.getSecret();
                    result = SAMPLE_STR;
                }
            };

            // verify
            final var occurredException = assertThrows(exception.getClass(), () -> userLogic.getLoginUser(jwt));
            assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());

        }

        Stream<Arguments> 異_無効なJWT() {
            return Stream.of(
                // 無効
                arguments(SAMPLE_STR, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN)),
                // 期限切れ
                arguments(
                    "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJTQU1QTEUiLCJpZCI6MSwiaWF0IjoxNjI1OTEyMjUxLCJleHAiOjE2MjU5MTIyNTF9.sg0Nf3hQ7d7NpfO569v9zrwF1mvgIq9bewULiZ7H0UF2--UgqPa98XFiF6kpvNLlnv7om6KpmRB6HOzeImfD2w",
                    new UnauthorizedException(ErrorCode.EXPIRED_ACCESS_TOKEN)));
        }

    }

    /**
     * Test for encode password
     */
    @Nested
    @TestInstance(PER_CLASS)
    public class EncodePasswordTest {

        @Test
        void 正_パスワードをハッシュ化() {
            new Expectations() {
                {
                    passwordEncoder.encode(anyString);
                    result = SAMPLE_STR;
                }
            };

            // verify
            final var encodedPassword = userLogic.encodePassword(SAMPLE_STR);
            assertThat(encodedPassword).isEqualTo(SAMPLE_STR);
        }

    }

    /**
     * Test for verify password
     */
    @Nested
    @TestInstance(PER_CLASS)
    public class VerifyPasswordTest {

        @Test
        void 正_パスワードが一致している() {
            final var user = UserSample.builder().roleId(UserRoleEnum.ADMIN.getId()).build();

            new Expectations() {
                {
                    passwordEncoder.matches(anyString, anyString);
                    result = true;
                }
            };

            // verify
            assertDoesNotThrow(() -> userLogic.verifyPassword(user, anyString()));
        }

        @Test
        void 異_パスワードが間違っている() {
            // setup
            final var user = UserSample.builder().roleId(UserRoleEnum.ADMIN.getId()).build();

            new Expectations() {
                {
                    passwordEncoder.matches(anyString, anyString);
                    result = false;
                }
            };

            // verify
            final var occurredException = assertThrows(UnauthorizedException.class, () -> userLogic.verifyPassword(user, anyString()));
            assertThat(occurredException.getErrorCode()).isEqualTo(ErrorCode.WRONG_PASSWORD);
        }

    }

    /**
     * Test for validate password
     */
    @Nested
    @TestInstance(PER_CLASS)
    public class ValidatePasswordTest {

        @ParameterizedTest
        @MethodSource
        void パスワードが有効かどうか(final String password, final BaseException exception) {
            // verify
            if (exception == null) {
                assertDoesNotThrow(() -> userLogic.validatePassword(password));
            } else {
                final var occurredException = assertThrows(exception.getClass(), () -> userLogic.validatePassword(password));
                assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());
            }
        }

        Stream<Arguments> パスワードが有効かどうか() {
            return Stream.of( //
                // 有効
                arguments("f4BabxEr", null), //
                arguments("f4BabxEr4gNsjdtRpH9Pfs6Atth9bqdA", null), //
                // 無効：8文字以下
                arguments("f4BabxE", new BadRequestException(ErrorCode.INVALID_PASSWORD_SIZE)), //
                // 無効：33文字以上
                arguments("f4BabxEr4gNsjdtRpH9Pfs6Atth9bqdAN", new BadRequestException(ErrorCode.INVALID_PASSWORD_SIZE)), //
                // 無効：大文字を含まない
                arguments("f4babxer", new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD)), //
                // 無効：小文字を含まない
                arguments("F4BABXER", new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD)), //
                // 無効：数字を含まない
                arguments("fxbabxEr", new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD)) //
            );
        }

    }

}
