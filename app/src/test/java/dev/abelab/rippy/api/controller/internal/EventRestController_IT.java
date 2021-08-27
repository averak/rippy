package dev.abelab.rippy.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import dev.abelab.rippy.api.controller.AbstractRestController_IT;
import dev.abelab.rippy.db.entity.EventSample;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.repository.EventRepository;
import dev.abelab.rippy.api.response.EventResponse;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.UnauthorizedException;

/**
 * EventRestController Integration Test
 */
public class EventRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/events";
	static final String GET_EVENTS_PATH = BASE_PATH;

	@Autowired
	EventRepository eventRepository;

	/**
	 * イベント一覧取得APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetEventsTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_イベント一覧を取得(final UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var events = Arrays.asList( //
				EventSample.builder().ownerId(loginUser.getId()).build(), //
				EventSample.builder().ownerId(loginUser.getId()).build() //
			);
			events.stream().forEach(eventRepository::insert);

			// test
			final var request = getRequest(GET_EVENTS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK, EventsResponse.class);

			// verify
			assertThat(response.getEvents().size()).isEqualTo(events.size());
			assertThat(response.getEvents()) //
				.extracting(EventResponse::getId, EventResponse::getName, EventResponse::getDescription, EventResponse::getOwnerId) //
				.containsExactlyInAnyOrderElementsOf(
					events.stream().map(event -> tuple(event.getId(), event.getName(), event.getDescription(), event.getOwnerId()))
						.collect(Collectors.toList()));
		}

		Stream<Arguments> 正_イベント一覧を取得() {
			return Stream.of( //
				arguments(UserRoleEnum.ADMIN), //
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// test
			final var request = getRequest(GET_EVENTS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
