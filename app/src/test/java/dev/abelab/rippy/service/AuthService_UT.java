package dev.abelab.rippy.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

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

import dev.abelab.rippy.db.entity.UserSample;
import dev.abelab.rippy.api.request.LoginRequest;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.logic.UserLogic;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BaseException;
import dev.abelab.rippy.exception.UnauthorizedException;

/**
 * AuthService Unit Test
 */
class AuthService_UT extends AbstractService_UT {

	@Injectable
	UserRepository userRepository;

	@Injectable
	UserLogic userLogic;

	@Tested
	AuthService authService;

	/**
	 * Test for login
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class LoginTest {

		@Test
		void 正_ユーザがログイン() {
			// setup
			final var user = UserSample.builder().build();

			new Expectations() {
				{
					userRepository.selectByEmail(anyString);
					result = user;
				}
				{
					userLogic.verifyPassword(user, anyString);
					result = null;
				}
				{
					userLogic.generateJwt(user);
					result = SAMPLE_STR;
				}
			};

			// verify
			final var accessToken = authService.login(new LoginRequest());
			assertThat(accessToken.getAccessToken()).isNotNull();
			assertThat(accessToken.getTokenType()).isEqualTo("Bearer");
		}

	}

	/**
	 * Test for get login user
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetLoginUserTest {

		@Test
		void 正_ログインユーザを取得() {
			// setup
			final var user = UserSample.builder().build();

			new Expectations() {
				{
					userLogic.getLoginUser(anyString);
					result = user;
				}
			};

			// verify
			final var credentials = "Bearer <jwt>";
			assertDoesNotThrow(() -> authService.getLoginUser(credentials));
		}

		@ParameterizedTest
		@MethodSource
		void 異_無効な資格情報(final String credentials, final BaseException exception) {
			// verify
			final var occurredException = assertThrows(exception.getClass(), () -> authService.getLoginUser(credentials));
			assertThat(occurredException.getErrorCode()).isEqualTo(exception.getErrorCode());
		}

		Stream<Arguments> 異_無効な資格情報() {
			return Stream.of( //
				arguments("Basic <jwt>", new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN)), //
				arguments("Bearer: <jwt>", new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN)), //
				arguments("<jwt>", new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN)) //
			);
		}

	}

}
