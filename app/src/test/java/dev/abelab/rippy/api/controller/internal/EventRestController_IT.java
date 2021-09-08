package dev.abelab.rippy.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Date;
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
import org.modelmapper.ModelMapper;

import dev.abelab.rippy.api.controller.AbstractRestController_IT;
import dev.abelab.rippy.db.entity.UserSample;
import dev.abelab.rippy.db.entity.EventSample;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.repository.EventRepository;
import dev.abelab.rippy.api.request.EventCreateRequest;
import dev.abelab.rippy.api.request.EventUpdateRequest;
import dev.abelab.rippy.api.response.EventResponse;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.util.DateTimeUtil;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BaseException;
import dev.abelab.rippy.exception.NotFoundException;
import dev.abelab.rippy.exception.BadRequestException;
import dev.abelab.rippy.exception.ForbiddenException;
import dev.abelab.rippy.exception.UnauthorizedException;

/**
 * EventRestController Integration Test
 */
public class EventRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/events";
	static final String GET_EVENTS_PATH = BASE_PATH;
	static final String CREATE_EVENT_PATH = BASE_PATH;
	static final String UPDATE_EVENT_PATH = BASE_PATH + "/%s";
	static final String DELETE_EVENT_PATH = BASE_PATH + "/%s";

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

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
				// 管理者
				arguments(UserRoleEnum.ADMIN), //
				// メンバー
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

	/**
	 * イベント作成APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class CreateEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_イベントを作成(final UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.CREATED);

			// verify
			final var createdEvents = eventRepository.selectByOwnerId(loginUser.getId());
			assertThat(createdEvents.size()).isEqualTo(1);
			assertThat(createdEvents.get(0).getOwnerId()).isEqualTo(loginUser.getId());
			assertThat(createdEvents.get(0).getExpiredAt()).isInSameMinuteAs(event.getExpiredAt());
		}

		Stream<Arguments> 正_イベントを作成() {
			return Stream.of( //
				// 管理者
				arguments(UserRoleEnum.ADMIN), //
				// メンバー
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void 有効な募集締め切りかチェック(final Date expiredAt, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(expiredAt) //
				.build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.CREATED);
			} else {
				execute(request, exception);
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

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// setup
			final var event = EventSample.builder().build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * イベント更新APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class UpdateEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_イベントを更新(UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var createdEvents = eventRepository.selectByOwnerId(loginUser.getId());
			assertThat(createdEvents.size()).isEqualTo(1);
			assertThat(createdEvents.get(0).getOwnerId()).isEqualTo(loginUser.getId());
			assertThat(createdEvents.get(0).getExpiredAt()).isInSameMinuteAs(event.getExpiredAt());

		}

		Stream<Arguments> 正_イベントを更新() {
			return Stream.of( //
				// 管理者
				arguments(UserRoleEnum.ADMIN), //
				// メンバー
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void 異_イベントを更新する権限がない() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var eventOwner = UserSample.builder().id(loginUser.getId() + 1).build();
			userRepository.insert(eventOwner);
			final var event = EventSample.builder() //
				.ownerId(eventOwner.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@ParameterizedTest
		@MethodSource
		void 有効な募集締め切りかチェック(final Date expiredAt, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			event.setExpiredAt(expiredAt);
			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.OK);
			} else {
				execute(request, exception);
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

		@Test
		void 異_更新対象イベントが存在しない() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, SAMPLE_INT), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
		}

		@Test
		void 異_募集を締め切ったイベントは更新不可() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getYesterday()) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new BadRequestException(ErrorCode.PAST_EVENT_CANNOT_BE_UPDATED));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// setup
			final var event = EventSample.builder().build();
			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, SAMPLE_INT), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * イベント削除APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class DeleteEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_イベントを削除(UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			// test
			final var request = deleteRequest(String.format(UPDATE_EVENT_PATH, event.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var events = eventRepository.selectByOwnerId(loginUser.getId());
			assertThat(events.size()).isEqualTo(0);
		}

		Stream<Arguments> 正_イベントを削除() {
			return Stream.of( //
				// 管理者
				arguments(UserRoleEnum.ADMIN), //
				// メンバー
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void 異_イベントを削除する権限がない() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var eventOwner = UserSample.builder().id(loginUser.getId() + 1).build();
			userRepository.insert(eventOwner);
			final var event = EventSample.builder() //
				.ownerId(eventOwner.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			// test
			final var request = deleteRequest(String.format(UPDATE_EVENT_PATH, event.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@Test
		void 異_削除対象のイベントが存在しない() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			// test
			final var request = deleteRequest(String.format(UPDATE_EVENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// test
			final var request = deleteRequest(String.format(DELETE_EVENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
