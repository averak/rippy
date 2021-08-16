package dev.abelab.rippy.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;

import dev.abelab.rippy.api.controller.AbstractRestController_IT;
import dev.abelab.rippy.db.entity.UserSample;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.api.response.UserResponse;
import dev.abelab.rippy.api.response.UsersResponse;

/**
 * UserRestController Integration Test
 */
public class UserRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/users";
	static final String GET_USERS_PATH = BASE_PATH;

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ一覧取得APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetUsersTest extends AbstractRestControllerInitialization_IT {

		@Test
		void 正_管理者がユーザ一覧を取得() throws Exception {
			// TODO: ログインユーザの取得

			// setup
			final var users = Arrays.asList( //
				UserSample.builder().id(1).email("email1").build(), //
				UserSample.builder().id(2).email("email2").build() //
			);
			users.stream().forEach(userRepository::insert);

			// test
			final var request = getRequest(GET_USERS_PATH);
			// FIXME: ""ではなくJWTを入れる
			request.header(HttpHeaders.AUTHORIZATION, "");
			final var response = execute(request, HttpStatus.OK, UsersResponse.class);

			// verify
			assertThat(response.getUsers()) //
				.extracting(UserResponse::getId, UserResponse::getEmail, UserResponse::getFirstName, UserResponse::getLastName,
					UserResponse::getAdmissionYear, UserResponse::getRoleId) //
				.containsExactlyInAnyOrderElementsOf(users.stream().map(user -> tuple(user.getId(), user.getEmail(), user.getFirstName(),
					user.getLastName(), user.getAdmissionYear(), user.getRoleId())).collect(Collectors.toList()));
		}

	}

}
